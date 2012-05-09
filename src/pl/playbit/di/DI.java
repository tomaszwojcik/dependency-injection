package pl.playbit.di;

import pl.playbit.di.data.Car;

public class DI {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Car car = Context.create(Car.class);
        car = null;
    }
}
