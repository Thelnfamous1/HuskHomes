package me.Thelnfamous1.huskhomes;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class HuskHomesTeleporter implements ITeleporter {

    private final PortalInfo portalInfo;

    public HuskHomesTeleporter(PortalInfo portalInfo){
        this.portalInfo = portalInfo;
    }

    @Override
    public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        return this.portalInfo;
    }
}
