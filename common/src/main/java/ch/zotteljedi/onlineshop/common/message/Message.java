package ch.zotteljedi.onlineshop.common.message;

import ch.zotteljedi.onlineshop.common.dto.ValueObject;

public interface Message extends ValueObject {
    String getMessage();
}
