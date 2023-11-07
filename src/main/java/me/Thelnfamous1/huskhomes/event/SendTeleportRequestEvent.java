package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.ISendTeleportRequestEvent;

public class SendTeleportRequestEvent extends CancellableForgeHuskHomesEvent<ISendTeleportRequestEvent> {
    public SendTeleportRequestEvent(ISendTeleportRequestEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
