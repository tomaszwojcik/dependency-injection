package pl.playbit.di.car;

import pl.playbit.di.annotations.Inject;

public class Car {

    @Inject
    private Engine engine;

    private int maxSpeed = 200;

    private String name = "Crow";

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
