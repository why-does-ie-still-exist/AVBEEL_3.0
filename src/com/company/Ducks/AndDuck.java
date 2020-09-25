package com.company.Ducks;

import com.company.Duck;

import java.util.ArrayList;

public class AndDuck {
    public AndDuck() {
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) {
        if (ducks.size() >= 3) {
            Boolean first = (Boolean) ducks.get(1).value();
            Boolean second = (Boolean) ducks.get(2).value();
            ducks.set(0, new Duck(new DuckBool(first && second)));
            ducks.remove(1);
            ducks.remove(1);
            return ducks;
        }
        return ducks;
    }

    public String value() {
        return "and";
    }
}
