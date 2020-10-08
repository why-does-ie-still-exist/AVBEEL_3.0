package com.company.Ducks;

import com.company.Duck;

import java.util.ArrayList;

public class Divide {
    public Divide() {
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) {
        if (ducks.size() >= 3) {
            Number first = (Number) ducks.get(1).value();
            Number second = (Number) ducks.get(2).value();
            ducks.set(0, new Duck(new DuckInt(first.intValue() / second.intValue())));
            ducks.remove(1);
            ducks.remove(1);
            return ducks;
        }
        return ducks;
    }

    public String value() {
        return "/";
    }
}
