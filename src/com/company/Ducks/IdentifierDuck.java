package com.company.Ducks;

import com.company.Duck;
import com.company.Pair;

import java.util.ArrayList;

public class IdentifierDuck {
    private String identifier;
    private boolean isSet = false;
    private Duck value;

    public IdentifierDuck(String identifier) {
        this.identifier = identifier;
    }

    public IdentifierDuck() {
    }

    public Pair<ArrayList<Duck>, Integer> apply(ArrayList<Duck> ducks, Integer pos) {
        return new Pair<ArrayList<Duck>, Integer>(ducks, pos);
    }

    public String value() {
        return this.identifier;
    }

    public void set(Duck d) {
        this.isSet = true;
        this.value = d;
    }

    public void unset(Duck d) {
        this.isSet = false;
    }

    public Duck resolve() {
        if (this.isSet) {
            return this.value;
        } else {
            throw new UnsupportedOperationException("Identifier: " + this.identifier + " not set to a value");
        }
    }
}
