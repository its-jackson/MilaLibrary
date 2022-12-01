package com.example.application.views;

import com.example.application.data.entity.Customer;
import com.example.application.data.service.CRMService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "customers", layout = MainLayout.class)
@PageTitle("Customers | Mila Library")
public class CustomerListView extends VerticalLayout {

    Grid<Customer> grid = new Grid<>(Customer.class);
    TextField filterText = new TextField();
    CustomerForm customerForm;
    CRMService service;

    public CustomerListView(CRMService service) {
        this.service = service;
        addClassName("customer-list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, customerForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, customerForm);
        content.addClassName("customer-content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        customerForm = new CustomerForm();
        customerForm.setWidth("25em");
        customerForm.addListener(CustomerForm.SaveEvent.class, this::saveCustomer);
        customerForm.addListener(CustomerForm.DeleteEvent.class, this::deleteCustomer);
        customerForm.addListener(CustomerForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveCustomer(CustomerForm.SaveEvent event) {
        service.saveCustomer(event.getCustomer());
        updateList();
        closeEditor();
    }

    private void deleteCustomer(CustomerForm.DeleteEvent event) {
        service.deleteCustomer(event.getCustomer());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("customer-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "address", "phone", "email");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editCustomer(e.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by customer name");
        filterText.setWidth("15em");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList()); // update the contacts any time the filter changes

        Button addContactButton = new Button("Add Customer");
        addContactButton.addClickListener(click -> addCustomer());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("customer-toolbar");
        return toolbar;
    }

    public void editCustomer(Customer customer) {
        if (customer == null) {
            closeEditor();
        } else {
            customerForm.setCustomer(customer);
            customerForm.setVisible(true);
            addClassName("Customer-Editing");
        }
    }

    private void closeEditor() {
        customerForm.setCustomer(null);
        customerForm.setVisible(false);
        removeClassName("Customer-Editing");
    }

    private void addCustomer() {
        grid.asSingleSelect().clear();
        editCustomer(new Customer());
    }

    /**
     * Sets the grid items by calling the service with the value from the filter text field.
     */
    private void updateList() {
        grid.setItems(service.findAllCustomers(filterText.getValue()));
    }
}