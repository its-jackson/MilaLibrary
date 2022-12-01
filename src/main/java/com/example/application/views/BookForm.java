package com.example.application.views;

import com.example.application.data.entity.Book;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class BookForm extends FormLayout {

    TextField title = new TextField("Title");
    TextField authorFirstName = new TextField("Author first name");
    TextField authorLastName = new TextField("Author last name");
    ComboBox<Integer> rating = new ComboBox<>("Rating");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Book> binder = new BeanValidationBinder<>(Book.class);

    private Book book;

    public BookForm() {
        addClassName("book-form");
        configureRating();
        binder.bindInstanceFields(this);
        add(this.title, this.authorFirstName, this.authorLastName, this.rating, createButtonsLayout());
    }

    private void configureRating() {
        this.rating.setItems(1,2,3,4,5);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new BookForm.DeleteEvent(this, book)));
        close.addClickListener(e -> fireEvent(new BookForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setBook(Book book) {
        this.book = book;
        binder.readBean(book);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(book);
            fireEvent(new BookForm.SaveEvent(this, book));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(
            Class<T> eventType,
            ComponentEventListener<T> listener
    ) {
        return getEventBus().addListener(eventType, listener);
    }

    // Events
    public static abstract class BookFormEvent extends ComponentEvent<BookForm> {
        private final Book book;

        protected BookFormEvent(BookForm source, Book book) {
            super(source, false);
            this.book = book;
        }

        public Book getBook() {
            return book;
        }
    }

    public static class SaveEvent extends BookForm.BookFormEvent {
        SaveEvent(BookForm source, Book book) {
            super(source, book);
        }
    }

    public static class DeleteEvent extends BookForm.BookFormEvent {
        DeleteEvent(BookForm source, Book book) {
            super(source, book);
        }
    }

    public static class CloseEvent extends BookForm.BookFormEvent {
        CloseEvent(BookForm source) {
            super(source, null);
        }
    }
}
