/*
 * The MIT License
 * Copyright © 2022 Loïc DUBOIS-TERMOZ (alias Djaytan)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.sql.access;

import fr.djaytan.minecraft.jobsreborn.patchplacebreak.api.entities.BlockLocation;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.api.entities.Tag;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.api.OldNewBlockLocationPair;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.api.OldNewBlockLocationPairSet;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.api.TagRepository;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.api.TagRepositoryException;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.storage.sql.ConnectionPool;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class SqlTagRepository implements TagRepository {

  private final ConnectionPool connectionPool;
  private final TagSqlDao tagSqlDao;

  @Inject
  public SqlTagRepository(@NotNull ConnectionPool connectionPool, @NotNull TagSqlDao tagSqlDao) {
    this.connectionPool = connectionPool;
    this.tagSqlDao = tagSqlDao;
  }

  @Override
  public void put(@NotNull Tag tag) {
    connectionPool.useConnection(
        connection -> {
          try {
            connection.setAutoCommit(false);
            tagSqlDao.delete(connection, tag.getBlockLocation());
            tagSqlDao.insert(connection, tag);
            connection.commit();
          } catch (SQLException e) {
            throw TagRepositoryException.put(tag, e);
          }
        });
  }

  @Override
  public void updateLocations(@NotNull OldNewBlockLocationPairSet oldNewLocationPairs) {
    connectionPool.useConnection(
        connection -> {
          try {
            connection.setAutoCommit(false);
            Set<Tag> newTags = prepareNewTags(connection, oldNewLocationPairs);
            cleanUpTags(connection, oldNewLocationPairs);
            putNewTags(connection, newTags);
            connection.commit();
          } catch (SQLException e) {
            throw TagRepositoryException.update(oldNewLocationPairs, e);
          }
        });
  }

  private @NotNull Set<Tag> prepareNewTags(
      @NotNull Connection connection, @NotNull OldNewBlockLocationPairSet oldNewLocationPairs)
      throws SQLException {
    Set<Tag> newTags = new HashSet<>();

    for (OldNewBlockLocationPair oldNewLocationPair :
        oldNewLocationPairs.getOldNewBlockLocationPairs()) {
      Optional<Tag> oldTag =
          tagSqlDao.findByLocation(connection, oldNewLocationPair.getOldBlockLocation());

      if (!oldTag.isPresent()) {
        continue;
      }

      Tag newTag =
          new Tag(
              oldNewLocationPair.getNewBlockLocation(),
              oldTag.get().isEphemeral(),
              oldTag.get().getInitLocalDateTime());
      newTags.add(newTag);
    }

    return newTags;
  }

  private void cleanUpTags(
      @NotNull Connection connection,
      @NotNull OldNewBlockLocationPairSet oldNewBlockLocationPairSet)
      throws SQLException {
    Set<BlockLocation> tagsToRemove = oldNewBlockLocationPairSet.flattenBlockLocations();

    for (BlockLocation oldTagLocation : tagsToRemove) {
      tagSqlDao.delete(connection, oldTagLocation);
    }
  }

  private void putNewTags(@NotNull Connection connection, @NotNull Set<Tag> newTags)
      throws SQLException {
    for (Tag newTag : newTags) {
      tagSqlDao.insert(connection, newTag);
    }
  }

  @Override
  public @NotNull Optional<Tag> findByLocation(@NotNull BlockLocation blockLocation) {
    return connectionPool.useConnection(
        connection -> {
          try {
            return tagSqlDao.findByLocation(connection, blockLocation);
          } catch (SQLException e) {
            throw TagRepositoryException.fetch(blockLocation, e);
          }
        });
  }

  @Override
  public void delete(@NotNull BlockLocation blockLocation) {
    connectionPool.useConnection(
        connection -> {
          try {
            tagSqlDao.delete(connection, blockLocation);
          } catch (SQLException e) {
            throw TagRepositoryException.delete(blockLocation, e);
          }
        });
  }
}