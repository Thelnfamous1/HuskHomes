package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IWarpEditEvent;

public class WarpEditEvent extends CancellableForgeHuskHomesEvent<IWarpEditEvent> {
    public WarpEditEvent(IWarpEditEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
