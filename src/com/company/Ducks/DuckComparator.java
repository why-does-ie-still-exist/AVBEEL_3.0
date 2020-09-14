package com.company.Ducks;

import com.company.Duck;

import java.util.ArrayList;

public class DuckComparator {
    public final boolean direction;

    public DuckComparator(boolean direction) {
        //true = >  false = <
        this.direction = direction;
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) {
        if (ducks.size() >= 3) {
            Integer first = (Integer) ducks.get(1).value();
            Integer second = (Integer) ducks.get(2).value();
            boolean leftIsGreater = first > second;
            ducks.set(0, new Duck(new DuckBool(this.direction == leftIsGreater)));
            ducks.remove(1);
            ducks.remove(1);
            return ducks;
        }
        return ducks;
    }
}
