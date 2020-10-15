package com.company;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FakeCloner {
    public static TreeSet<String> knownImmutables = new TreeSet<String>();
    public static boolean collectionCloneFailed = false;
    public static FakeCloneException exampleFailure;

    @SuppressWarnings("unchecked")
    public static ArrayList<Duck> maybeFakeClone(ArrayList<Duck> ducks) throws FakeCloneException {
        boolean found = false;
        for (int i = 0; i < ducks.size(); i++) {
            if (ducks.get(i).isCloneIdentifier) {
                found = true;
                ducks.remove(i);
            }
        }
        if (!found) return ducks;
        collectionCloneFailed = false;
        Constructor emptyconst = getEmptyConstructor(ducks);
        return (ArrayList<Duck>) collectionSafeReplace(ducks, o1 -> {
            try {
                return maybeFakeClone(o1);
            } catch (FakeCloneException e) {
                exampleFailure = e;
                collectionCloneFailed = true;
            }
            return null;
        }, emptyconst);
    }

    public static Object maybeFakeClone(Object o) throws FakeCloneException {
        if (o == null) return null;
        if (isBoxed(o) || isString(o) || isSpecialCase(o)) return o;
        Constructor emptyconst = null;
        if (o instanceof Collection) {
            collectionCloneFailed = false;
            emptyconst = getEmptyConstructor(o);
            return collectionSafeReplace((Collection) o, o1 -> {
                try {
                    return maybeFakeClone(o1);
                } catch (FakeCloneException e) {
                    exampleFailure = e;
                    collectionCloneFailed = true;
                }
                return null;
            }, emptyconst);
        }
        if (o instanceof Duck) {
            return new Duck((maybeFakeClone(((Duck) o).notADuck)));
        }
        HashMap<Field, Object> props = stealprops(o);
        if (isImmutable(props, o.getClass().getName())) return o;
        if (emptyconst == null) emptyconst = getEmptyConstructor(o);
        return forceProps(props, emptyconst);
    }

    public static HashMap<Field, Object> stealprops(Object obj) throws FakeCloneException {
        var values = new HashMap<Field, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                values.put(f, f.get(obj));
            } catch (IllegalAccessException e) {
                throw new FakeCloneException("Could not steal property: " + f.getName() + " from " + obj.getClass().getName(), e);
            }
        }
        return values;
    }

    public static Object forceProps(HashMap<Field, Object> fieldvals, Constructor maker) throws FakeCloneException {
        Object clone = null;
        try {
            clone = maker.newInstance();
        } catch (Exception e) {
            throw new FakeCloneException("Could not create empty instance of: " + maker.getName(), e);
        }
        Iterator<Map.Entry<Field, Object>> itr = fieldvals.entrySet().iterator();
        Map.Entry<Field, Object> entry;
        while (itr.hasNext()) {
            entry = itr.next();
            try {
                entry.getKey().set(clone, maybeFakeClone(entry.getValue()));
            } catch (IllegalAccessException e) {
                throw new FakeCloneException("While forcing props, could not set property in: " + maker.getName(), e);
            }
        }
        return clone;
    }

    public static Constructor getEmptyConstructor(Object o) throws FakeCloneException {
        Class[] emptyArgs = new Class[0];
        Constructor maker;
        try {
            maker = o.getClass().getConstructor(emptyArgs);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new FakeCloneException(o.getClass().getName() + " has no 0-parameter constructor. This is a requirement for all ducks and classes used within that are not Cloneable ducks.\n" +
                    "If you don't want this to happen, make the class Cloneable or add a 0-parameter contructor.", e);
        }
        return maker;
    }

    public static boolean isBoxed(Object obj) {
        return obj instanceof Integer || obj instanceof Boolean
                || obj instanceof Byte || obj instanceof Character
                || obj instanceof Float || obj instanceof Long
                || obj instanceof Short || obj instanceof Double;
    }

    public static boolean isSpecialCase(Object obj) {
        return obj instanceof Pattern;
    }

    public static boolean isString(Object obj) {
        return obj instanceof String;
    }

    public static boolean isImmutable(HashMap<Field, Object> fieldValues, String classPath) {
        if (knownImmutables.contains(classPath)) return true;
        var isImmutable = true;
        Set<Field> fields = fieldValues.keySet();
        if (fields.isEmpty()) {
            knownImmutables.add(classPath);
            return true;
        }
        for (Field field : fields) {
            if (!Modifier.isFinal(field.getModifiers())) {
                isImmutable = false;
            }
        }
        if (isImmutable) {
            knownImmutables.add(classPath);
        }
        return isImmutable;
    }

    public static void disableWarning() { //thanks stackoverflow
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);

            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
            // ignore
        }
    }

    public static Collection collectionSafeReplace(Collection collection, Function<Object, Object> function, Constructor<Collection> c) throws FakeCloneException {
        ArrayList modList = (ArrayList) collection.parallelStream().map(function::apply).collect(Collectors.toList());
        if (collectionCloneFailed)
            throw new FakeCloneException("A collection was detected by fakeClone, but at least one of its items failed to fakeClone.", exampleFailure);
        Collection emptycoll = null;
        try {
            emptycoll = c.newInstance();
        } catch (Exception e) {
            throw new FakeCloneException("Some stuff happened while trying to construct: " + collection.getClass().getName(), e);
        }
        emptycoll.addAll(modList);
        return emptycoll;
    }
}
