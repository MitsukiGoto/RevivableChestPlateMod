package com.github.mikn.undying.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class UndyingConfig {
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
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.EnumValue<UndyingCost> undyingCost;
    static {
        BUILDER.push("Config for Undying Enchantment Mod");
        undyingCost = BUILDER.defineEnum("meltSpeed", UndyingCost.NORMAL);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
