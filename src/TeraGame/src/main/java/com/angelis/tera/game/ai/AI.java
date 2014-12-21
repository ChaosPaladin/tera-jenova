package com.angelis.tera.game.ai;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import javolution.util.FastMap;

import com.angelis.tera.game.ai.desire.Desire;
import com.angelis.tera.game.ai.events.Event;
import com.angelis.tera.game.ai.events.IEventHandler;
import com.angelis.tera.game.ai.state.IStateHandler;
import com.angelis.tera.game.ai.state.enums.AIStateEnum;
import com.angelis.tera.game.process.model.visible.VisibleTeraObject;
import com.angelis.tera.game.services.ThreadPoolService;

public abstract class AI<O extends VisibleTeraObject> implements Runnable {

    protected final O owner;

    protected Map<Event, IEventHandler> eventHandlers = new FastMap<Event, IEventHandler>();
    protected Map<AIStateEnum, IStateHandler> stateHandlers = new FastMap<AIStateEnum, IStateHandler>();
    private final Queue<Desire> desireQueue = new PriorityBlockingQueue<>();
    protected AIStateEnum aiState = AIStateEnum.NONE;
    private boolean isStateChanged;
    private Future<?> aiTask;

    public AI(final O visibleTeraObject) {
        this.owner = visibleTeraObject;
    }

    protected void addEventHandler(final IEventHandler eventHandler) {
        this.eventHandlers.put(eventHandler.getEvent(), eventHandler);
    }

    public void clearEventHandler() {
        this.eventHandlers.clear();
    }

    protected void addStateHandler(final IStateHandler stateHandler) {
        this.stateHandlers.put(stateHandler.getState(), stateHandler);
    }

    public void clearStateHandler() {
        this.stateHandlers.clear();
    }

    public void setAiState(final AIStateEnum aiState) {
        if (this.aiState != aiState) {
            this.aiState = aiState;
            this.isStateChanged = true;
        }
    }

    public void analyzeState() {
        this.isStateChanged = false;
        final IStateHandler stateHandler = stateHandlers.get(aiState);
        if (stateHandler != null) {
            stateHandler.handleState(aiState, this);
        }
    }

    @Override
    public final void run() {
        synchronized (this.owner) {
            if (this.desireQueue.isEmpty() || this.isStateChanged) {
                analyzeState();
            }
        }
    }

    public final void schedule() {
        if (!this.isScheduled()) {
            this.aiTask = ThreadPoolService.getInstance().scheduleAtFixedRate(this, 1000, 1000, TimeUnit.SECONDS);
        }
    }

    public boolean isScheduled() {
        return this.aiTask != null && !this.aiTask.isCancelled();
    }

    public final void cancel() {
        this.aiTask.cancel(false);
    }

    public void addDesire(final Desire desire) {
        final Iterator<Desire> itr = this.desireQueue.iterator();
        while (itr.hasNext()) {
            final Desire iterated = itr.next();
            if (iterated.equals(desire)) {
                itr.remove();

                // Increase this desire
                if (desire != iterated) {
                    desire.increaseDesirePower(iterated.getDesirePower());
                }

                break;
            }
        }

        this.desireQueue.add(desire);
    }
}
