package com.github.mikn.undying.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class UndyingEnchantment extends Enchantment {
    public UndyingEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, EnchantmentCategory.ARMOR_CHEST, equipmentSlots);
    }

    public int getMinCost(int p_45017_) {
        return p_45017_ * 10;
    }

    public int getMaxCost(int p_45027_) {
        return this.getMinCost(p_45027_) + 15;
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
