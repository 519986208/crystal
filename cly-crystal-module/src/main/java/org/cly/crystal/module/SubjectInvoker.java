package org.cly.crystal.module;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SubjectInvoker {

    public static Map<Integer, Map<Integer, Invoker>> subjects = new ConcurrentHashMap<>(32);

    public static void addInvoker(int subject, int module, Invoker invoker) {
        Map<Integer, Invoker> map = subjects.get(subject);
        if (map == null) {
            map = new HashMap<>();
            subjects.putIfAbsent(subject, map);
        }
        map.put(module, invoker);
    }

    public static Invoker getInvoker(int subject, int module) {
        Map<Integer, Invoker> map = subjects.get(subject);
        if (map.isEmpty()) {
            throw new IllegalArgumentException("subject is not found !!! ");
        }
        Invoker invoker = map.get(module);
        if (invoker == null) {
            throw new IllegalArgumentException("module is not found !!! ");
        }
        return invoker;
    }

}
