package com.company.Ducks;

import com.company.Duck;
import com.company.Main;

import java.util.ArrayList;

public class REPLDuck {
    public REPLDuck() {
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) {
        ducks.remove(0);
        ducks.addAll(0, Main.doReplOnce(false));
        return ducks;
    }

    public String value() {
        return "REPL";
    }
}
