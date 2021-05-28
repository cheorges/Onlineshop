package ch.cheorges.onlineshop.core.service;

import ch.cheorges.onlineshop.common.message.Message;
import ch.cheorges.onlineshop.common.message.MessageContainer;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ApplicationService {
    private final MessageContainer messageContainer = new MessageContainer();

    protected void addMessage(Message message) {
        messageContainer.add(message);
    }

    public MessageContainer getMessageContainer() {
        return messageContainer;
    }

    protected <T> boolean validate(T entity) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        List<String> collect = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        collect.forEach(message -> addMessage(() -> message));
        return !getMessageContainer().hasMessages();
    }
}
