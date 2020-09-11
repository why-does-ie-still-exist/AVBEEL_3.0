package com.company;

import com.company.Lexicons.AlphaSpecialLexicon;
import com.company.Lexicons.DigitLexicon;
import com.company.Lexicons.GlobalDefLexicon;
import com.company.Lexicons.TokenPatternLexicon;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;

public class Main {

    public static HashMap<String, Function<String, Duck>> globalalphastore = new HashMap<String, Function<String, Duck>>();
    public static HashMap<String, Function<String, Duck>> globaldigitstore = new HashMap<String, Function<String, Duck>>();
    public static GlobalDefLexicon[] globaldefstore = GlobalDefLexicon.values();
    public static TokenPatternLexicon[] globaltokenpatterns = TokenPatternLexicon.values();
    public static HashMap<String, Duck> globaldefs = new HashMap<String, Duck>();

    public static void main(String[] args) {
        FakeCloner.disableWarning();
        lexerInitialize();
        doREPL();
    }

    public static void doREPL() {
        System.out.println("AVBEEL 3.0 REPL");
        System.out.println("Type \"exit\" to quit or \"reset\" to clear functions");
        Lexer l = new Lexer();
        var running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.print("Enter Code: ");
            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case "exit":
                    running = false;
                    break;
                case "reset":
                    clearFunctions();
                    break;
                default:
                    try {
                        String output = Interpreter.stringify(Interpreter.interpret(Lexer.staticParse(input)));
                        System.out.print("Result: ");
                        System.out.println(output);
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
            }
        }
    }

    public static void clearFunctions() {
        globaldefs.clear();
    }

    private static void lexerInitialize() {
        for (AlphaSpecialLexicon term : AlphaSpecialLexicon.values()) {
            String[] names = term.getNames();
            if (names.length == 1) {
                var first = names[0];
                if (first.charAt(0) == '\'')
                    System.err.println(new StringBuilder("WARNING: Interpreter will not recognize the following because its name starts with a ' :").append(first));
                globalalphastore.put(first, term.getConverter());
            } else {
                for (String name : names
                ) {
                    if (name.charAt(0) == '\'')
                        System.err.println(new StringBuilder("WARNING: Interpreter will not recognize the following because its name starts with a ' :").append(name));
                    globalalphastore.put(name, term.getConverter());
                }
            }
        }
        for (DigitLexicon term : DigitLexicon.values()) {
            if (term.getNames().length == 1) {
                globaldigitstore.put(term.getNames()[0], term.getConverter());
            } else {
                for (String name : term.getNames()
                ) {
                    globalalphastore.put(name, term.getConverter());
                }
            }
        }
    }
}
