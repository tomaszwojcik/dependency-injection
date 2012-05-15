package pl.playbit.di.inspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

public interface ClassInspector {

    <T> LinkedList<Class<?>> getClassHierarchy(Class<T> instance);

    <T> Collection<Field> getAnnotatedFields(Class<T> clazz, Class<? extends Annotation> annotation);

    <T> Collection<Field> getAnnotatedFields(Class<T> clazz, Class<? extends Annotation>... annotations);

    <T> Collection<Method> getAnnotatedMethods(Class<T> clazz, Class<? extends Annotation> annotation);

    <T> Collection<Method> getAnnotatedMethods(Class<T> clazz, Class<? extends Annotation>... annotations);
}
