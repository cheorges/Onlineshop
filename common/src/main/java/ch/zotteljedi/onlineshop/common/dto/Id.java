package ch.zotteljedi.onlineshop.common.dto;

import java.io.Serializable;

public abstract class Id implements Serializable {

    private Integer value;

    public static <T extends Id> T of(Integer idValue, Class<T> concreteIdClazz) {
        T instance;
        try {
            instance = concreteIdClazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        instance.setValue(idValue);
        return instance;
    }

    void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
