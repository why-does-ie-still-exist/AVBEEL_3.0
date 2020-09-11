package com.company.Lexicons;

import com.company.Duck;

import java.util.function.Function;

public enum DigitLexicon {
    ;
    private final String[] names;
    private final Function<String, Duck> converter;

    DigitLexicon(String[] names, Function<String, Duck> converter) {
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
