package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IHomeDeleteEvent;

public class HomeDeleteEvent extends CancellableForgeHuskHomesEvent<IHomeDeleteEvent> {
    public HomeDeleteEvent(IHomeDeleteEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
