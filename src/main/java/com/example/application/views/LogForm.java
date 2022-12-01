package com.example.application.views;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Customer;
import com.example.application.data.entity.Log;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.Arrays;
import java.util.List;

public class LogForm extends FormLayout {

    private Log log;

    ComboBox<String> state = new ComboBox<>("State");
    DateTimePicker checkOutDate = new DateTimePicker("Check Out Date");
    DateTimePicker checkInDate = new DateTimePicker("Check In Date");
    ComboBox<Book> book = new ComboBox<>("Book");
    ComboBox<Customer> customer = new ComboBox<>("Customer");
    Binder<Log> binder = new BeanValidationBinder<>(Log.class);

    public LogForm(List<Book> books, List<Customer> customers) {
        addClassName("log-form");

        binder.bindInstanceFields(this);

        String[] stateOptions = Arrays.stream(Log.State.values())
                .map(Log.State::getState)
                .toArray(String[]::new);

        state.setItems(stateOptions);

        book.setItems(books);
        book.setItemLabelGenerator(Book::getTitle);

        customer.setItems(customers);
        customer.setItemLabelGenerator(Customer::toString);

        add(state,
                checkOutDate,
                checkInDate,
                book,
                customer,
                createButtonsLayout());
    }

    public HorizontalLayout createButtonsLayout() {
        Button save = new Button("Save");
        Button delete = new Button("Delete");
        Button close = new Button("Cancel");

        save.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        delete.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        close.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new DeleteEvent(this, log)));
        close.addClickListener(e -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setLog(Log log) {
        this.log = log;
        binder.readBean(log);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(log);
            fireEvent(new SaveEvent(this, log));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    // Events
    public static abstract class LogFormEvent extends ComponentEvent<LogForm> {
        private final Log log;

        protected LogFormEvent(LogForm source, Log log) {
            super(source, false);
            this.log = log;
        }

        public Log getLog() {
            return log;
        }
    }

    public static class SaveEvent extends LogFormEvent {
        public SaveEvent(LogForm source, Log log) {
            super(source, log);
        }
    }

    public static class DeleteEvent extends LogFormEvent {
        public DeleteEvent(LogForm source, Log log) {
            super(source, log);
        }
    }

    public static class CloseEvent extends LogFormEvent {
        CloseEvent(LogForm source) {
            super(source, null);
        }
    }
}
