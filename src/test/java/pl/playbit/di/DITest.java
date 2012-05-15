package pl.playbit.di;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.playbit.di.annotations.Init;
import pl.playbit.di.annotations.Inject;
import pl.playbit.di.car.Car;
import pl.playbit.di.car.better.BetterCar;
import pl.playbit.di.context.Context;
import pl.playbit.di.context.DefaultContext;
import pl.playbit.di.inspector.ClassInspector;
import pl.playbit.di.inspector.DefaultClassInspector;
import pl.playbit.di.sample.SampleA;
import pl.playbit.di.sample.SampleB;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

import static org.testng.Assert.*;

//TODO check given values
public class DITest {

    private ClassInspector classInspector = new DefaultClassInspector();
    private Context context = new DefaultContext(classInspector); //Parameter can be omitted

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
            car = context.create(clazz);
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
        LinkedList<Class<?>> hierarchy = classInspector.getClassHierarchy(targetClass);
        assertEquals(hierarchy.size(), superiorClasses.length);
        int idx = 0;
        for (Class<?> clazz : hierarchy) {
            assertTrue(clazz.getClass() == superiorClasses[idx].getClass());
            idx++;
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
        Collection<Field> fields = classInspector.getAnnotatedFields(clazz, annotation);
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
        Collection<Method> methods = classInspector.getAnnotatedMethods(clazz, annotation);
        assertEquals(methods.size(), annotatedCount);
    }
}
