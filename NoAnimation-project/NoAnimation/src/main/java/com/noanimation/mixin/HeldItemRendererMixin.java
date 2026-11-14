package com.noanimation.mixin;

import com.noanimation.NoAnimationClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @ModifyVariable(
        method = "renderFirstPersonItem",
        at = @At("HEAD"),
        argsOnly = true,
        ordinal = 2
    )
    private float noanimation$removeSwingAnimation(float swingProgress) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return swingProgress;
        }

        ItemStack mainHand = client.player.getMainHandStack();
        ItemStack offHand = client.player.getOffHandStack();

        if (NoAnimationClient.shouldRemoveSwing(mainHand)
            || NoAnimationClient.shouldRemoveSwing(offHand)) {
            return 0.0F;
        }

        return swingProgress;
    }
}
