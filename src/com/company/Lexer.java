package com.company;

import com.company.Lexicons.TokenPatternLexicon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

import static com.company.Main.*;

public class Lexer {

    public static ArrayList<Duck> staticParse(String s) {
        return staticParse(s, new HashMap<String, Duck>(), false);
    }

    public static ArrayList<Duck> staticParse(String s, HashMap<String, Duck> staticcustomstore) {
        return staticParse(s, staticcustomstore, true);
    }

    public static ArrayList<Duck> staticParse(String s, HashMap<String, Duck> staticcustomstore, boolean staticiscustomstore) {
        var p = definitionize(s);
        var list = staticTokenizeList(p, staticcustomstore, staticiscustomstore);
        return list;
    }

    private static Duck tokenize(String s, HashMap<String, Duck> staticcustomstore, boolean staticiscustomstore) {
        Function converter = null;
        if (Character.isDigit(s.charAt(0))) {
            converter = globaldigitstore.get(s);
        } else if (converter == null) {
            converter = globalalphastore.get(s);
        }
        if (staticiscustomstore && converter == null) {
            var custom = staticcustomstore.get(s);
            if (custom != null) {
                return custom;
            }
        }
        if (converter == null) {
            Duck nemrequest = globaldefs.get(s);
            if (nemrequest != null) {
                return nemrequest;
            }
        }
        if (converter == null) {
            Matcher matcher;
            for (TokenPatternLexicon enu : globaltokenpatterns
            ) {
                matcher = enu.getPattern().matcher(s);
                if (matcher.matches()) converter = enu.getConverter();
            }
        }
        if (converter == null) {
            throw new IllegalArgumentException(new StringBuilder("token s does not exist in lexicons: ").append(s).toString());
        }
        Duck convertedDuck = ((Duck) converter.apply(s));
        return convertedDuck;
    }

    public static String definitionize(String string) {
        var tba = new StringBuffer(string);
        var namestofuncs = new HashMap<String, Duck>();
        boolean foundAny = false;
        boolean firstPass = true;
        while (foundAny || firstPass) { //could just pass what stores I haven't already iterated over and process string and then recur.
            if (firstPass) firstPass = false;
            foundAny = false;
            for (var store : globaldefstore) {
                Matcher m = store.getPattern().matcher(string);
                Function<String, Duck> converter = store.getConverter();
                boolean toinline = store.shouldInline();
                int start;
                int end;
                String sstr;
                String name;
                for (Object o : m.results().toArray()) { //redo this logic with matcher replace method
                    foundAny = true;
                    start = ((MatchResult) o).start();
                    end = ((MatchResult) o).end();
                    sstr = tba.substring(start, end);
                    Duck duck = converter.apply(sstr);
                    name = (String) duck.value();
                    namestofuncs.put(name, duck);
                    if (toinline) {
                        string = String.valueOf(tba.replace(start, end, name));
                    } else {
                        string = String.valueOf(tba.delete(start, end));
                    }
                }
            }
        }
        globaldefs.putAll(namestofuncs);
        return tba.toString();
    }

    public static ArrayList<Duck> staticTokenizeList(String s, HashMap<String, Duck> staticcustomstore) {
        return staticTokenizeList(s, staticcustomstore, true);
    }

    public static ArrayList<Duck> staticTokenizeList(String s) {
        return staticTokenizeList(s, new HashMap<String, Duck>(), false); //thanks, lazy instantiation!
    }

    public static ArrayList<Duck> staticTokenizeList(String s, HashMap<String, Duck> staticcustomstore, boolean staticiscustomstore) {
        var stringarr = s.split("\\s");
        var newlist = new ArrayList<Duck>();
        for (String term : stringarr) {
            if (!term.equals("")) {
                newlist.add(tokenize(term, staticcustomstore, staticiscustomstore));
            }
        }
        return newlist;
    }
}
