package com.company.Lexicons;

import com.company.Duck;
import com.company.Ducks.*;
import com.company.SpecialDucks.MaybeCloneDuck;

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
    Boolean(new String[]{"true", "false"}, (String s) -> (new Duck(new DuckBool(s)))),
    Display(new String[]{"display"}, (String s) -> (new Duck(new DisplayDuck()))),
    Equals(new String[]{"==", "=", "eq", "equals"}, (String s) -> (new Duck(new EqualityDuck()))),
    Not(new String[]{"!", "not"}, (String s) -> (new Duck(new NotDuck()))),
    Or(new String[]{"or", "||"}, (String s) -> new Duck(new OrDuck())),
    And(new String[]{"and", "&&"}, (String s) -> new Duck(new AndDuck())),
    Comparator(new String[]{">", "<"}, (String s) -> new Duck(new DuckComparator(s.equals(">")))),
    REPL(new String[]{"replinput"}, (String s) -> new Duck(new REPLDuck())),
    CloneIdentifier(new String[]{"clone", "doclone"}, s -> new Duck(new MaybeCloneDuck())),
    Wait(new String[]{"wait"}, s -> new Duck(new WaitDuck())),
    SetTime(new String[]{"settime"}, s -> new Duck(new SetTimeDuck()));

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
