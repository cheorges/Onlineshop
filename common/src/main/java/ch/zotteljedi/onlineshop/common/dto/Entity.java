package ch.zotteljedi.onlineshop.common.dto;

import java.io.Serializable;

public interface Entity<T extends Id> extends Serializable {
    T getId();
}
