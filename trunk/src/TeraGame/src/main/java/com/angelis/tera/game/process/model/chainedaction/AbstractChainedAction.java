package com.angelis.tera.game.process.model.chainedaction;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.angelis.tera.game.process.model.player.Player;

public abstract class AbstractChainedAction {
    
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final InnerAbstractChainedAction innerChainedAction = new InnerAbstractChainedAction();
    protected final Player owner;
    
    protected boolean complete;

    public AbstractChainedAction(final Player owner) {
        this.owner = owner;
    }
    
    public final void start() {
        if (!this.check()) {
            return;
        }
        
        this.onStart();
        this.next(1000);
    }

    public void cancel() {
        this.onCancel();
        this.end();
    }
    
    private boolean check() {
        final boolean value = this.onCheck();
        return value;
    }
    
    public final void next(final long delay) {
        executor.schedule(innerChainedAction, delay, TimeUnit.MILLISECONDS);
    }
    
    private final void end() {
        this.executor.shutdownNow();
        this.owner.getController().setChainedAction(null);
    }
    
    private final class InnerAbstractChainedAction extends Thread {
        @Override
        public final void run() {
            AbstractChainedAction.this.onStep();
            
            if (AbstractChainedAction.this.complete) {
                AbstractChainedAction.this.end();
                AbstractChainedAction.this.onEnd();
                return;
            }
        }
    }
    
    public abstract boolean onCheck();
    public abstract void onStart();
    public abstract void onStep();
    public abstract void onEnd();
    public abstract void onCancel();
}
