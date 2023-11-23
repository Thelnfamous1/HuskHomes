package me.Thelnfamous1.huskhomes;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.william278.huskhomes.FabricHuskHomes;
import org.slf4j.Logger;

@Mod(HuskHomesMod.MODID)
public class HuskHomesMod {
    public static final String MODID = "huskhomes";
    public static final Logger LOGGER = LogUtils.getLogger();
    private final FabricHuskHomes huskHomes;

    public HuskHomesMod() {
        this.huskHomes = new FabricHuskHomes();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onServerAboutToStart);
    }

    private void onServerAboutToStart(FMLLoadCompleteEvent event){
        event.enqueueWork(this.huskHomes::onInitializeServer);
    }
}
