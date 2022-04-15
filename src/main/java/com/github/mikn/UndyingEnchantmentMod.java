package com.github.mikn;

import com.github.mikn.init.EnchantmentInit;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class UndyingEnchantmentMod implements ModInitializer {
    public static String MODID = "undying";
    @Override
    public void onInitialize() {
        Registry.register(Registry.ENCHANTMENT, new ResourceLocation(UndyingEnchantmentMod.MODID, "undying"), EnchantmentInit.UNDYING);
    }
}
