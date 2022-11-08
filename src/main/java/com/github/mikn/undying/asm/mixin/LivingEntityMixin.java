/*
 Copyright (c) 2022 Mikndesu

 Permission is hereby granted, free of charge, to any person obtaining a copy of
 this software and associated documentation files (the "Software"), to deal in
 the Software without restriction, including without limitation the rights to
 use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.mikn.undying.asm.mixin;

import com.github.mikn.undying.config.UndyingConfig;
import com.github.mikn.undying.init.EnchantmentInit;
import me.shedaniel.autoconfig.AutoConfig;
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
    @Inject(method = "checkTotemDeathProtection(Lnet/minecraft/world/damagesource/DamageSource;)Z", at = @At("RETURN"), cancellable = true)
    private void injection(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        UndyingConfig config = AutoConfig.getConfigHolder(UndyingConfig.class).getConfig();
        if (cir.getReturnValue()) return;
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.UNDYING, livingEntity);
        if (enchantmentLevel <= 0) return;
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            Advancement advancementIn = serverPlayer.getServer().getAdvancements()
                    .getAdvancement(new ResourceLocation("adventure/totem_of_undying"));
            PlayerAdvancements playerAdvancements = serverPlayer.getAdvancements();
            boolean isPlayerUsedTotem = (advancementIn != null && playerAdvancements.getOrStartProgress(advancementIn).isDone());
            int playerExpLevel = serverPlayer.experienceLevel;
            int cost = isPlayerUsedTotem ? config.undyingCost.getInt() : (config.undyingCost.getInt()) * 2;
            if (playerExpLevel < cost) {
                return;
            }
            serverPlayer.giveExperienceLevels(-cost);
            Vec3 vec = new Vec3(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ());
            serverPlayer.connection.send(new ClientboundCustomSoundPacket(new ResourceLocation("item.totem.use"),
                    serverPlayer.getSoundSource(), vec, 1.0f, 1.0f, serverPlayer.getRandom().nextLong()));
        }
        /*
         * To avoid mobs other than players from being invincible, if the mobs has both
         * absorption and fire_resistance,
         * this enchantment will not affect them.
         */
        if (!(livingEntity instanceof Player)) {
            if (livingEntity.hasEffect(MobEffects.ABSORPTION) && livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE))
                return;
        }
        livingEntity.setHealth(3.0f);
        livingEntity.removeAllEffects();
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
        cir.setReturnValue(true);
    }
}
