package com.github.mikn.init;

import com.github.mikn.enchantment.UndyingEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentInit {
    public static final Enchantment UNDYING = new UndyingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.CHEST);
}