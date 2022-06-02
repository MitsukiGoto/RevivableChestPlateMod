package com.github.mikn.undying.asm.mixin;

import com.github.mikn.undying.config.UndyingConfig;
import com.github.mikn.undying.init.EnchantmentInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method= "checkTotemDeathProtection(Lnet/minecraft/world/damagesource/DamageSource;)Z", at=@At("RETURN"), cancellable = true)
    private void injection(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        var undyingCost = UndyingConfig.undyingCost.get();
        if(!cir.getReturnValue()) {
            LivingEntity livingEntity = (LivingEntity) (Object) this;
            int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.UNDYING.get(), livingEntity);
            if(enchantmentLevel>0) {
                if(livingEntity instanceof ServerPlayer serverPlayer) {
                    Advancement advancementIn = serverPlayer.getServer().getAdvancements().getAdvancement(new ResourceLocation("adventure/totem_of_undying"));
                    PlayerAdvancements playerAdvancements = serverPlayer.getAdvancements();
                    boolean isPlayerUsedTotem = (advancementIn != null && playerAdvancements.getOrStartProgress(advancementIn).isDone());
                    int playerExpLevel = serverPlayer.experienceLevel;
                    int cost = (isPlayerUsedTotem ? undyingCost.getInt() : (undyingCost.getInt())*2) + enchantmentLevel - 1;
                    if(playerExpLevel<cost) {
                        return;
                    }
                    serverPlayer.giveExperienceLevels(-cost);
                    Vec3 vec = new Vec3(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ());
                    serverPlayer.connection.send(new ClientboundCustomSoundPacket(new ResourceLocation("item.totem.use"), serverPlayer.getSoundSource(), vec, 1.0f, 1.0f));
                }
                /*
                * To avoid mobs other than players from being invincible, if the mobs has both absorption and fire_resistance,
                * this enchantment will not affect them.
                * */
                if(!(livingEntity instanceof Player)) {
                    if(livingEntity.hasEffect(MobEffects.ABSORPTION)&&livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE)) return;
                }
                livingEntity.setHealth(3.0f);
                livingEntity.removeAllEffects();
                livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                cir.setReturnValue(true);
            }
        }
    }
}
