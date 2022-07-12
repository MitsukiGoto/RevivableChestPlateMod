package com.github.mikn.undying;

import com.github.mikn.undying.config.UndyingConfig;
import com.github.mikn.undying.init.EnchantmentInit;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UndyingEnchantmentMod implements ModInitializer {
    public static final String MODID = "undying";
    public static final Logger LOGGER = LogManager.getLogger("Undying/Main");
    public static UndyingConfig HOLDER;
    @Override
    public void onInitialize() {
        AutoConfig.register(UndyingConfig.class, GsonConfigSerializer::new);
        HOLDER = AutoConfig.getConfigHolder(UndyingConfig.class).getConfig();
        Registry.register(Registry.ENCHANTMENT, new ResourceLocation(UndyingEnchantmentMod.MODID, "undying"), EnchantmentInit.UNDYING);
    }
}
