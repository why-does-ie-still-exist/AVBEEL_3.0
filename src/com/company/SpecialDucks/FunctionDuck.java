package com.company.SpecialDucks;

import com.company.*;
import com.company.Ducks.IdentifierDuck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;
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

    public FunctionDuck(){
    }

    private void initialize(){
        this.isInitialized = true;
        Duck idduck;
        for (String arg :
                this.strarguments) {
            idduck = new Duck(new IdentifierDuck(arg));
            Main.identifiers.put(arg,idduck);
        }
        this.parsedbody = Lexer.staticParse(this.strtoeval, Main.identifiers);
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> input) throws FakeCloneException {
        if(! this.isInitialized) initialize();

        //could take into consideration whether functions in body are already defined so it doesn't re-run the definition.
        input.remove(0);
        for (int i = 0; i < numargs; i++) {
            ((IdentifierDuck) Main.identifiers.get((strarguments.get(i))).notADuck).set(input.get(i));
        }
        for (int i = 0; i < numargs; i++) {
            input.remove(0);
        }
        var workspace = new ArrayList<Duck>((Collection) fakeClone(this.parsedbody));
        workspace = Interpreter.interpret(IdentifierUtil.resolveIdentifiers(workspace));
        input.addAll(0, workspace);
        return input;
    }

    public String value() {
        return this.name;
    }

}
