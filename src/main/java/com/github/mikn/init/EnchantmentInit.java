package com.github.mikn.init;

import com.github.mikn.enchantment.RevivalEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentInit {
    public static final Enchantment REVIVAL = new RevivalEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.CHEST);
}