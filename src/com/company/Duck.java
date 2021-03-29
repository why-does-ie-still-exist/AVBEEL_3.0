package com.company;

import com.company.SpecialDucks.MaybeCloneDuck;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

public class Duck {

    public final Class<?> notyetduckclass;
    public final boolean iscloneidentifier; //quick hack to improve performance, don't modify this class like I did. If you want to have all your ducks have a special property, use custom objects in value function.
    private final Object notyetduck;
    private Method applymethoddelegate;
    private boolean issimpleapply;
    private Method valuemethoddelegate;

    public Duck(Object notYetDuck) {
        this.iscloneidentifier = notYetDuck instanceof MaybeCloneDuck;
        this.notyetduck = notYetDuck;
        this.notyetduckclass = notYetDuck.getClass();
        setupmethods();
    }

    public Duck() {
        this.iscloneidentifier = false;
        this.notyetduck = null;
        this.notyetduckclass = null;
    }


    private void setupmethods() {
        this.issimpleapply = false;
        for (Method method : notyetduckclass.getMethods()) {
            switch (method.getName()) {
                case "apply":
                    this.applymethoddelegate = method;
                    break;
                case "simpleapply":
                    this.issimpleapply = true;
                    this.applymethoddelegate = method;
                    break;
                case "value":
                    this.valuemethoddelegate = method;
                    break;
            }
        }
        if (this.applymethoddelegate == null && !this.issimpleapply) {
            throw new IllegalArgumentException(new StringBuilder("Both apply and simpleapply missing for: ").append(notyetduckclass.getName()).toString());
        }
    }


    public Pair<ArrayList<Duck>, Integer> apply(ArrayList<Duck> args, Integer pos) {
        try {
            if (!this.issimpleapply) {
                return (Pair<ArrayList<Duck>, Integer>) this.applymethoddelegate.invoke(this.notyetduck, args, pos);
            }
            var subargs = new ArrayList<Duck>(args.subList(pos, args.size()));
            var processed = (ArrayList<Duck>) this.applymethoddelegate.invoke(this.notyetduck, subargs);
            processed.addAll(0, args.subList(0, pos));
            return new Pair<ArrayList<Duck>, Integer>(processed, pos);

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(new StringBuilder().append("Issues with apply/simpleapply method in: ").append(notyetduckclass.getName()).toString());
    }

    public Object value() {
        if (this.valuemethoddelegate == null) return this.notyetduckclass.getName() + "@" + UUID.randomUUID();
        try {
            return this.valuemethoddelegate.invoke(this.notyetduck);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(new StringBuilder().append("Issues with value method in: ").append(notyetduckclass.getName()).toString());
    }

    public Object getInternalDuck() {
        return notyetduck;
    }

    public Class<?> getInternalDuckType() {
        return notyetduckclass;
    }

    public Class<?> getNotyetduckclass() {
        return notyetduckclass;
    }

    public Object getNotyetduck() {
        return notyetduck;
    }
}
