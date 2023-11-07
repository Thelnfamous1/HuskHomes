package me.Thelnfamous1.huskhomes.event;

import net.minecraft.world.InteractionResult;
import net.minecraftforge.eventbus.api.Event;
import net.william278.huskhomes.event.HuskHomesEvent;

public abstract class ForgeHuskHomesEvent<E extends HuskHomesEvent> extends Event {

    protected final E wrappedEvent;
    private InteractionResult invokeResult = InteractionResult.PASS;

    public ForgeHuskHomesEvent(E wrappedEvent){
        this.wrappedEvent = wrappedEvent;
    }

    public E getWrappedEvent() {
        return wrappedEvent;
    }

    public InteractionResult getInvokeResult() {
        return this.invokeResult;
    }

    public void setInvokeResult(InteractionResult interactionResult) {
        this.invokeResult = interactionResult;
    }
}
