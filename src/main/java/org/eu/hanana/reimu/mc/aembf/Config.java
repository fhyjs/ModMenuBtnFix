package org.eu.hanana.reimu.mc.aembf;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.Logging;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.logging.log4j.LogManager;

public class Config {
    public final ModConfigSpec.BooleanValue ENABLED;

    public Config(ModConfigSpec.Builder builder) {
        ENABLED = builder.define("enable", true);;
    }
}
