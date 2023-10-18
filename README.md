# jobs-reborn-patch-place-break

![Target](https://img.shields.io/badge/plugin-Minecraft-blueviolet)
![Compatibility](https://img.shields.io/badge/compatibility-v1.17.x%20-->%20v1.20.x-blue)
[![Maven - CI](https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/actions/workflows/maven-ci.yml/badge.svg?branch=main)](https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/actions/workflows/maven-ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Djaytan_mc-jobs-reborn-patch-place-break&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Djaytan_mc-jobs-reborn-patch-place-break)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Djaytan_mc-jobs-reborn-patch-place-break&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Djaytan_mc-jobs-reborn-patch-place-break)

A place-and-break patch extension
of [JobsReborn plugin](https://www.spigotmc.org/resources/jobs-reborn.4216/)
for Bukkit servers.

The resource is available
on [Spigot](https://www.spigotmc.org/resources/jobsreborn-patchplacebreak.102779/),
[Hangar (PaperMC)](https://hangar.papermc.io/Djaytan/JobsReborn-PatchPlaceBreak) and
[Modrinth](https://modrinth.com/plugin/jobsreborn-patchplacebreak).

## Place-and-break issue

With JobsReborn, it appears that placing a block and then breaking it is counted as a valid job
action which leads to a payment for the player.
Given this fact, it's straightforward to imagine a diamond ore being gathered with a Silk Touch
pickaxe and immediately after being replaced again so that the process can be repeated
indefinitely...

A solution with JobsReborn is to remove money and xp when a diamond ore is placed, preventing
the previously described scenario. But it isn't perfect: if you expect to use money and xp boost
for whatever reason, the amount of money to give when the block is broken will be higher than
the amount to be retrieved when placing the same one. And... well... losing money and xp when you
place a block for decoration isn't very appreciated by players too.

The place-and-break patch provided by JobsReborn seems insufficient: you must specify **for
each block** a fix amount of time during which breaking the block again will not permit earning
money and xp. It's a first step forward, but can go even further. Specifying an unlimited time can
be limited too, because after 14 days maximum the placed blocks will not prevent the payment
anymore... And finally, piston exploit isn't taking into account at all.

So, this is why this addon exists: giving an easy and efficient solution to these problematics.

## How the patch works

The patch is simple: when breaking or placing blocks, each one is tagged.
This information is persisted across server restarts.

At payment time, if a BREAK, TNTBREAK or PLACE action involve an active "player" tag, the payment
will be cancelled.
It doesn't matter whose player is the author, so if one player places a block and another one breaks
it, the payment will be cancelled anyway.

There are two main behaviors that have subtle differences:

* When a block is placed, a tag is attached to it: This is useful to patch BREAK and TNTBREAK
  actions (e.g. for breaking diamond ores) ;
* When a block is broken, a tag is attached to the location where it was: This is useful to
  patch PLACE actions (e.g. for placing saplings).

*Note: the second behavior leads to "ephemeral" tags, that's to say, a tag which will be
applicable during a short-time only. The value is fixed to three seconds.*

As a comparison point, this behavior can have similarities with the one implemented by
[mcMMO](https://www.spigotmc.org/resources/official-mcmmo-original-author-returns.64348/) plugin.

Easy and efficient, this does the trick.

## Setup of the Bukkit plugin

We expect here that you already have a Bukkit server already set up with the JobsReborn plugin
installed on it.

The server's version must be higher or equals to 1.17.x.

If you wish to use this plugin on a lower version of the server, you should instead use the
following version:

* [2.2.53](https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/releases/tag/v2.2.53) for
  Minecraft versions from 1.11 tp 1.16 (included)
* [1.2.0](https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/releases/tag/v1.2.0) for
  Minecraft versions 1.8, 1.9 and 1.10

Download the latest `.jar` file from the
[release section](https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/releases/) of this
repository and put it into the `plugins/` folder, and you'll be done! After restarting the server,
the plugin should now appear green in the list displayed by the `/plugins` command.

At this point, you should turn off all options of the "PlaceAndBreak" config part of JobsReborn.
This would lead to a similar result as the following one:

```yaml
PlaceAndBreak:
  # Enable blocks protection, like ore, from exploiting by placing and destroying same block again and again.
  # Modify restrictedBlocks.yml for blocks you want to protect
  Enabled: false
  # Enabling this we will ignore blocks generated in ore generators, liko stone, coublestone and obsidian. You can still use timer on player placed obsidian block
  IgnoreOreGenerators: true
  # For how long in days to keep block protection data in data base
  # This will clean block data which ones have -1 as cooldown value
  # Data base cleanup will be performed on each server startup
  # This cant be more then 14 days
  KeepDataFor: 14
  # All blocks will be protected X sec after player places it on ground.
  GlobalBlockTimer:
    Use: false
    Timer: 3
  # Enable silk touch protection.
  # With this enabled players wont get paid for broken blocks from restrictedblocks list with silk touch tool.
  SilkTouchProtection: false
```

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on ways to help us.

Take care to always follow our [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md).

## Built With

* Java 8
* [Maven](https://maven.apache.org/)
* [Guice](https://github.com/google/guice)
* [HikariCP](https://github.com/brettwooldridge/HikariCP)
* [Flyway](https://github.com/flyway/flyway)
* [Jakarta Beans Validator](https://beanvalidation.org/)
  (with [Hibernate Validator](https://github.com/hibernate/hibernate-validator) as implementation)
* [Configurate](https://github.com/SpongePowered/Configurate)

Specifically for the tests:

* [JUnit 5](https://junit.org/junit5/)
* [Mockito](https://site.mockito.org/)
* [AssertJ](https://github.com/assertj/assertj)
* [EqualsVerifier](https://jqno.nl/equalsverifier/) & [ToStringVerifier](https://github.com/jparams/to-string-verifier)
* [Jimfs](https://github.com/google/jimfs)
* [MockBukkit](https://github.com/MockBukkit/MockBukkit)
* [Awaitability](https://github.com/awaitility/awaitility)

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the
[tags on this repository](https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/tags).

## Licence

This project is under the [MIT](https://opensource.org/licenses/MIT) license.
