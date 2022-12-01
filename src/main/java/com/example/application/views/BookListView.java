package com.example.application.views;

import com.example.application.data.entity.Book;
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
@Route(value = "books", layout = MainLayout.class)
@PageTitle("Books | Mila Library")
public class BookListView extends VerticalLayout {

    Grid<Book> grid = new Grid<>(Book.class);
    TextField filterText = new TextField();

    BookForm bookForm;
    CRMService crmService;

    public BookListView(CRMService crmService) {
        this.crmService = crmService;
        addClassName("book-list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), getContent());
        updateList();
    }

    private void configureGrid() {
        grid.addClassNames("book-grid");
        grid.setSizeFull();
        grid.setColumns("title", "authorFirstName", "authorLastName", "rating");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editBook(e.getValue()));
    }

    private void configureForm() {
        bookForm = new BookForm();
        bookForm.setWidth("25em");
        bookForm.addListener(BookForm.SaveEvent.class, this::saveBook);
        bookForm.addListener(BookForm.DeleteEvent.class, this::deleteBook);
        bookForm.addListener(BookForm.CloseEvent.class, e -> closeEditor());
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by book title or author first name");
        filterText.setWidth("20em");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add Book");
        addContactButton.addClickListener(click -> addBook());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("book-toolbar");
        return toolbar;
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, bookForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, bookForm);
        content.addClassName("book-content");
        content.setSizeFull();
        return content;
    }

    /**
     * Sets the grid items by calling the service with the value from the filter text field.
     */
    private void updateList() {
        grid.setItems(crmService.findAllBooks(filterText.getValue()));
    }

    private void closeEditor() {
        bookForm.setBook(null);
        bookForm.setVisible(false);
        removeClassName("Book-Editing");
    }

    private void addBook() {
        grid.asSingleSelect().clear();
        editBook(new Book());
    }

    public void editBook(Book book) {
        if (book == null) {
            closeEditor();
        } else {
            bookForm.setBook(book);
            bookForm.setVisible(true);
            addClassName("Book-Editing");
        }
    }

    private void saveBook(BookForm.SaveEvent event) {
        crmService.saveBook(event.getBook());
        updateList();
        closeEditor();
    }

    private void deleteBook(BookForm.DeleteEvent event) {
        crmService.deleteBook(event.getBook());
        updateList();
        closeEditor();
    }
}
