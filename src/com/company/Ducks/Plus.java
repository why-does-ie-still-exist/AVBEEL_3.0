package com.company.Ducks;

import com.company.Duck;

import java.util.ArrayList;

public class Plus {
    public Plus() {
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) {
        if (ducks.size() >= 3) {
            var first = ducks.get(1).value();
            var second = ducks.get(2).value();
            ducks.set(0, new Duck(new DuckInt(((Number) first).intValue() + ((Number) second).intValue())));
            ducks.remove(1);
            ducks.remove(1);
            return ducks;
        }
        return ducks;
    }

    public String value() {
        return "+";
    }
}
