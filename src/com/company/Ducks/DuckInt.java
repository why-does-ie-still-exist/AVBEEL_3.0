package com.company.Ducks;

import com.company.Duck;
import com.company.Pair;

import java.util.ArrayList;

public class DuckInt {
    private final Integer integer;

    public DuckInt(Integer integer) {
        this.integer = integer;
    }

    public Pair<ArrayList<Duck>, Integer> apply(ArrayList<Duck> ducks, Integer pos) {
        return new Pair<ArrayList<Duck>, Integer>(ducks, pos);
    }

    public Integer value() {
        return this.integer;
    }
}
