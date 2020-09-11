package com.company.Ducks;

import com.company.Duck;
import com.company.Pair;

import java.util.ArrayList;

public class IdentifierDuck {
    private final String identifier;

    public IdentifierDuck(String identifier) {
        this.identifier = identifier;
    }

    public Pair<ArrayList<Duck>, Integer> apply(ArrayList<Duck> ducks, Integer pos) {
        return new Pair<ArrayList<Duck>, Integer>(ducks, pos);
    }

    public String value() {
        return this.identifier;
    }
}
