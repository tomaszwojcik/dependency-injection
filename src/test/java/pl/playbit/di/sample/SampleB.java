package pl.playbit.di.sample;

import pl.playbit.di.annotations.Init;
import pl.playbit.di.annotations.Inject;
import pl.playbit.di.car.Carburetor;
import pl.playbit.di.car.Engine;
import pl.playbit.di.car.Exhaust;

public class SampleB {
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
