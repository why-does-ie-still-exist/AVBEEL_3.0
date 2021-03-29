package com.company;

import com.company.Lexicons.AlphaSpecialLexicon;
import com.company.Lexicons.DigitLexicon;
import com.company.Lexicons.GlobalDefLexicon;
import com.company.Lexicons.TokenPatternLexicon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

public class Main {

    public static HashMap<String, Function<String, Duck>> globalalphastore = new HashMap<String, Function<String, Duck>>();
    public static HashMap<String, Function<String, Duck>> globaldigitstore = new HashMap<String, Function<String, Duck>>();
    public static GlobalDefLexicon[] globaldefstore = GlobalDefLexicon.values();
    public static TokenPatternLexicon[] globaltokenpatterns = TokenPatternLexicon.values();
    public static HashMap<String, Duck> globaldefs = new HashMap<String, Duck>();
    public static HashMap<String, Duck> identifiers = new HashMap<String, Duck>();
    public static Scanner bigscanner = new Scanner(System.in);
    public static boolean replisrunning;
    private static long offset = System.currentTimeMillis();

    public static void main(String[] args) {
        FakeCloner.disableWarning();
        lexerInitialize();
        if (args.length == 0) {
            doREPL();
        } else {
            var working = new ArrayList<Duck>();
            try {
                for (Map.Entry<int[], String> entry : splitfile(args[0]).entrySet()) {
                    try {
                        working = Interpreter.interpret(Lexer.staticParse(entry.getValue()));
                    } catch (Exception e) {
                        System.out.println("Error at lines: " + entry.getKey()[0] + " to " + entry.getKey()[1]);
                        e.printStackTrace();
                        break;
                    }
                }
                System.out.println(Interpreter.stringify(working));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO error with file: " + args[0]);
            }
        }
    }

    public static void doREPL() {
        System.out.println("AVBEEL 3.0 REPL");
        System.out.println("Type \"exit\" to quit, \"reset\" to clear functions, or \"pwd\" to get the pwd");
        Lexer l = new Lexer();
        replisrunning = true;
        while (replisrunning) {
            doReplOnce(true);
        }
    }

    public static ArrayList<Duck> doReplOnce(boolean doesoutput) {
        long starttime;
        long endtime;
        ArrayList<Duck> evaluated = null;
        System.out.print("Enter Code: ");
        String input = bigscanner.nextLine();
        switch (input.toLowerCase()) {
            case "exit":
                replisrunning = false;
                break;
            case "reset":
                reset();
                break;
            case "pwd":
                System.out.println("Working Directory = " + System.getProperty("user.dir")); //thanks https://stackoverflow.com/questions/4871051/how-to-get-the-current-working-directory-in-java
                break;
            default:
                try {
                    starttime = System.nanoTime();
                    evaluated = Interpreter.interpret(Lexer.staticParse(input));
                    if (doesoutput) {
                        String output = Interpreter.stringify(evaluated);
                        endtime = System.nanoTime();
                        System.out.println("Time: <" + ((endtime - starttime) / 1000000) + "ms");
                        System.out.print("Result: ");
                        System.out.println(output);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
        }
        return (evaluated == null) ? new ArrayList<Duck>() : evaluated;
    }

    public static LinkedHashMap<int[], String> splitfile(String couldbepath) throws IOException {
        Path p = Paths.get(couldbepath);
        var f = new File(p.toRealPath().toUri());
        var s = new Scanner(f);
        var map = new LinkedHashMap<int[], String>();
        var currenttext = new StringBuilder();
        String currentline = "";
        int currentlineno = 0;
        int startline = 1;
        int endline = 0;
        while (s.hasNext()) {
            currentlineno++;
            currentline = s.nextLine();
            if (currentline.contains(";")) {
                endline = currentlineno;
                map.put(new int[]{startline, endline}, currenttext.toString() + currentline.split(";")[0]);
                startline = currentlineno + 1;
                currenttext = new StringBuilder();
            } else {
                currenttext.append(currentline).append("\n");
            }
        }
        if (!currenttext.toString().isBlank()) {
            endline = currentlineno;
            map.put(new int[]{startline, endline}, currenttext.toString());
        }
        return map;
    }

    public static void reset() {
        globaldefs.clear();
        identifiers.clear();
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

    public static int getCurrentTime() {
        return (int) (System.currentTimeMillis() - offset);
    }

    public static void setCurrenttime(int timeinms) {
        offset = System.currentTimeMillis() - timeinms;
    }
}
