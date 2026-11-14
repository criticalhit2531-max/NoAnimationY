package com.noanimation;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

public final class NoAnimationClient implements ClientModInitializer {
    private static final Set<String> TARGET_ITEMS = new LinkedHashSet<>();
    private static Path configPath;

    @Override
    public void onInitializeClient() {
        configPath = FabricLoader.getInstance().getConfigDir().resolve("noanimation.txt");
        loadConfig();
    }

    private static void loadConfig() {
        TARGET_ITEMS.clear();

        try {
            if (Files.notExists(configPath)) {
                Files.createDirectories(configPath.getParent());
                Files.writeString(
                    configPath,
                    "# Add one item ID per line. Lines beginning with # are ignored.\n" +
                    "minecraft:wind_charge\n" +
                    "minecraft:ender_pearl\n" +
                    "minecraft:end_crystal\n",
                    StandardCharsets.UTF_8
                );
            }

            for (String line : Files.readAllLines(configPath, StandardCharsets.UTF_8)) {
                String value = line.trim();
                if (!value.isEmpty() && !value.startsWith("#")) {
                    TARGET_ITEMS.add(value);
                }
            }
        } catch (IOException exception) {
            System.err.println("[NoAnimation] Could not load config: " + exception.getMessage());
            TARGET_ITEMS.add("minecraft:wind_charge");
            TARGET_ITEMS.add("minecraft:ender_pearl");
            TARGET_ITEMS.add("minecraft:end_crystal");
        }
    }

    public static boolean shouldRemoveSwing(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return false;
        }

        Identifier id = Registries.ITEM.getId(stack.getItem());
        return id != null && TARGET_ITEMS.contains(id.toString());
    }
}
