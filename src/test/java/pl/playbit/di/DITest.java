package pl.playbit.di;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.playbit.di.annotations.Init;
import pl.playbit.di.annotations.Inject;
import pl.playbit.di.car.Car;
import pl.playbit.di.car.Carburetor;
import pl.playbit.di.car.Engine;
import pl.playbit.di.car.Exhaust;
import pl.playbit.di.car.better.BetterCar;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

import static org.testng.Assert.*;

public class DITest {

    @DataProvider
    public Object[][] shouldInjectDependencies() {
        return new Object[][]{
                //Car class,      name,     speed, engine name,  carburetor, exhaust
                {Car.class, "Crow", 200, "Kaalakiota", "Good", null},
                {BetterCar.class, "Raptor", 220, "Lai Dai", "Good", "Caldari"}
        };
    }

    @Test(dataProvider = "shouldInjectDependencies")
    public <T extends Car> void shouldInjectDependencies(Class<T> clazz, String name, int maxSpeed, String engineVendor,
                                                         String carburetorVendor, String exhaustVendor) {
        T car = null;
        try {
            car = Context.create(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(car);
        assertNotNull(car.getEngine());
        assertNotNull(car.getEngine().getCarburetor());
        assertNotNull(car.getEngine().getExhaust());

        assertEquals(name, car.getName());
        assertEquals(maxSpeed, car.getMaxSpeed());
        assertEquals(engineVendor, car.getEngine().getName());
        assertEquals(carburetorVendor, car.getEngine().getCarburetor().getName());
        assertEquals(exhaustVendor, car.getEngine().getExhaust().getName());
    }

    @DataProvider
    public Object[][] shouldGetClassHierarchy() {
        return new Object[][]{
                {BetterCar.class, new Object[]{BetterCar.class, Car.class, Object.class}},
        };
    }

    @Test(dataProvider = "shouldGetClassHierarchy")
    public <T> void shouldGetClassHierarchy(Class<T> targetClass, Object[] superiorClasses) {
        LinkedList<Class<?>> hierarchy = ClassInspector.getClassHierarchy(targetClass);
        assertEquals(hierarchy.size(), superiorClasses.length);
        int idx = 0;
        for (Class<?> clazz : hierarchy) {
            assertTrue(clazz.getClass() == superiorClasses[idx].getClass());
            idx++;
        }
    }

    private class SampleA {
        @Inject
        private Car car;

        private BetterCar betterCar;

    }

    private class SampleB {
        @Inject
        private Engine spareEngine;

        @Inject
        private Exhaust spareExhaust;

        private Carburetor spareCarburetor;

        @Init
        public void doSomething() {
        }

        public void doSomethingElse() {
        }
    }

    @DataProvider
    public Object[][] itShouldGetAnnotatedFields() {
        return new Object[][]{
                {SampleA.class, Inject.class, 1},
                {SampleB.class, Inject.class, 2}
        };
    }

    @Test(dataProvider = "itShouldGetAnnotatedFields")
    public <T> void itShouldGetAnnotatedFields(Class<T> clazz, Class<? extends Annotation> annotation, int annotatedCount) {
        Collection<Field> fields = ClassInspector.getAnnotatedFields(clazz, annotation);
        assertEquals(fields.size(), annotatedCount);
    }

    @DataProvider
    public Object[][] itShouldGetAnnotatedMethods() {
        return new Object[][]{
                {SampleA.class, Init.class, 0},
                {SampleB.class, Init.class, 1}
        };
    }

    @Test(dataProvider = "itShouldGetAnnotatedMethods")
    public <T> void itShouldGetAnnotatedMethods(Class<T> clazz, Class<? extends Annotation> annotation, int annotatedCount) {
        Collection<Method> methods = ClassInspector.getAnnotatedMethods(clazz, annotation);
        assertEquals(methods.size(), annotatedCount);
    }
}
