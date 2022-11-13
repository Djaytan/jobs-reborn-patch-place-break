/*
 * JobsReborn extension to patch place-break (Bukkit servers)
 * Copyright (C) 2022 - Loïc DUBOIS-TERMOZ
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.djaytan.minecraft.jobs_reborn_patch_place_break.model.serializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Singleton;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

@Singleton
public class LocalDateTimeStringSerializer implements StringSerializer<LocalDateTime> {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

  @Override
  public @NotNull String serialize(@NotNull LocalDateTime localDateTime) {
    Preconditions.checkNotNull(localDateTime);
    return localDateTime.format(formatter);
  }

  @Override
  public @NotNull LocalDateTime deserialize(@NotNull String string) {
    Preconditions.checkNotNull(string);
    return LocalDateTime.parse(string, formatter);
  }
}
