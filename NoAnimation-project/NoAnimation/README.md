# NoAnimation

Client-side Fabric mod for Minecraft 1.21.11.

## Build
Requires Java 21 and Gradle. Run:

```bash
gradle build
```

The JAR will be created in `build/libs/`.

## Configuration
After launching the game once, edit `.minecraft/config/noanimation.txt`.
Add or remove one item identifier per line. Lines starting with `#` are comments.

Default entries:
- `minecraft:wind_charge`
- `minecraft:ender_pearl`
- `minecraft:end_crystal`

This prototype suppresses the first-person swing progress for the configured held items. It does not hide the held item model.
