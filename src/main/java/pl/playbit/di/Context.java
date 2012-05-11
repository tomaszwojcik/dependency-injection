package pl.playbit.di;

import pl.playbit.di.annotations.Init;
import pl.playbit.di.annotations.Inject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

public class Context {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) {
        try {
            T instance = clazz.newInstance();
            injectFields(instance);
            initMethods(instance);
            return instance;
        } catch (Exception e) { //TODO handle exceptions at each method?
            e.printStackTrace();
        }
        return null;
    }

    private static <T> void injectFields(T instance) throws InvocationTargetException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        LinkedList<Class<?>> hierarchy = ClassInspector.getClassHierarchy(instance.getClass());
        while (hierarchy.size() > 0) {
            Class<?> clazz = hierarchy.pollLast();
            injectClassFields(instance, clazz);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> void injectClassFields(T instance, Class<?> clazz) throws InvocationTargetException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Collection<Field> injectedList = ClassInspector.getAnnotatedFields(clazz, Inject.class);
        for (Field fieldToBeInjected : injectedList) {
            Class classToBeInjected;// = null;
            Inject annotation = fieldToBeInjected.getAnnotation(Inject.class);
            if (annotation.value() != Inject.DEFAULT.class) {
                //Get type of class to be injected (field (with @Inject) type)
                classToBeInjected = annotation.value();
            } else {
                //Get type of class to be injected (type passed to @Inject)
                classToBeInjected = fieldToBeInjected.getType();
            }
            //Create underlying object
            Object injectedObject = create(classToBeInjected);
            //Check is field is accessible and raise privileges if not.
            boolean isAccessible = fieldToBeInjected.isAccessible();
            if (!isAccessible) { //Raise privileges
                fieldToBeInjected.setAccessible(true);
            }
            //Set field for the given instance with given value.
            fieldToBeInjected.set(instance, injectedObject);
            if (!isAccessible) { //Restore privileges
                fieldToBeInjected.setAccessible(false);
            }
        }
    }

    private static <T> void initMethods(T instance) throws InvocationTargetException, IllegalAccessException {
        Collection<Method> methods = ClassInspector.getAnnotatedMethods(instance.getClass(), Init.class);
        for (Method method : methods) {
            boolean isAccessible = method.isAccessible();
            if (!isAccessible) {
                method.setAccessible(true);
            }
            method.invoke(instance);
            if (!isAccessible) {
                method.setAccessible(false);
            }
        }
    }

}
