package pl.playbit.di.sample;

import pl.playbit.di.annotations.Inject;
import pl.playbit.di.car.Car;
import pl.playbit.di.car.better.BetterCar;

public class SampleA {
    @Inject
    private Car car;

    private BetterCar betterCar;
}
