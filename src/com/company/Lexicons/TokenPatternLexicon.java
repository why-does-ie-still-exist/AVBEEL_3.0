package com.company.Lexicons;

import com.company.Duck;
import com.company.Ducks.DuckInt;

import java.util.function.Function;
import java.util.regex.Pattern;

public enum TokenPatternLexicon {
    DuckInteger(Pattern.compile("^[0-9]+$"), (String s) -> new Duck(new DuckInt(Integer.valueOf(s))));
    private final Pattern pattern;
    private final Function<String, Duck> converter;

    TokenPatternLexicon(Pattern pattern, Function<String, Duck> converter) {
        this.pattern = pattern;
        this.converter = converter;
    }

    public Pattern getPattern() {
        return this.pattern;
    }

    public Function<String, Duck> getConverter() {
        return this.converter;
    }
}
