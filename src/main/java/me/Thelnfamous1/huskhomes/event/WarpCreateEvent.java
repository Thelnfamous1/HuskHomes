package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IWarpCreateEvent;

public class WarpCreateEvent extends CancellableForgeHuskHomesEvent<IWarpCreateEvent> {
    public WarpCreateEvent(IWarpCreateEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
