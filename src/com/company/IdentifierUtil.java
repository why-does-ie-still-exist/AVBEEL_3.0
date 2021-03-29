package com.company;

import com.company.Ducks.IdentifierDuck;

import java.util.ArrayList;

public class IdentifierUtil {
    public static ArrayList<Duck> resolveIdentifiers(ArrayList<Duck> workspace) {
        Duck duck;
        String id;
        for (int i = 0; i < workspace.size(); i++) {
            duck = workspace.get(i);
            if (duck.getNotyetduck() instanceof IdentifierDuck) {
                id = (String) duck.value();
                workspace.remove(i);
                IdentifierDuck identifer = (IdentifierDuck) Main.identifiers.get(id).getNotyetduck();
                workspace.add(i, identifer.resolve());
            }
        }
        return workspace;
    }
}
