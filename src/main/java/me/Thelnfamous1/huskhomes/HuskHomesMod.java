package me.Thelnfamous1.huskhomes;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.william278.huskhomes.FabricHuskHomes;
import org.slf4j.Logger;

@Mod(HuskHomesMod.MODID)
public class HuskHomesMod {
    public static final String MODID = "huskhomes";
    public static final Logger LOGGER = LogUtils.getLogger();
    private final FabricHuskHomes huskHomes;

    public HuskHomesMod() {
        this.huskHomes = new FabricHuskHomes();

        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
        MinecraftForge.EVENT_BUS.addListener(this::onServerAboutToStart);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStopping);
    }

    private void onServerAboutToStart(ServerAboutToStartEvent event){
        this.huskHomes.setMinecraftServer(event.getServer());
        this.huskHomes.onInitializeServer();
    }

    private void onRegisterCommands(RegisterCommandsEvent event) {
        this.huskHomes.onCommandRegistration(event.getDispatcher());
    }

    private void onServerStarting(ServerStartingEvent event) {
        this.huskHomes.onEnable();
    }

    private void onServerStopping(ServerStoppingEvent event) {
        this.huskHomes.onDisable();
    }
}
