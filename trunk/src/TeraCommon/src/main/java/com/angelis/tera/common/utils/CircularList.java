package com.angelis.tera.common.utils;

import java.util.ArrayList;

public class CircularList<E> extends ArrayList<E> {

    private static final long serialVersionUID = 5533815634618536497L;
    
    private int current = 0;

    public CircularList() {}
    
    public E first() {
        return this.get(0);
    }
    
    public E next() {
        if (++current > this.size()) {
            current = 0;
        }
        
        return this.get(current);
    }
    
    public E previous() {
        if (--current < 0) {
            current = this.size()-1;
        }
        
        return this.get(current);
    }
}
