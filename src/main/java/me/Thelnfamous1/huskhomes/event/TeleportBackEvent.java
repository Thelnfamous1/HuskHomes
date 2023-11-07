package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.ITeleportBackEvent;

public class TeleportBackEvent extends AbstractTeleportEvent<ITeleportBackEvent> {
    public TeleportBackEvent(ITeleportBackEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
