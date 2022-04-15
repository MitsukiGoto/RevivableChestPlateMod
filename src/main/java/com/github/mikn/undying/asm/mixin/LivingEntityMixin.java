package com.github.mikn.undying.asm.mixin;

import com.github.mikn.undying.init.EnchantmentInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method= "checkTotemDeathProtection(Lnet/minecraft/world/damagesource/DamageSource;)Z", at=@At("RETURN"), cancellable = true)
    private void injection(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if(!cir.getReturnValue()) {
            LivingEntity livingEntity = (LivingEntity) (Object) this;
            if(livingEntity instanceof Player player && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.UNDYING, player) > 0) {
                if(player instanceof ServerPlayer serverPlayer) {
                    Advancement advancementIn = player.getServer().getAdvancements().getAdvancement(new ResourceLocation("adventure/totem_of_undying"));
                    PlayerAdvancements playerAdvancements = serverPlayer.getAdvancements();
                    if(advancementIn != null && playerAdvancements.getOrStartProgress(advancementIn).isDone()) {
                        serverPlayer.giveExperienceLevels(-5);
                    } else {
                        serverPlayer.giveExperienceLevels(-10);
                    }
                    Vec3 vec = new Vec3(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ());
                    serverPlayer.connection.send(new ClientboundCustomSoundPacket(new ResourceLocation("item.totem.use"), serverPlayer.getSoundSource(), vec, 1.0f, 1.0f));
                }
                player.setHealth(1.0f);
                player.removeAllEffects();
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                cir.setReturnValue(true);
            }
        }
    }
}
