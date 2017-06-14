package org.cly.crystal.module.scan;

import java.lang.reflect.Method;

import org.cly.crystal.module.Invoker;
import org.cly.crystal.module.SubjectInvoker;
import org.cly.crystal.module.enumeration.Module;
import org.cly.crystal.module.enumeration.Subject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class SubjectScanner implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<? extends Object> clz = bean.getClass();
        Class<?>[] interfaces = clz.getInterfaces();
        if (interfaces == null || interfaces.length == 0) {
            return bean;
        }
        for (Class<?> inter : interfaces) {
            Subject subject = inter.getAnnotation(Subject.class);
            if (subject == null) {
                return bean;
            }
            Method[] methods = inter.getDeclaredMethods();
            if (methods == null || methods.length == 0) {
                return bean;
            }
            for (Method method : methods) {
                Module module = method.getAnnotation(Module.class);
                if (module != null) {
                    Invoker invoker = new Invoker();
                    invoker.setTarget(bean);
                    invoker.setMethod(method);
                    SubjectInvoker.addInvoker(subject.value(), module.value(), invoker);
                }
            }
        }
        return bean;
    }

}
