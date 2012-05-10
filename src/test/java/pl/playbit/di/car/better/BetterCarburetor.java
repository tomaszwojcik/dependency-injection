package pl.playbit.di.car.better;

import pl.playbit.di.annotations.Init;
import pl.playbit.di.car.Carburetor;

public class BetterCarburetor extends Carburetor {

    @Init
    private void updateName() {
        setName("Good");
    }
}
