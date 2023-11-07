package me.Thelnfamous1.huskhomes.event;

import net.minecraft.world.InteractionResult;
import net.william278.huskhomes.event.Cancellable;

public abstract class CancellableForgeHuskHomesEvent<E extends Cancellable> extends ForgeHuskHomesEvent<E> {
    public CancellableForgeHuskHomesEvent(E wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    public void setInvokeResult(InteractionResult result) {
        if (!this.wrappedEvent.isCancelled() && this.canCancelWithResult(result)) {
            this.wrappedEvent.setCancelled(true);
            super.setInvokeResult(result);
        }
    }

    protected boolean canCancelWithResult(InteractionResult result) {
        return result != InteractionResult.PASS;
    }

    @Override
    public InteractionResult getInvokeResult() {
        if (this.wrappedEvent.isCancelled()) {
            return this.getCancellationResult();
        }
        return super.getInvokeResult();
    }

    protected InteractionResult getCancellationResult() {
        return InteractionResult.CONSUME;
    }
}
