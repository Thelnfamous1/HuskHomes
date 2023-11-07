package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.ITeleportWarmupEvent;

public class TeleportWarmupEvent extends AbstractTeleportEvent<ITeleportWarmupEvent> {
    public TeleportWarmupEvent(ITeleportWarmupEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
