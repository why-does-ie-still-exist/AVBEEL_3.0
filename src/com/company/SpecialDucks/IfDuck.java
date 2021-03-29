package com.company.SpecialDucks;

import com.company.*;
import com.company.Ducks.DuckBool;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static com.company.FakeCloner.maybeFakeClone;

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

    public IfDuck() {
        this.condition = null;
        this.body = null;
    }

    private void initialize() {
        this.initialized = true;
        this.parsedcond = Lexer.staticParse(condition, Main.identifiers);
        this.parsedbody = Lexer.staticParse(body, Main.identifiers);
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> input) throws FakeCloneException {
        if (!this.initialized) initialize();
        input.remove(0);
        if (evaluateCond()) {
            ArrayList<Duck> workspace = new ArrayList<Duck>(maybeFakeClone(parsedbody));
            workspace = IdentifierUtil.resolveIdentifiers(workspace);
            input.addAll(0, Interpreter.interpret(workspace));
        }
        return input;
    }

    private boolean evaluateCond() throws FakeCloneException {
        ArrayList<Duck> workspace = new ArrayList<Duck>(maybeFakeClone(parsedcond));
        workspace = IdentifierUtil.resolveIdentifiers(workspace);
        ArrayList<Duck> clones = Interpreter.interpret(workspace);
        Duck first = clones.get(0);
        if (first.getNotyetduck() instanceof DuckBool) {
            return (boolean) first.value();
        }
        throw new IllegalArgumentException("Cond did not evaluate to boolean");
    }

}
