package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IReplyTeleportRequestEvent;

public class ReplyTeleportRequestEvent extends CancellableForgeHuskHomesEvent<IReplyTeleportRequestEvent> {
    public ReplyTeleportRequestEvent(IReplyTeleportRequestEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
