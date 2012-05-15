package pl.playbit.di.multiple.annotations;

import pl.playbit.di.annotations.Inject;

public class MultipleAnnotations {

    @Inject
    @Inject2
    private String str;

    @Inject2
    private String str2;

}
