package com.company.Ducks;

import com.company.Duck;
import com.company.Main;

import java.util.ArrayList;

public class WaitDuck {
    public WaitDuck() {
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) throws InterruptedException {
        if (ducks.size() >= 2) {
            var first = (int) ducks.get(1).value();
            int now = Main.getCurrentTime();
            int towait = first - now;
            if (towait > 0) Thread.sleep(towait);
            ducks.remove(0);
            ducks.remove(0);
        }
        return ducks;
    }

    public String value() {
        return "wait";
    }
}
