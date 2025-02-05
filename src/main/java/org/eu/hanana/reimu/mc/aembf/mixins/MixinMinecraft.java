package org.eu.hanana.reimu.mc.aembf.mixins;

import com.terraformersmc.mod_menu.gui.ModsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.neoforge.client.gui.ModListScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow public abstract void setScreen(@Nullable Screen guiScreen);

    @Shadow @javax.annotation.Nullable public Screen screen;

    @Inject(method = {"setScreen"},at=@At("HEAD"),cancellable = true)
    public void setScreen(Screen guiScreen, CallbackInfo ci) {
        if (guiScreen instanceof ModListScreen) {
            setScreen(new ModsScreen(screen));
            ci.cancel();
        }
    }
}
