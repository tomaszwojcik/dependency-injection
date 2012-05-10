package pl.playbit.di.car.better;

import pl.playbit.di.annotations.Init;
import pl.playbit.di.car.Car;

public class BetterCar extends Car {

    @Init
    private void afterInit() {
        setName("Raptor");
        setMaxSpeed(220);
        getEngine().setName("Lai Dai");
        getEngine().getExhaust().setName("Caldari");
    }
}
