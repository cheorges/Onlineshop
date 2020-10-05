package ch.zotteljedi.onlineshop.common.message;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MessageContainerTest {

    @Test
    public void test_no_message() {
        // Given
        MessageContainer messageContainer = new MessageContainer();
        final List<Message> messages = new ArrayList<>();

        // When
        boolean hasMessages = messageContainer.hasMessagesThenProvide(messages::add);

        // Then
        assertFalse(hasMessages);
        assertTrue(messages.isEmpty());
    }

    @Test
    public void test_add_message() {
        // Given
        MessageContainer messageContainer = new MessageContainer();
        final List<Message> messages = new ArrayList<>();

        // When
        messageContainer.add((Message) () -> "message");

        // Then
        assertTrue(messageContainer.hasMessagesThenProvide(messages::add));
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0).getMessage(), is("message"));
    }

    @Test
    public void test_one_message_exist() {
        // Given
        MessageContainer messageContainer = new MessageContainer();
        messageContainer.add(() -> "message");

        final List<Message> messages = new ArrayList<>();

        // When
        boolean hasMessages = messageContainer.hasMessagesThenProvide(messages::add);

        // Then
        assertTrue(hasMessages);
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0).getMessage(), is("message"));
    }

    @Test
    public void test_multiple_messages_exist() {
        // Given
        MessageContainer messageContainer = new MessageContainer();
        messageContainer.add(() -> "message 1");
        messageContainer.add(() -> "message 2");
        messageContainer.add(() -> "message 3");

        final List<Message> messages = new ArrayList<>();

        // When
        boolean hasMessages = messageContainer.hasMessagesThenProvide(messages::add);

        // Then
        assertTrue(hasMessages);
        assertThat(messages.size(), is(3));
        assertThat(messages.get(0).getMessage(), is("message 1"));
        assertThat(messages.get(1).getMessage(), is("message 2"));
        assertThat(messages.get(2).getMessage(), is("message 3"));
    }

    @Test
    public void test_after_provide_no_more_messages_exist() {
        // Given
        MessageContainer messageContainer = new MessageContainer();
        messageContainer.add(() -> "message");

        final List<Message> messages = new ArrayList<>();

        // When
        boolean hasMessages = messageContainer.hasMessagesThenProvide(messages::add);

        // Then
        assertTrue(hasMessages);
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0).getMessage(), is("message"));
        assertFalse(messageContainer.hasMessagesThenProvide(messages::add));
    }

    @Test
    public void test_has_no_message() {
        // Given
        MessageContainer messageContainer = new MessageContainer();
        Runnable runnable = mock(Runnable.class);

        // When
        messageContainer.hasNoMessage(runnable);

        // Then
        verify(runnable).run();
    }

    @Test
    public void test_messagecontainer_has_message() {
        // Given
        MessageContainer messageContainer = new MessageContainer();
        messageContainer.add(() -> "message");
        Runnable runnable = mock(Runnable.class);

        // When
        messageContainer.hasNoMessage(runnable);

        // Then
        verify(runnable, never()).run();
    }

}