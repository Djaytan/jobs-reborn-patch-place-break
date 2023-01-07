/*
 * MIT License
 *
 * Copyright (c) 2023 Loïc DUBOIS-TERMOZ (alias Djaytan)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fr.djaytan.minecraft.jobsreborn.patchplacebreak.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ConfigManager {

  public static final String CONFIG_FILE_NAME = "config.yml";

  private final ClassLoader classLoader;
  private final ConfigurationLoader<CommentedConfigurationNode> configurationLoader;
  private final Path configFile;

  @Inject
  ConfigManager(ClassLoader classLoader,
      ConfigurationLoader<CommentedConfigurationNode> configurationLoader,
      @Named("configFile") Path configFile) {
    this.classLoader = classLoader;
    this.configurationLoader = configurationLoader;
    this.configFile = configFile;
  }

  public void createDefaultIfNotExists() throws ConfigException {
    if (Files.exists(configFile)) {
      return;
    }
    createDefault();
  }

  private void createDefault() {
    try (InputStream configFileIs = classLoader.getResourceAsStream(CONFIG_FILE_NAME)) {
      if (configFileIs == null) {
        throw ConfigException.resourceNotFound(CONFIG_FILE_NAME);
      }
      copyDefault(configFileIs);
      log.atInfo().log("Default configuration file created.");
    } catch (IOException e) {
      throw ConfigException.failedCreatingDefault(CONFIG_FILE_NAME, e);
    }
  }

  private void copyDefault(@NonNull InputStream configFileIs) throws IOException {
    long configFileSize = Files.copy(configFileIs, configFile);

    if (configFileSize <= 0) {
      throw ConfigException.unexpectedEmptyDefaultConfig(CONFIG_FILE_NAME);
    }
  }

  public @NonNull Config readConfig() throws ConfigException {
    try {
      ConfigurationNode rootNode = configurationLoader.load();
      Config config = rootNode.get(Config.class);

      if (config == null) {
        throw ConfigException.failedReadingConfig(configFile);
      }
      return config;
    } catch (ConfigurateException e) {
      throw ConfigException.failedReadingConfig(configFile, e);
    }
  }
}
