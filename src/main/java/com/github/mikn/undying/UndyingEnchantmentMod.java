package com.github.mikn.undying;

import com.github.mikn.undying.config.UndyingConfig;
import com.github.mikn.undying.init.EnchantmentInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UndyingEnchantmentMod.MODID)
public class UndyingEnchantmentMod {
    public static final String MODID = "undying";
    public UndyingEnchantmentMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, UndyingConfig.SPEC, "undying-common.toml");
        EnchantmentInit.ENCHANTMENTS.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
