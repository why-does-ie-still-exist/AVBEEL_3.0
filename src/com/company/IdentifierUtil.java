package com.company;

import com.company.Ducks.IdentifierDuck;

import java.util.ArrayList;

public class IdentifierUtil {
    public static ArrayList<Duck> resolveIdentifiers( ArrayList<Duck> workspace){
        Duck value;
        String id;
        for (int i = 0; i < workspace.size(); i++) {
            value = workspace.get(i);
            if (value.notADuck instanceof IdentifierDuck) {
                id = (String) value.value();
                workspace.remove(i);
                IdentifierDuck identifer = (IdentifierDuck) Main.identifiers.get(id).notADuck;
                workspace.add(i,identifer.resolve());
            }
        }
        return workspace;
    }
}
