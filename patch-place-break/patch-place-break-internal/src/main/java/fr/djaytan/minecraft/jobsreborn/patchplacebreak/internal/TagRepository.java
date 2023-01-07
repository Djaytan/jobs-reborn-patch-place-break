/*
 * MIT License
 *
 * Copyright (c) 2022-2023 Loïc DUBOIS-TERMOZ (alias Djaytan)
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

package fr.djaytan.minecraft.jobsreborn.patchplacebreak.internal;

import java.util.Optional;

import fr.djaytan.minecraft.jobsreborn.patchplacebreak.api.entities.Tag;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.api.entities.TagLocation;
import lombok.NonNull;

/**
 * Represents a {@link Tag} repository.
 *
 * <p>A repository represents an abstraction of data storage. This is the appliance of the
 * Data Access Object pattern (DAO).
 *
 * @apiNote It is agnostic about whether the final storage is persistent (e.g. non-temporary files)
 * or not (like in-memory). However, it is expected that the underlying implementation share ways
 * to select the type of storage wished. At least one type must be a persistent one. Non-persistent
 * ones are typically reserved for test purposes. If there isn't way to configure
 * the type of storage, or no particular type wished as been provided, then the default type must
 * be a persistent one.
 */
// TODO: explicitly ask an ID parameter for methods (instead of TagLocation)
public interface TagRepository {

  /**
   * Puts the tag into the storage.
   *
   * <p>If the tag doesn't exist (i.e. no tag with the same {@link TagLocation} is already stored),
   * a new resource is created. Otherwise, the existing tag is overridden.
   *
   * @param tag The tag to be put in storage.
   * @throws TagRepositoryException If something prevents the tag to be put into the storage.
   */
  void put(@NonNull Tag tag) throws TagRepositoryException;

  /**
   * Finds a tag from a {@link TagLocation}.
   *
   * @param tagLocation The tag location where the sought tag may be.
   * @return The tag which match with the given location if exists.
   * @throws TagRepositoryException If something prevents the search to be done.
   */
  @NonNull
  Optional<Tag> findByLocation(@NonNull TagLocation tagLocation) throws TagRepositoryException;

  /**
   * Deletes a tag from its {@link TagLocation}.
   *
   * <p>If no tag exists at the given location, then nothing is done.
   *
   * @param tagLocation The location of the tag to be deleted.
   * @throws TagRepositoryException If something prevents the tag to be deleted (except the case
   * where it doesn't exist).
   */
  void delete(@NonNull TagLocation tagLocation) throws TagRepositoryException;
}