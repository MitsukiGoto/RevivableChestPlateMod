package com.github.mikn.asm.mixin;

import com.github.mikn.init.EnchantmentInit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method= "checkTotemDeathProtection(Lnet/minecraft/world/damagesource/DamageSource;)Z", at=@At("RETURN"), cancellable = true)
    private void injection(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if(!cir.getReturnValue()) {
            LivingEntity livingEntity = (LivingEntity) (Object) this;
            if(livingEntity instanceof Player player && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.REVIVAL, player) > 0) {
                player.setHealth(1.0f);
                player.removeAllEffects();
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                player.level.broadcastEntityEvent(player, (byte)35);
                cir.setReturnValue(true);
            }
        }
    }
}
