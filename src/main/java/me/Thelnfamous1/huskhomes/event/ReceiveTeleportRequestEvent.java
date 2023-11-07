package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IReceiveTeleportRequestEvent;

public class ReceiveTeleportRequestEvent extends CancellableForgeHuskHomesEvent<IReceiveTeleportRequestEvent> {
    public ReceiveTeleportRequestEvent(IReceiveTeleportRequestEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
