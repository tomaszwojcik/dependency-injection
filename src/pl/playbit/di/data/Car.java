package pl.playbit.di.data;

import pl.playbit.di.annotations.Inject;

public class Car {

    @Inject
    private Engine engine;

    private int maxSpeed;

    private int weight;

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
