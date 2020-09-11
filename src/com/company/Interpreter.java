package com.company;

import java.util.ArrayList;

public class Interpreter {

    public static ArrayList<Duck> interpret(ArrayList<Duck> tokens) {
        var currentsize = tokens.size();
        Pair<ArrayList<Duck>, Integer> result;
        Duck token;
        for (Integer pos = currentsize - 1; pos >= 0; pos--) {
            token = tokens.get(pos);
            if (!token.getIgnoreFirstPass()) {
                result = token.apply(tokens, pos);
                tokens = result.getFirst();
                pos = result.getSecond();
                currentsize = tokens.size();
            } else {
                token.setIgnoreFirstPass(false);
            }
        }
        return tokens;
    }

    public static String stringify(ArrayList<Duck> computed) {
        var builder = new StringBuilder();
        for (Duck duck : computed
        ) {
            builder.append(duck.value());
            builder.append(' ');
        }
        return builder.toString();
    }
}
