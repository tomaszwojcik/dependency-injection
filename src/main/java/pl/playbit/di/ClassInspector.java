package pl.playbit.di;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class ClassInspector {
    public static <T> LinkedList<Class<?>> getClassHierarchy(Class<T> instance) {
        LinkedList<Class<?>> hierarchy = new LinkedList<>();
        Class<?> clazz = instance;
        while (clazz != null) {
            hierarchy.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return hierarchy;
    }

    public static <T> Collection<Field> getAnnotatedFields(Class<T> clazz, Class<? extends Annotation> annotation) {
        Field[] fields = clazz.getDeclaredFields();
        Collection<Field> result = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(annotation)) {
                result.add(field);
            }
        }
        return result;
    }

    public static <T> Collection<Method> getAnnotatedMethods(Class<T> clazz, Class<? extends Annotation> annotation) {
        Method[] methods = clazz.getDeclaredMethods();
        Collection<Method> result = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                result.add(method);
            }
        }
        return result;
    }

}
