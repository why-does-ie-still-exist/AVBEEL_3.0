package com.company.SpecialDucks;

import com.company.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class FinalDuck {
    public final Pattern pattern = Pattern.compile("final\\s+([^\\s()]+)\\s+[(]([^()]*)[)]");
    private ArrayList<Duck> parsedbody;
    private String name;

    public FinalDuck(String definition) {
        var matcher = this.pattern.matcher(definition);
        matcher.matches();
        this.name = matcher.group(1);
        this.parsedbody = Interpreter.interpret(Lexer.staticParse(matcher.group(2), Main.identifiers));
    }

    public FinalDuck() {
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> input) {
        input.remove(0);
        input.addAll(0, this.parsedbody);
        return input;
    }

    public String value() {
        return this.name;
    }

}
