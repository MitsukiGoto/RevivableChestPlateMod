package com.github.mikn.undying.init;

import com.github.mikn.undying.UndyingEnchantmentMod;
import com.github.mikn.undying.enchantment.UndyingEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, UndyingEnchantmentMod.MODID);
    public static final RegistryObject<Enchantment> UNDYING = ENCHANTMENTS.register("undying", () -> new UndyingEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.CHEST));
}