package pl.playbit.di.inspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class DefaultClassInspector implements ClassInspector {

    @Override
    public <T> LinkedList<Class<?>> getClassHierarchy(Class<T> instance) {
        LinkedList<Class<?>> hierarchy = new LinkedList<>();
        Class<?> clazz = instance;
        while (clazz != null) {
            hierarchy.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return hierarchy;
    }

    @Override
    public <T> Collection<Field> getAnnotatedFields(Class<T> clazz, Class<? extends Annotation> annotation) {
        Field[] fields = clazz.getDeclaredFields();
        Collection<Field> result = new HashSet<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(annotation)) {
                result.add(field);
            }
        }
        return result;
    }

    @Override
    @SafeVarargs
    public final <T> Collection<Field> getAnnotatedFields(Class<T> clazz, Class<? extends Annotation>... annotations) {
        Field[] fields = clazz.getDeclaredFields();
        Collection<Field> result = new HashSet<>();
        for (Field field : fields) {
            for (Class<? extends Annotation> annotation : annotations) {
                if (field.isAnnotationPresent(annotation)) {
                    result.add(field);
                }
            }
        }
        return result;
    }

    @Override
    public <T> Collection<Method> getAnnotatedMethods(Class<T> clazz, Class<? extends Annotation> annotation) {
        Method[] methods = clazz.getDeclaredMethods();
        Collection<Method> result = new HashSet<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                result.add(method);
            }
        }
        return result;
    }

    @Override
    @SafeVarargs
    public final <T> Collection<Method> getAnnotatedMethods(Class<T> clazz, Class<? extends Annotation>... annotations) {
        Method[] methods = clazz.getDeclaredMethods();
        Collection<Method> result = new HashSet<>();
        for (Method method : methods) {
            for (Class<? extends Annotation> annotation : annotations) {
                if (method.isAnnotationPresent(annotation)) {
                    result.add(method);
                }
            }
        }
        return result;
    }

}
