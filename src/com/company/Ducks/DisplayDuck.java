package com.company.Ducks;

import com.company.Duck;

import java.util.ArrayList;

public class DisplayDuck {
    public DisplayDuck() {
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) {
        ducks.remove(0);
        System.out.println(ducks.get(0).value());
        ducks.remove(0);
        return ducks;
    }

    public String value() {
        return "display";
    }
}
