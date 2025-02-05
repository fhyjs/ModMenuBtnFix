package org.eu.hanana.reimu.mc.aembf.mixins;

import com.terraformersmc.mod_menu.event.ModMenuEventHandler;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.eu.hanana.reimu.mc.aembf.AeTitleFxMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModMenuEventHandler.class)
public class MixinModMenuEventHandler {
    @Inject(method = {"onScreenInit"},at=@At("HEAD"),cancellable = true)
    private static void onScreenInit(ScreenEvent.Init.Post event, CallbackInfo ci) {
        if (AeTitleFxMod.CONFIG.getLeft().ENABLED.get())
            ci.cancel();
    }
}
