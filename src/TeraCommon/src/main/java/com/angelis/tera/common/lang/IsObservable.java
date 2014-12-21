package com.angelis.tera.common.lang;

import java.util.Collection;


public interface IsObservable<E extends Eventable> {
    
    public void addObserver(IsObserver<E> observer);
    public void notifyObservers(E event, Object... data);
    public Collection<IsObserver<E>> getObservers();
}
