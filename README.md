# jobs-reborn-patch-place-break
![Target](https://img.shields.io/badge/plugin-Minecraft-blueviolet)
![Minecraft version](https://img.shields.io/badge/version-1.18.2-blue)
[![CircleCI](https://dl.circleci.com/status-badge/img/gh/Djaytan/mc-jobs-reborn-patch-place-break/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/Djaytan/mc-jobs-reborn-patch-place-break/tree/main)

This is a place-break patch extension of Jobs Reborn plugin for Bukkit servers.

## Place-break issue

With [JobsReborn](https://www.spigotmc.org/resources/jobs-reborn.4216/), it appears that placing
a block and then breaking it is counted as a valid job action which lead to a payment for the player.
Given this fact, it's very easy to imagine a diamond ore to be gathered with a Silk Touch pickaxe
and immediately after be replaced to repeat the process again and again...

A solution with JobsReborn is to remove money and xp when a diamond ore is placed, preventing
the previously described scenario. But it isn't perfect: if you expect to use money and xp boost
for whatever reason, the amount of money to give when the block is broken will be higher than
the amount to be retrieved when placing the same one. And... well... losing money and xp when you
place a block for decoration isn't very appreciated by players too.

The place-break patch provided by JobsReborn seem to be insufficient: you must specify **for each
block** a fix amount of time during which breaking the block again will not permit to earn money
and xp. It's a first step forward, but it's insufficient. Specifying an unlimited time isn't
sufficient too, because at server restart the placed blocks will not prevent the payment anymore.

So, this is why this project exists: doing an obvious and easy patch that would be done a long time
ago.

## How the patch works

The patch is very simple: each block is marked as a "player" one. This information
is persisted over restart of the server. At payment time, if the breaking block is the one placed
by a player, the payment will be cancelled no matter who is the breaker.
Easy and efficient, this does the trick.

## Setup

We expect here that you already have a Bukkit server already set up with the JobsReborn plugin
installed on it.

Simply download the latest `.jar` file in the
[release section](https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/releases/) of this
repository and put it into the `plugins/` folder, and you'll be done! After restarting the server,
the plugin should now appear green in the list displayed by the `/plugins` command.

## Licence

This project is under the licence GNU GPLv3.
