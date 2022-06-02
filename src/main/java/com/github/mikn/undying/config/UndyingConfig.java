package com.github.mikn.undying.config;

import com.github.mikn.undying.UndyingEnchantmentMod;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = UndyingEnchantmentMod.MODID)
public class UndyingConfig implements ConfigData {
    public enum UndyingCost {
        CRAZY(30), HEAVY(20), NORMAL(10), LIGHT(5), LILBIT(3); 
        private final int value;
        UndyingCost(final int value) {
            this.value = value;
        }
        public int getInt() {
            return this.value;
        }
    }
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public UndyingCost undyingCost = UndyingCost.NORMAL;
}
