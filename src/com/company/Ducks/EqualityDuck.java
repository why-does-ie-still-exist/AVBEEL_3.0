package com.company.Ducks;

import com.company.Duck;

import java.util.ArrayList;

public class EqualityDuck {
    public EqualityDuck() {
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) {
        if (ducks.size() >= 3) {
            var first = ducks.get(1).value();
            var second = ducks.get(2).value();
            if (first.equals(second)) {
                ducks.set(0, new Duck(new DuckBool(true)));
            } else {
                ducks.set(0, new Duck(new DuckBool(false)));
            }
            ducks.remove(1);
            ducks.remove(1);
            return ducks;
        }
        return ducks;
    }

    public String value() {
        return "display";
    }
}
