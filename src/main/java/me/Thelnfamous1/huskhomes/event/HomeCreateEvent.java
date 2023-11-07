package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IHomeCreateEvent;

public class HomeCreateEvent extends CancellableForgeHuskHomesEvent<IHomeCreateEvent> {
    public HomeCreateEvent(IHomeCreateEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
