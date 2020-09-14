package com.company.Ducks;

import com.company.Duck;

import java.util.ArrayList;

public class NotDuck {
    public NotDuck() {
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) {
        if (ducks.size() >= 2) {
            var first = (boolean) ducks.get(1).value();
            ducks.set(0, new Duck(new DuckBool(!first)));
            ducks.remove(1);
            return ducks;
        }
        return ducks;
    }

    public String value() {
        return "not";
    }
}
