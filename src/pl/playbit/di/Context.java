package pl.playbit.di;

import pl.playbit.di.annotations.Inject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class Context {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        T instance = clazz.newInstance();
        Collection<Field> injectedList = getInjectFields(clazz);
        for (Field fieldToBeInjected : injectedList) {
            //Get type of class to be injected (field (with @Inject) type)
            Class classToBeInjected = fieldToBeInjected.getType();
            //Create underlying object
            Object injectedObject = create(classToBeInjected);
            boolean isAccessible = fieldToBeInjected.isAccessible();
            if (!isAccessible) {
                fieldToBeInjected.setAccessible(true);
            }
            fieldToBeInjected.set(instance, injectedObject);
            if (!isAccessible) {
                fieldToBeInjected.setAccessible(false);
            }
        }
        return instance;
    }

    private static <T> Collection<Field> getInjectFields(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Collection<Field> result = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                result.add(field);
            }
        }
        return result;
    }

}
