package com.company.SpecialDucks;

import com.company.*;
import com.company.Ducks.DuckBool;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static com.company.FakeCloner.maybeFakeClone;

public class IfElseDuck {
    public final Pattern pattern = Pattern.compile("ifl[\\s]+[(]([^()]+)[)][\\s]+[(]([^()]+)[)][\\s]+else[\\s]+[(]([^()]+)[)]");
    private final String condition;
    private final String truebody;
    private final String falsebody;
    private ArrayList<Duck> parsedcond;
    private ArrayList<Duck> parsedtruebody;
    private ArrayList<Duck> parsedfalsebody;
    private boolean initialized = false;

    public IfElseDuck(String definition) {
        var matcher = this.pattern.matcher(definition);
        matcher.matches();
        this.condition = matcher.group(1);
        this.truebody = matcher.group(2);
        this.falsebody = matcher.group(3);
    }

    public IfElseDuck() {
        this.condition = null;
        this.truebody = null;
        this.falsebody = null;
    }

    private void initialize() {
        this.initialized = true;
        this.parsedcond = Lexer.staticParse(condition, Main.identifiers);
        this.parsedtruebody = Lexer.staticParse(truebody, Main.identifiers);
        this.parsedfalsebody = Lexer.staticParse(falsebody, Main.identifiers);
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> input) throws FakeCloneException {
        if (!this.initialized) initialize();
        input.remove(0);
        if (evaluateCond()) {
            ArrayList<Duck> workspace = new ArrayList<Duck>(maybeFakeClone(parsedtruebody));
            workspace = IdentifierUtil.resolveIdentifiers(workspace);
            input.addAll(0, Interpreter.interpret(workspace));
        } else {
            ArrayList<Duck> workspace = new ArrayList<Duck>(maybeFakeClone(parsedfalsebody));
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
        if (first.notADuck instanceof DuckBool) {
            return (boolean) first.value();
        }
        throw new IllegalArgumentException("Cond did not evaluate to boolean");
    }

}
