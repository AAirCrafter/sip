package air.sip.client.mixin;

import air.sip.Config;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.math.MathHelper;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static air.sip.Config.enabled;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    @Inject(method = "isVisible", at = @At("RETURN"), cancellable = true)
    private void isVisible(S state, CallbackInfoReturnable<Boolean> cir) {
        if (enabled) cir.setReturnValue(true);
    }

    @Inject(method = "getRenderLayer", at = @At("RETURN"), cancellable = true)
    private void getRenderLayer(S state, boolean showBody, boolean translucent, boolean showOutline, CallbackInfoReturnable<@Nullable RenderLayer> cir) {
        if (!enabled) return;
        if (!state.invisible) return;

        @SuppressWarnings("unchecked")
        LivingEntityRenderer<?, S, M> self = (LivingEntityRenderer<?, S, M>) (Object) this;

        cir.setReturnValue(RenderLayers.itemEntityTranslucentCull(self.getTexture(state)));
    }

    @Inject(method = "getMixColor", at = @At("RETURN"), cancellable = true)
    private void getMixColor(S state, CallbackInfoReturnable<Integer> cir) {
        if (!enabled) return;
        if (!state.invisible) return;

        int alpha = Math.round(MathHelper.clamp((float) Config.opacityPerc / 100, 0f, 1f) * 255f);
        cir.setReturnValue((alpha << 24) | 0xFFFFFF);
    }
}