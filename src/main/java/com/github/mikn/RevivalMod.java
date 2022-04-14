package com.github.mikn;

import com.github.mikn.init.EnchantmentInit;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class RevivalMod implements ModInitializer {
    public static String MODID = "revival";
    @Override
    public void onInitialize() {
        Registry.register(Registry.ENCHANTMENT, new ResourceLocation(RevivalMod.MODID, "revival_ench"), EnchantmentInit.REVIVAL);
    }
}
