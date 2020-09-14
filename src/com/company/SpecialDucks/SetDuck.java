package com.company.SpecialDucks;

import com.company.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SetDuck {
    public final Pattern pattern = Pattern.compile("set\\s+([^\\s()]+)\\s+[(]([^()]*)[)]");
    private String strtoeval;
    private ArrayList<Duck> parsedbody;
    private String name;
    private boolean isInitialized;

    public SetDuck(String definition) {
        var matcher = this.pattern.matcher(definition);
        matcher.matches();
        this.name = matcher.group(1);
        this.strtoeval = matcher.group(2);
    }

    public SetDuck() {
    }

    private void initialize() {
        this.isInitialized = true;
        this.parsedbody = Lexer.staticParse(this.strtoeval, Main.identifiers);
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> input) throws FakeCloneException {
        if (!this.isInitialized) initialize();
        input.remove(0);
        var workspace = new ArrayList<Duck>(this.parsedbody);
        workspace = Interpreter.interpret(IdentifierUtil.resolveIdentifiers(workspace));
        input.addAll(0, workspace);
        return input;
    }

    public String value() {
        return this.name;
    }

}
