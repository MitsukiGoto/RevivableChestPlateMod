package com.github.mikn.undying;

import com.github.mikn.undying.init.EnchantmentInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(UndyingEnchantmentMod.MODID)
public class UndyingEnchantmentMod {
    public static final String MODID = "undying";
    public UndyingEnchantmentMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EnchantmentInit.ENCHANTMENTS.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
