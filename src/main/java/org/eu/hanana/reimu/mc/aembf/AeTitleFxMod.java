package org.eu.hanana.reimu.mc.aembf;

import com.terraformersmc.mod_menu.config.ModMenuConfig;
import com.terraformersmc.mod_menu.config.ModMenuConfigScreen;
import cpw.mods.cl.ModularURLHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import sun.misc.Unsafe;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.eu.hanana.reimu.mc.aembf.AeTitleFxMod.MOD_ID;

@Mod(value = MOD_ID,dist = {Dist.CLIENT})
public class AeTitleFxMod {
    public static final String MOD_ID = "aemodbtnfix";
    public static final Logger logger = LogManager.getLogger();
    public static final Pair<Config, ModConfigSpec> CONFIG = new ModConfigSpec.Builder().configure(Config::new);
    public AeTitleFxMod(IEventBus modBus, ModContainer container) {
        NeoForge.EVENT_BUS.register(new EventHandler());
        modBus.addListener(this::init);
        container.registerConfig(ModConfig.Type.CLIENT, CONFIG.getValue());
        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, screen) ->
                new ConfigurationScreen(container, screen));
    }
    private void init(FMLCommonSetupEvent event){

    }

}
