package com.company.Ducks;

import com.company.Duck;
import com.company.Main;

import java.util.ArrayList;

public class SetTimeDuck {
    public SetTimeDuck() {
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) {
        if (ducks.size() >= 2) {
            var first = (int) ducks.get(1).value();
            Main.setCurrenttime(first);
        }
        return ducks;
    }

    public String value() {
        return "settime";
    }
}
