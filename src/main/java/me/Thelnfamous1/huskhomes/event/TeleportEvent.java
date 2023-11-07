package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.ITeleportEvent;

public class TeleportEvent extends AbstractTeleportEvent<ITeleportEvent> {
    public TeleportEvent(ITeleportEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
