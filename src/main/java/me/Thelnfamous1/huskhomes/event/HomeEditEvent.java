package me.Thelnfamous1.huskhomes.event;

import net.william278.huskhomes.event.IHomeEditEvent;

public class HomeEditEvent extends CancellableForgeHuskHomesEvent<IHomeEditEvent> {
    public HomeEditEvent(IHomeEditEvent wrappedEvent) {
        super(wrappedEvent);
    }

}
