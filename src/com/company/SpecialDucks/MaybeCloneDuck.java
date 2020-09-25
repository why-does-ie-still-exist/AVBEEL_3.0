package com.company.SpecialDucks;

import com.company.Duck;

import java.util.ArrayList;

public class MaybeCloneDuck {

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) {
        ducks.remove(0);
        return ducks;
    }

    public String value() {
        return "clone";
    }
}
