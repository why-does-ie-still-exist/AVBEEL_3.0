package com.company.Ducks;

import com.company.Duck;
import com.company.Pair;

import java.util.ArrayList;

public class DuckBool {
    private final boolean aBoolean;

    public DuckBool(String sBoolean) {
        aBoolean = sBoolean.equals("true");
    }

    public DuckBool(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public Pair<ArrayList<Duck>, Integer> apply(ArrayList<Duck> ducks, Integer pos) {
        return new Pair<ArrayList<Duck>, Integer>(ducks, pos);
    }

    public boolean value() {
        return this.aBoolean;
    }
}
