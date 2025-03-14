# Changelog - Angel Ring 3

## [3.0.0+beta.1] - 2025-03-14

A brand new version of Angel Ring. This version is a complete rewrite of the mod, with a lot of new features coming along the way and improvements. Supports both Accessories, Curios API and Trinkets API. Support for more modloaders: Fabric, Quilt and NeoForge. This version is not compatible with the previous versions of Angel Ring 2. 
Also note, if you previously used the any clone or fork of the mod with the same mod's id, mod will try to override the ring with the new one to make sure that nothing was broken and you have seamless migration for 1.21+ versions. Modpack developers should provide a way to migrate the ring to the new version through the recipe changes, item replacement through CraftTweaker or KubeJS if any of the other forks or clones were used before.

# Requirements
**Angel Ring 3 as of now requires:** 
- HaydenAPI 1.0.4 or later
- Flight API 1.0.4 or later

**NeoForge version:** 
- NeoForge 21.0.42-beta or later 
- Curios API 9.2.2+1.21.1 (or later) **OR** Accessories API 1.1.0-beta.16 (or later) with Curios Compat Layer for Accessories.

**Fabric version:** 
- Fabric Loader 0.16.10 or later
- Fabric API 0.102.0 or later
- Trinkets API 3.10.0 or later **OR** Accessories API 1.1.0-beta.16 (or later) with Trinkets Compatibility Layer for Accessories.

### Added
- Added support for Accessories API. Should work just fine also with just Curios or Trinkets API.
- Added support for Fabric and NeoForge modloaders. Forge is currently unsupported, however its support may come a bit later in 3.0.x builds.
- A brand new flight issues detection system provided by the Flight API. Now you will be notified about any issues with the flight and the mod will try to attempt to fix them. If this is not possible, then will notify you about the issue and problematic mod classes. You can report these issues to the mod authors by providing debug.log file from the `logs` directory
- Moved to a HaydenAPI configuration system, now the configuration file is `angelring.json5`.

### Changed
- Now the Angel Ring is in Classic Mode by default. Does not require anything. You can change the mode in the config file, so it will require durability points or XP to work if you changed this. Simple, yet powerful, right?
- - Mana and RF/FE support will come in the future versions. Stay tuned!
- As of Angel Ring 3, there will be only two items. The Angel Ring and Diamond Ring. No more Energetic Angel Ring nor Thermal Angel Rings. The Angel Ring will have all the features of the previous versions such as ability to change the FE consumption and storage rates and so on. However, if you'd like to fit your expert pack progression, there are some addons planned with custom textures and predefined settings for the Angel Ring after the 3.0.0 release.

### Fixed
- Fixed most of the flight issues with the Angel Ring. Now the flight should be 99.9% stable and reliable with most (if not every) mods.

### Removed
- Removed the Energetic Angel Ring and Thermal Foundation Angel Ring. Now there is only one Angel Ring item. However, they probably will come back as separate mod in the future with mod integrations. While it is not recommended to update 1.20.1 worlds to this version, it is still possible to do so. However, the Energetic Angel Ring and Thermal Foundation Angel Ring will be removed from the world.

## [2.3.1] - 2024-11-09

### Fixed
- Fixed an issue where the Angel Ring would not work properly due to a missing NBT check that was accidentally removed in the previous version when we started working on 3.0.0. Oops. Sorry about that.
- Fixed a tooltip being wrongly displayed when the Angel Ring was in Classic Mode.

## [2.3.0] - 2024-11-01

### Added
- Now Experience API supports Long values.
- Russian translation by TheAnaxMan.
- Turkish translation by RuyaSavascisi.

### Changed
- Changed the recipe for the Thermal Foundation's Angel Ring to use energetic one instead of the original one.

### Fixed
- Fixed an issue where the Angel Ring would not work properly when the player had more than ~15601 levels.
- Fixed an issue when the Angel Ring warned player about low energy, but the ring was in Classic Mode.

There may still be some incompatibilities with other mods, but I am working on it.
