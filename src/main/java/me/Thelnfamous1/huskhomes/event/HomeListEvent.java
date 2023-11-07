package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IHomeListEvent;

public class HomeListEvent extends CancellableForgeHuskHomesEvent<IHomeListEvent> {
    public HomeListEvent(IHomeListEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
