package pl.playbit.di.car;

import pl.playbit.di.annotations.Init;
import pl.playbit.di.annotations.Inject;
import pl.playbit.di.car.better.BetterCarburetor;

public class Engine {

    @Inject(BetterCarburetor.class)
    private Carburetor carburetor;

    @Inject
    private Exhaust exhaust;

    private String name;

    @Init
    public void postConstruct1() {
        name = "Kaalakiota";
    }

    public Carburetor getCarburetor() {
        return carburetor;
    }

    public void setCarburetor(Carburetor carburetor) {
        this.carburetor = carburetor;
    }

    public Exhaust getExhaust() {
        return exhaust;
    }

    public void setExhaust(Exhaust exhaust) {
        this.exhaust = exhaust;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
