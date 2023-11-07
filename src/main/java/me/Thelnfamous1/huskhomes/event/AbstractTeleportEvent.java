package me.Thelnfamous1.huskhomes.event;

import net.minecraft.world.InteractionResult;
import net.william278.huskhomes.event.Cancellable;

public abstract class AbstractTeleportEvent<E extends Cancellable> extends CancellableForgeHuskHomesEvent<E> {
    public AbstractTeleportEvent(E wrappedEvent) {
        super(wrappedEvent);
    }

    @Override
    protected boolean canCancelWithResult(InteractionResult result) {
        return result == InteractionResult.FAIL;
    }

    @Override
    protected InteractionResult getCancellationResult() {
        return InteractionResult.FAIL;
    }
}
