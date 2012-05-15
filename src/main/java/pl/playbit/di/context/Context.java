package pl.playbit.di.context;

public interface Context {
    public <T> T create(Class<T> clazz);
}
