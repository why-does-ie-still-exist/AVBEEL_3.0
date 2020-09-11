package com.company;

import com.company.Ducks.DuckBool;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class IfDuck {
    public final Pattern pattern = Pattern.compile("if[\\s]+[(]([^()]+)[)][\\s]+[(]([^()]+)[)]");
    private final String condition;
    private final String body;
    private ArrayList<Duck> parsedcond;
    private ArrayList<Duck> parsedbody;
    private boolean initialized = false;

    public IfDuck(String definition) {
        var matcher = this.pattern.matcher(definition);
        matcher.matches();
        this.condition = matcher.group(1);
        this.body = matcher.group(2);
    }

    private void initialize(){
        this.initialized = true;
        this.parsedcond = Lexer.staticParse(condition);
        this.parsedbody = Lexer.staticParse(body);
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> input) throws FakeCloneException {
        if( ! this.initialized) initialize();
        input.remove(0);
        if(evaluateCond()) {
            input.addAll(0, Interpreter.interpret((ArrayList<Duck>) FakeCloner.fakeClone(parsedbody)));
        }
        return input;
    }
    private boolean evaluateCond() throws FakeCloneException {
        ArrayList<Duck> clones = Interpreter.interpret((ArrayList<Duck>) FakeCloner.fakeClone(parsedcond));
        Duck first = clones.get(0);
        if(first.notADuck instanceof DuckBool){
            return (boolean) first.value();
        }
        throw new IllegalArgumentException("Cond did not evaluate to boolean");
    }

}
