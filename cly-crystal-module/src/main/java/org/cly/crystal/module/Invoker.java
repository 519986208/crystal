package org.cly.crystal.module;

import java.lang.reflect.Method;

public class Invoker {

    private Object target;
    private Method method;

    public Object invoke(Object... args) {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

}
