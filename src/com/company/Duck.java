package com.company;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Duck {

    public final Class<?> notADuckType;
    public final Object notADuck;
    private Method applydel;
    private boolean simple;
    private Method valuedel;
    private boolean ignoreFirstPass;

    public Duck(Object notADuck) {
        this.notADuck = notADuck;
        this.notADuckType = notADuck.getClass();
        initialize();
    }

    public Duck(Object notADuck, boolean ignoreFirstPass) {
        this.notADuck = notADuck;
        this.notADuckType = notADuck.getClass();
        initialize();
    }

    public boolean getIgnoreFirstPass() {
        return ignoreFirstPass;
    }

    public void setIgnoreFirstPass(boolean ignorefirstpass) {
        this.ignoreFirstPass = ignorefirstpass;
    }

    private void initialize() {
        this.simple = false;
        for (Method method : notADuckType.getMethods()) {
            switch (method.getName()) {
                case "apply":
                    this.applydel = method;
                    break;
                case "simpleapply":
                    this.simple = true;
                    this.applydel = method;
                    break;
                case "value":
                    this.valuedel = method;
                    break;
            }
        }
        if (this.applydel == null && !this.simple) {
            throw new IllegalArgumentException(new StringBuilder("Both apply and simpleapply missing for: ").append(notADuckType.getName()).toString());
        }
    }


    public Pair<ArrayList<Duck>, Integer> apply(ArrayList<Duck> args, Integer pos) {
        try {
            if (!this.simple) {
                return (Pair<ArrayList<Duck>, Integer>) this.applydel.invoke(this.notADuck, args, pos);
            }
            var subargs = new ArrayList<Duck>(args.subList(pos, args.size()));
            var processed = (ArrayList<Duck>) this.applydel.invoke(this.notADuck, subargs);
            processed.addAll(0, args.subList(0, pos));
            return new Pair<ArrayList<Duck>, Integer>(processed, pos);

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(new StringBuilder().append("Issues with apply/simpleapply method in: ").append(notADuckType.getName()).toString());
    }

    public Object value() {
        if(this.valuedel == null) return this.notADuckType.getName();
        try {
            return this.valuedel.invoke(this.notADuck);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(new StringBuilder().append("Issues with value method in: ").append(notADuckType.getName()).toString());
    }

    public Object getInternalDuck() {
        return notADuck;
    }

    public Class<?> getInternalDuckType() {
        return notADuckType;
    }

}
