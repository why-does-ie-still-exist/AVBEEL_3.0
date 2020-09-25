package com.company.Lexicons;

import com.company.Duck;
import com.company.SpecialDucks.*;

import java.util.function.Function;
import java.util.regex.Pattern;

public enum GlobalDefLexicon {
    DefineStatement(Pattern.compile("define[\\s]+[^\\s()]+[\\s]+[(][^()]*[)][\\s]+[(][^()]*[)]"),
            s -> new Duck(new FunctionDuck(s)), false),
    IfElseStatement(Pattern.compile("ifl[\\s]+[(][^()]+[)][\\s]+[(][^()]+[)][\\s]+else[\\s]+[(][^()]+[)]"),
            s -> new Duck(new IfElseDuck(s)), true),
    IfStatement(Pattern.compile("if[\\s]+[(][^()]+[)][\\s]+[(][^()]+[)]"),
            s -> new Duck(new IfDuck(s)), true),
    SetDuck(Pattern.compile("set\\s+[^\\s()]+\\s+[(][^()]*[)]"),
            s -> new Duck(new SetDuck(s)), false);

    private final Pattern pattern;
    private final Function<String, Duck> initializer;
    private final boolean inlined;

    GlobalDefLexicon(Pattern pattern, Function<String, Duck> converter, boolean isinlined) {
        this.pattern = pattern;
        this.initializer = converter;
        this.inlined = isinlined;
    }

    public Pattern getPattern() {
        return this.pattern;
    }

    public Function<String, Duck> getConverter() {
        return this.initializer;
    }

    public boolean shouldInline() {
        return this.inlined;
    }

}
