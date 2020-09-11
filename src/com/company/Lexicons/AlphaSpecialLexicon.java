package com.company.Lexicons;

import com.company.Duck;
import com.company.Ducks.*;

import java.util.function.Function;

public enum AlphaSpecialLexicon {
    IntegerPlus(new String[]{"+", "plus"}, (String s) -> (new Duck(new Plus()))
    ),
    IntegerMinus(new String[]{"-", "minus"}, (String s) -> (new Duck(new Minus()))
    ),
    IntegerTimes(new String[]{"*", "times"}, (String s) -> (new Duck(new Times()))
    ),
    IntegerDiv(new String[]{"/", "divide"}, (String s) -> (new Duck(new Divide()))
    ),
    Boolean(new String[]{"true","false"}, (String s)-> (new Duck(new DuckBool(s))));

    private final String[] names;
    private final Function<String, Duck> converter;

    AlphaSpecialLexicon(String[] names, Function<String, Duck> converter) {
        this.names = names;
        this.converter = converter;
    }

    public String[] getNames() {
        return this.names;
    }

    public Function<String, Duck> getConverter() {
        return this.converter;
    }
}
