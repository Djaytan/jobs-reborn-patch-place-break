# JobsReborn-PatchPlaceBreak \[Addon]

![Target](https://img.shields.io/badge/plugin-Minecraft-blueviolet)
![Compatibility](https://img.shields.io/badge/compatibility-v1.17.x%20-->%20v1.20.x-blue)
[![CI](https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/actions/workflows/ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Djaytan_mc-jobs-reborn-patch-place-break&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Djaytan_mc-jobs-reborn-patch-place-break)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Djaytan_mc-jobs-reborn-patch-place-break&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Djaytan_mc-jobs-reborn-patch-place-break) [![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FDjaytan%2Fmc-jobs-reborn-patch-place-break.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2FDjaytan%2Fmc-jobs-reborn-patch-place-break?ref=badge_shield)
 
[![semantic-release: conventional-commits](https://img.shields.io/badge/semantic--release-conventional--commits-e10079?logo=semantic-release)](https://github.com/semantic-release/semantic-release)
[![OpenSSF Best Practices](https://www.bestpractices.dev/projects/8112/badge)](https://www.bestpractices.dev/projects/8112)
[![OpenSSF Scorecard](https://api.securityscorecards.dev/projects/github.com/Djaytan/mc-jobs-reborn-patch-place-break/badge)](https://securityscorecards.dev/viewer/?uri=github.com/Djaytan/mc-jobs-reborn-patch-place-break)

A place-and-break patch extension
of [JobsReborn plugin](https://www.spigotmc.org/resources/jobs-reborn.4216/)
for Spigot servers.

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

In fact, the place-and-break patch provided by JobsReborn already provides a better solution.
However, its main limitation comes from the maximum delay of 14 days before the protection is
released. Furthermore, you must specify **for each block** a fix amount of time during which
breaking the block again will not permit earning money and xp. Overall, that's a first step forward
for sure, but we can/should go even further. That's here this plugin takes place.

## Features

* Similar and performant patch-and-break protection than the one already provided by JobsReborn
  (including piston patch, pair-players exploit, resiliency to server restarts, etc) ;
* Unlimited in time protection, discouraging once for all any exploiter in your server ;
* More fine-grained customization of restricted blocks (the list of blocks to be protected can be
  inclusive or exclusive) in order to easily maximise the protection effectiveness while fine-tuning
  performances ;
* Easy and fast setup with SQLite as a default persistence solution for quick discovery and
  experimentation with this plugin (not recommended in production environment tho because of low
  performances) ;
* Possibility to rely on a MySQL or a MariaDB database (much better performances observed) ;
* We keep you safe in case of configuration mistake by detecting issues early with strict
  validation to ensure fail-fast and safe reaction from the plugin.

## How the patch works

The patch is simple: when breaking or placing blocks, each one is tagged.
This information is persisted across server restarts.

At payment time, if a BREAK, TNTBREAK or PLACE action involve an active "player" tag, the payment
will be cancelled.
It doesn't matter whose player is the author, so if one player places a block and another one breaks
it, the payment will be cancelled anyway (patching pair-players exploit).

There are two main behaviors that have subtle differences:

* When a block is placed, a tag is attached to it: This is useful to patch BREAK and TNTBREAK
  actions (e.g. when breaking player-placed diamond ores) ;
* When a block is broken, a tag is attached to the location where it was: This is useful to
  patch PLACE actions (e.g. when replacing previously broken sapling repeatedly and in a brief time
  window).

*Note: the second behavior leads to "ephemeral" tags, that's to say, a tag which will be
applicable during a short-time only. The value is fixed to three seconds.*

Finally, when a piston moves a block, the tag just follows it (being ephemeral or not), thus
patching piston exploits.

As a comparison point, this behavior can have similarities with the one implemented by
[mcMMO](https://www.spigotmc.org/resources/official-mcmmo-original-author-returns.64348/) plugin.

Easy and efficient, this does the trick.

## Setup of the Spigot plugin

We expect here that you already have a Spigot server already set up with the JobsReborn plugin
installed on it.

The server's version must be higher or equals to 1.17.x.

### Download

Download the latest `.jar` file from the
[release section](https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/releases/) of this
repository.

#### Signature verification

In case you wish to verify the plugin's signature to ensure that the file is trustworthy, you can
follow these steps:

* Install the [Cosign CLI](https://github.com/sigstore/cosign?tab=readme-ov-file#installation)
* Install the `.sig` and `.pem` files alongside the plugin one
* Execute the following Bash commands (you will have to adapt them if necessary):

```shell
PLUGIN_VERSION=''
cosign verify-blob "JobsReborn-PatchPlaceBreak-${PLUGIN_VERSION}.jar" \
  --signature="JobsReborn-PatchPlaceBreak-${PLUGIN_VERSION}.jar-keyless.sig" \
  --certificate="JobsReborn-PatchPlaceBreak-${PLUGIN_VERSION}.jar-keyless.pem" \
  --certificate-identity=https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/.github/workflows/release.yml@refs/heads/main \
  --certificate-oidc-issuer=https://token.actions.githubusercontent.com
```

Well think about filling the `PLUGIN_VERSION` variable.

### Installation

Put the plugin JAR file into the `plugins/` folder, and you'll be done!
After restarting the server, the plugin should now appear green in the list displayed by
the `/plugins` command.

At this point, you should turn off all options of the "PlaceAndBreak" config part of JobsReborn.
This would lead to a similar result as the following one:

```yaml
PlaceAndBreak:
  # Enable blocks protection, like ore, from exploiting by placing and destroying same block again and again.
  # Modify restrictedBlocks.yml for blocks you want to protect
  Enabled: false
  # Should we use new block protection method
  # In most cases this is more efficient way to check for break/place protection and doesn't involve any cache or data saving into database
  # Only works with 1.14+ servers
  NewMethod: false
  BlockTracker:
    # Should we use BlockTracker plugin instead of built in block tracker
    Enabled: false
  # Enabling this we will ignore blocks generated in ore generators, liko stone, coublestone and obsidian. You can still use timer on player placed obsidian block
  IgnoreOreGenerators: true
  # For how long in days to keep block protection data in data base
  # This will clean block data which ones have -1 as cooldown value
  # Data base cleanup will be performed on each server startup
  # This cant be more then 14 days
  KeepDataFor: 14
  GlobalBlockTimer:
    # All blocks will be protected X seconds after player places it
    Place:
      Use: false
      # Time in seconds. This can only be positive number and no higher than 900
      # If higher timers are needed then it can be defined in restrictedBlocks.yml file for each specific block
      Timer: 3
    Break:
      Use: false
      # Time in seconds. This can only be positive number and no higher than 60
      # This is only to prevent player from placing blocks into same place and getting paid once more
      Timer: 3
  # Enable silk touch protection.
  # With this enabled players wont get paid for broken blocks from restrictedblocks list with silk touch tool.
  SilkTouchProtection: false
```

## Contributing

Please read [CONTRIBUTING.md](docs/CONTRIBUTING.md) for details on ways to help us.

Take care to always follow our [CODE_OF_CONDUCT.md](docs/CODE_OF_CONDUCT.md).

## How to support us?

If you appreciate the project and want to support us, then you can consider putting a star on the
GitHub repository.
This will show us your interest in the project, and we will be grateful for that!

## Built with

* Java 8
* [Maven](https://maven.apache.org/)
* [Guice](https://github.com/google/guice)
* [HikariCP](https://github.com/brettwooldridge/HikariCP)
* [Flyway](https://github.com/flyway/flyway)
* [Jakarta Beans Validator](https://beanvalidation.org/)
  (with [Hibernate Validator](https://github.com/hibernate/hibernate-validator) as implementation)
* [Configurate](https://github.com/SpongePowered/Configurate)
* [Spigot API](https://hub.spigotmc.org/javadocs/spigot/)
* [JobsReborn API](https://github.com/Zrips/Jobs/wiki/API)
* [bStats](https://bstats.org/)

Specifically for the tests:

* [JUnit 5](https://junit.org/junit5/)
* [Mockito](https://site.mockito.org/)
* [AssertJ](https://github.com/assertj/assertj)
* [EqualsVerifier](https://jqno.nl/equalsverifier/) & [ToStringVerifier](https://github.com/jparams/to-string-verifier)
* [Testcontainers](https://testcontainers.com/)
* [Jimfs](https://github.com/google/jimfs)
* [MockBukkit](https://github.com/MockBukkit/MockBukkit)
* [Awaitability](https://github.com/awaitility/awaitility)
* [Pitest](https://pitest.org/)

Specifically for the CI:

* [GitHub Actions](https://github.com/features/actions)
* [CodeQL](https://codeql.github.com/)
* [OpenSSF Scorecard](https://github.com/ossf/scorecard)
* [semantic-release](https://github.com/semantic-release/semantic-release)
* [commitlint](https://github.com/conventional-changelog/commitlint)
* [Sigstore](https://www.sigstore.dev/)

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the
[tags on this repository](https://github.com/Djaytan/mc-jobs-reborn-patch-place-break/tags).

## Security Policy

In case you think having found a security vulnerability, please consult
our [Security Policy](docs/SECURITY.md).

## Licence

This project is under the [MIT license](LICENSE).


[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FDjaytan%2Fmc-jobs-reborn-patch-place-break.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2FDjaytan%2Fmc-jobs-reborn-patch-place-break?ref=badge_large)