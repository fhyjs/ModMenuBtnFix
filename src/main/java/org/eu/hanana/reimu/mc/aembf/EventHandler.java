package org.eu.hanana.reimu.mc.aembf;

import com.terraformersmc.mod_menu.ModMenu;
import com.terraformersmc.mod_menu.config.ModMenuConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static com.terraformersmc.mod_menu.event.ModMenuEventHandler.MODS_BUTTON_TEXTURE;
import static com.terraformersmc.mod_menu.event.ModMenuEventHandler.buttonHasText;
import static org.eu.hanana.reimu.mc.aembf.AeTitleFxMod.CONFIG;
import static org.eu.hanana.reimu.mc.aembf.AeTitleFxMod.logger;

public class EventHandler {
    private static final Logger log = LogManager.getLogger(EventHandler.class);

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onScreenInit(ScreenEvent.Init.Post event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        if (!CONFIG.getLeft().ENABLED.get()) return;
        Screen screen = event.getScreen();
        ModMenuConfig.TitleMenuButtonStyle titleMenuButtonStyle = ModMenu.getConfig().MODS_BUTTON_STYLE.get();
        logger.debug(titleMenuButtonStyle);
        Button onlineBtn=null;
        for (Renderable renderable : screen.renderables) {
            if (renderable instanceof Button button){
                if (buttonHasText(button,"menu.online")){
                    onlineBtn=button;
                }
            }
        }
        Button modsBtn=null;
        for (Renderable renderable : screen.renderables) {
            if (renderable instanceof Button button){
                if (buttonHasText(button,"fml.menu.mods")){
                    modsBtn=button;
                }
            }
        }
        Button optionsBtn=null;
        for (Renderable renderable : screen.renderables) {
            if (renderable instanceof Button button){
                if (buttonHasText(button,"menu.options")){
                    optionsBtn=button;
                }
            }
        }
        Button languageBtn=null;
        for (Renderable renderable : screen.renderables) {
            if (renderable instanceof SpriteIconButton button){
               languageBtn=button;
            }
        }
        if (titleMenuButtonStyle.equals(ModMenuConfig.TitleMenuButtonStyle.REPLACE_REALMS)){
            if (onlineBtn!=null){
                Method removeWidget = Screen.class.getDeclaredMethod("removeWidget", GuiEventListener.class);
                removeWidget.trySetAccessible();
                removeWidget.invoke(screen,onlineBtn);
                var moveUp = onlineBtn.getHeight()+5;
                var buttonsUnder = new ArrayList<Button>();
                for (Renderable renderable : screen.renderables) {
                    if (renderable instanceof Button button) {
                        if (button.getY()>onlineBtn.getY()+onlineBtn.getHeight()){
                            buttonsUnder.add(button);
                        }
                    }
                }
                for (Button button : buttonsUnder) {
                    button.setY(button.getY()-moveUp);
                }
            }
        }else if (titleMenuButtonStyle.equals(ModMenuConfig.TitleMenuButtonStyle.SHRINK)) {
            if (onlineBtn != null && modsBtn != null && optionsBtn != null) {
                onlineBtn.setWidth(optionsBtn.getWidth());
                var xMove = 5;
                if (onlineBtn.getWidth() > 150) {
                    onlineBtn.setWidth(optionsBtn.getWidth() / 2 - 5);
                    xMove += 5;
                }
                modsBtn.setY(onlineBtn.getY());
                modsBtn.setWidth(onlineBtn.getWidth());
                modsBtn.setX(onlineBtn.getX() + onlineBtn.getWidth() + xMove);
                var moveUp = modsBtn.getHeight() + 5;
                var buttonsUnder = new ArrayList<Button>();
                for (Renderable renderable : screen.renderables) {
                    if (renderable instanceof Button button) {
                        if (button.getY() > onlineBtn.getY() + onlineBtn.getHeight()) {
                            buttonsUnder.add(button);
                        }
                    }
                }
                for (Button button : buttonsUnder) {
                    button.setY(button.getY() - moveUp);
                }
            }
        }else if (titleMenuButtonStyle.equals(ModMenuConfig.TitleMenuButtonStyle.ICON)){
            if (modsBtn!=null&&languageBtn!=null&&onlineBtn!=null){
                modsBtn.setWidth(languageBtn.getWidth());
                modsBtn.setHeight(languageBtn.getHeight());
                if(modsBtn.getX()<50){
                    modsBtn.setY(27);
                    modsBtn.setX(screen.width-24);
                }else {
                    modsBtn.setY(onlineBtn.getY());
                    modsBtn.setX(languageBtn.getX());
                }
                var moveUp = modsBtn.getHeight() + 5;
                var buttonsUnder = new ArrayList<Button>();
                for (Renderable renderable : screen.renderables) {
                    if (renderable instanceof Button button) {
                        if (button.getY() > onlineBtn.getY() + onlineBtn.getHeight()) {
                            buttonsUnder.add(button);
                        }
                    }
                }
                for (Button button : buttonsUnder) {
                    button.setY(button.getY() - moveUp);
                }
                modsBtn.setMessage(Component.literal("`*`"));
            }
        }
    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onScreenRender(ScreenEvent.Render.Post event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        if (!CONFIG.getLeft().ENABLED.get()) return;
        Button modsBtn=null;
        for (Renderable renderable : event.getScreen().renderables) {
            if (renderable instanceof Button button){
                if (button.getMessage().getString().equals("`*`")){
                    modsBtn=button;
                }
            }
        }
        if (modsBtn!=null){
            int v = 0;

            if (!modsBtn.isActive()) {
                v += 40;
            } else if (modsBtn.isHoveredOrFocused()) {
                v += 20;
            }

            event.getGuiGraphics().blit(MODS_BUTTON_TEXTURE,
                    modsBtn.getX(),
                    modsBtn.getY(),
                    0,
                    v,
                    modsBtn.getWidth(),
                    modsBtn.getHeight(),
                    32,
                    64
            );
            if (modsBtn.getWidth()>150){
                modsBtn.setMessage(Component.translatable("fml.menu.mods"));
            }
        }
    }
}
