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

package fr.djaytan.minecraft.jobs_reborn_patch_place_break.controller.listener;

import fr.djaytan.minecraft.jobs_reborn_patch_place_break.controller.JobsController;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

public class BlockBreakListener implements Listener {

  private final BukkitScheduler bukkitScheduler;
  private final JobsController jobsController;
  private final Plugin plugin;

  public BlockBreakListener(
      @NotNull BukkitScheduler bukkitScheduler,
      @NotNull JobsController jobsController,
      @NotNull Plugin plugin) {
    this.bukkitScheduler = bukkitScheduler;
    this.jobsController = jobsController;
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onBlockBreak(@NotNull BlockBreakEvent event) {
    Location location = event.getBlock().getLocation();
    World world = event.getBlock().getWorld();

    bukkitScheduler.runTaskLater(
        plugin, () -> jobsController.setPlayerBlockPlacedMetadata(world.getBlockAt(location)), 1L);
  }
}
