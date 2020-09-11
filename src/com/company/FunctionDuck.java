package com.company;

import com.company.Ducks.IdentifierDuck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.company.FakeCloner.fakeClone;

public class FunctionDuck {
    public final Pattern pattern = Pattern.compile("define\\s+([^\\s()]+)\\s+[(]([^()]*)[)]\\s+[(]([^()]*)[)]");
    private String strtoeval;
    private ArrayList<Duck> parsedbody;
    private Integer numargs;
    private final ArrayList<String> strarguments = new ArrayList<String>();
    private String name;
    private boolean isInitialized = false;

    public FunctionDuck(String definition) {
        var matcher = this.pattern.matcher(definition);
        matcher.matches();
        this.name = matcher.group(1);
        String strarguments = matcher.group(2);
        this.strtoeval = matcher.group(3);
        var preargs = strarguments.split("[\\s]");
        Collections.addAll(this.strarguments, preargs);
        this.strarguments.removeAll(Collections.singletonList(""));
        this.numargs = this.strarguments.size();
    }

    private void initialize(){
        this.isInitialized = true;
        this.parsedbody = Lexer.staticParse(this.strtoeval, getArguments());
    }

    public HashMap<String, Duck> getArguments() {
        var lex = new HashMap<String, Duck>();
        Duck duck;
        for (String arg :
                this.strarguments) {
            duck = new Duck(new IdentifierDuck(arg));
            lex.put(arg, duck);
        }
        return lex;
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> input) throws FakeCloneException {
        if(! this.isInitialized) initialize();
        input.remove(0);
        HashMap<String, Duck> argvalues = new HashMap<String, Duck>();
        for (int i = 0; i < numargs; i++) {
            argvalues.put(strarguments.get(i), input.get(i));
        }
        for (int i = 0; i < numargs; i++) {
            input.remove(0);
        }
        var workspace = new ArrayList<Duck>((Collection) fakeClone(this.parsedbody));
        Duck value;
        String id;
        for (int i = 0; i < workspace.size(); i++) {
            value = workspace.get(i);
            if (value.notADuck instanceof IdentifierDuck) {
                id = (String) value.value();
                if (argvalues.containsKey(id)) {
                    workspace.remove(i);
                    workspace.add(i, argvalues.get(id));
                }
            }
        }
        workspace = Interpreter.interpret(workspace);
        input.addAll(0, workspace);
        return input;
    }

    public String value() {
        return this.name;
    }

}
