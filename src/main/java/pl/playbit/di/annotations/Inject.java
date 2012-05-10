package pl.playbit.di.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {

    //If there is only value() present we will be able to use this annotation like this @Inject(MyClass.class),
    //otherwise we will have to specify parameters: @Inject(value = MyClass.class, otherParameter = OtherValue)
    Class value() default DEFAULT.class;

    //Dummy class (null isn't a valid default value for value())
    static final class DEFAULT {}
}
