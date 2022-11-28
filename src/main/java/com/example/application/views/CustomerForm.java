package com.example.application.views;

import com.example.application.data.entity.Customer;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class CustomerForm extends FormLayout {
    private Customer customer;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField address = new TextField("Address");
    TextField phone = new TextField("Phone");
    EmailField email = new EmailField("Email");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Customer> binder = new BeanValidationBinder<>(Customer.class);

    public CustomerForm() {
        addClassName("customer-form");
        binder.bindInstanceFields(this);
        add(firstName, lastName, address, phone, email, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new DeleteEvent(this, customer)));
        close.addClickListener(e -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        binder.readBean(customer);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(customer);
            fireEvent(new SaveEvent(this, customer));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class CustomerFormEvent extends ComponentEvent<CustomerForm> {
        private final Customer customer;

        protected CustomerFormEvent(CustomerForm source, Customer customer) {
            super(source, false);
            this.customer = customer;
        }

        public Customer getContact() {
            return customer;
        }
    }

    public static class SaveEvent extends CustomerFormEvent {
        SaveEvent(CustomerForm source, Customer customer) {
            super(source, customer);
        }
    }

    public static class DeleteEvent extends CustomerFormEvent {
        DeleteEvent(CustomerForm source, Customer customer) {
            super(source, customer);
        }

    }

    public static class CloseEvent extends CustomerFormEvent {
        CloseEvent(CustomerForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}