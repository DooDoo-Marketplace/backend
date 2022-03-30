package space.rebot.micro.common.factory;

import java.util.function.Consumer;

public abstract class AbstractFactory <T> {
    protected T entity;

    public T get(){
        return this.entity;
    }
    public void load(T entity){
        this.entity = entity;
    }
    public <V> void setIfNotNull(final Consumer<V> setter, final V value) {
        if (value != null) {
            setter.accept(value);
        }
    }

}
