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
package fr.djaytan.minecraft.jobsreborn.patchplacebreak.bukkit.adapter;

import com.gamingmesh.jobs.container.ActionInfo;
import com.gamingmesh.jobs.container.ActionType;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.api.PatchPlaceBreakApi;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.api.entities.Block;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.api.entities.BlockActionType;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.api.entities.BlockLocation;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.api.entities.Vector;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.bukkit.adapter.converter.ActionTypeConverter;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.bukkit.adapter.converter.BlockFaceConverter;
import fr.djaytan.minecraft.jobsreborn.patchplacebreak.bukkit.adapter.converter.LocationConverter;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.NonNull;
import org.bukkit.block.BlockFace;

/**
 * Adapter of {@link PatchPlaceBreakApi} for Bukkit.
 *
 * @see PatchPlaceBreakApi
 */
@Singleton
public class PatchPlaceBreakBukkitAdapterApi {

  private final ActionTypeConverter actionTypeConverter;
  private final BlockFaceConverter blockFaceConverter;
  private final LocationConverter locationConverter;
  private final PatchPlaceBreakApi patchPlaceBreakApi;

  @Inject
  public PatchPlaceBreakBukkitAdapterApi(
      ActionTypeConverter actionTypeConverter,
      BlockFaceConverter blockFaceConverter,
      LocationConverter locationConverter,
      PatchPlaceBreakApi patchPlaceBreakApi) {
    this.actionTypeConverter = actionTypeConverter;
    this.blockFaceConverter = blockFaceConverter;
    this.locationConverter = locationConverter;
    this.patchPlaceBreakApi = patchPlaceBreakApi;
  }

  /**
   * Puts a tag on the specified location.
   *
   * @param bukkitBlock The block where to put tag.
   * @param isEphemeral Whether the tag to put must be an ephemeral one or not.
   * @return The completable future object.
   * @see PatchPlaceBreakApi#putTag(Block, boolean)
   */
  public @NonNull CompletableFuture<Void> putTag(@NonNull org.bukkit.block.Block bukkitBlock, boolean isEphemeral) {
    BlockLocation blockLocation = locationConverter.convert(bukkitBlock);
    return patchPlaceBreakApi.putTag(Block.of(blockLocation, bukkitBlock.getType().name()), isEphemeral);
  }

  /**
   * Moves tags associated with given blocks (if they exist) to the direction of the specified block
   * face.
   *
   * <p><i>The performed move is of only one block in the specified direction.
   *
   * @param bukkitBlocks The list of blocks with potential tags to be moved.
   * @param blockFace The block face from which to infer the move direction.
   * @return The completable future object.
   * @see PatchPlaceBreakApi#moveTags(Set, Vector)
   */
  public @NonNull CompletableFuture<Void> moveTags(
      @NonNull Collection<org.bukkit.block.Block> bukkitBlocks, @NonNull BlockFace blockFace) {
    Set<Block> blockLocations =
      bukkitBlocks.stream()
        .map(
          bukkitBlock ->
            Block.of(locationConverter.convert(bukkitBlock), bukkitBlock.getType().name()))
        .collect(Collectors.toSet());
    Vector vector = blockFaceConverter.convert(blockFace);
    return patchPlaceBreakApi.moveTags(blockLocations, vector);
  }

  /**
   * Removes existing tag from the specified location.
   *
   * @param bukkitBlock The block from which to remove the tag if it exists.
   * @return The completable future object.
   * @see PatchPlaceBreakApi#removeTag(Block)
   */
  public @NonNull CompletableFuture<Void> removeTag(@NonNull org.bukkit.block.Block bukkitBlock) {
    BlockLocation blockLocation = locationConverter.convert(bukkitBlock);
    return patchPlaceBreakApi.removeTag(Block.of(blockLocation, bukkitBlock.getType().name()));
  }

  /**
   * Checks if the specified job action for the specified block is a patch-and-break exploit or not.
   *
   * @param actionInfo The job action recorded.
   * @param bukkitBlock The targeted block by job action which has been recorded.
   * @return <code>true</code> if the specified job action for the specified block is a
   *     patch-and-break exploit or not.
   * @see PatchPlaceBreakApi#isPlaceAndBreakExploit(BlockActionType, Block)
   */
  public boolean isPlaceAndBreakExploit(ActionInfo actionInfo, org.bukkit.block.Block bukkitBlock) {
    if (actionInfo == null || bukkitBlock == null) {
      return false;
    }

    ActionType actionType = actionInfo.getType();

    if (actionType == null) {
      return false;
    }

    BlockActionType patchActionType = actionTypeConverter.convert(actionType);
    Block block = Block.of(locationConverter.convert(bukkitBlock), bukkitBlock.getType().name());
    return patchPlaceBreakApi.isPlaceAndBreakExploit(patchActionType, block);
  }
}
