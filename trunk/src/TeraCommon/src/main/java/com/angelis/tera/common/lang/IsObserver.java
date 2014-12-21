package com.angelis.tera.common.lang;

public interface IsObserver<E extends Eventable> {
    public void onObservableUpdate(E event, IsObservable<E> observable, Object... data);
}
