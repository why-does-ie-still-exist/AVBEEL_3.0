package com.company.SpecialDucks;

import com.company.Duck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class ScriptDuck {
    private final String scriptlocation;
    private boolean hasRun;
    private String scriptout;

    public ScriptDuck(String scriptlocation) {
        var pattern = Pattern.compile("py\\s+[(]([^()]+)[)]");
        var matcher = pattern.matcher(scriptlocation);
        matcher.find();
        this.scriptlocation = matcher.group(1);
        this.hasRun = false;
    }

    public static String runExternalScript(String location) throws IOException {
        Process script = runPyScript(location);
        return getOutput(script);
    }

    public static String getOutput(Process p) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));
        var buffer = new StringBuffer();
        for (String s : stdInput.lines().toArray(String[]::new)) buffer.append(s);
        return buffer.toString();
    }

    public static Process runPyScript(String scriptlocation) throws IOException {
        return Runtime.getRuntime().exec(new String[]{"python", scriptlocation});
    }

    public ArrayList<Duck> simpleapply(ArrayList<Duck> ducks) throws IOException {
        this.scriptout = runExternalScript(scriptlocation);
        this.hasRun = true;
        System.out.println(this.scriptout);
        return ducks;
    }

    public String value() {
        if (this.hasRun) {
            return scriptout;
        } else {
            return scriptlocation.replaceAll("\\s", "");
        }
    }
}
