package com.example.application.views;

import com.example.application.data.entity.Log;
import com.example.application.data.service.CRMService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.stream.Collectors;

@PermitAll
@Route(value = "logs", layout = MainLayout.class)
@PageTitle("Logs | Mila Library")
public class LogListView extends VerticalLayout {

    Grid<Log> grid = new Grid<>(Log.class);
    LogForm logForm;
    CRMService crmService;

    public LogListView(CRMService crmService) {
        this.crmService = crmService;
        addClassName("log-list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("log-grid");
        grid.setSizeFull();
        grid.setColumns(
                "state",
                "checkOutDate",
                "checkInDate"
        );

        grid.addColumn(log -> log.getBook().getTitle()).setHeader("Book");
        grid.addColumn(log -> log.getCustomer().toString()).setHeader("Customer");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void configureForm() {
        logForm = new LogForm(crmService.findAllBooks(""), crmService.findAllCustomers(""));
        logForm.setWidth("25em");
        logForm.addListener(LogForm.SaveEvent.class, this::saveLog);
        logForm.addListener(LogForm.DeleteEvent.class, this::deleteLog);
        logForm.addListener(LogForm.CloseEvent.class, e -> closeEditor());

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editLog(e.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, logForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, logForm);
        content.addClassName("log-content");
        content.setSizeFull();
        return content;
    }

    private HorizontalLayout getToolbar() {
        Button addLogButton = new Button("Add Log");
        addLogButton.addClickListener(click -> addLog());

        Button filterByCheckOut = new Button("Filter Checked-Out");
        filterByCheckOut.addClickListener(click -> filterCheckedOut());

        Button filterByCheckIn = new Button("Filter Checked-In");
        filterByCheckIn.addClickListener(click -> filterCheckedIn());

        HorizontalLayout toolbar = new HorizontalLayout(
                addLogButton,
                filterByCheckOut,
                filterByCheckIn
        );

        toolbar.addClassName("log-toolbar");
        return toolbar;
    }

    private void filterCheckedOut() {
       List<Log> checkedOutLogs = crmService.findAllLogs()
                .stream()
                .filter(log -> log.getCheckInDate() == null && log.getCheckOutDate() != null)
                .collect(Collectors.toList());

       grid.setItems(checkedOutLogs);
    }

    private void filterCheckedIn() {
        List<Log> checkedInLogs = crmService.findAllLogs()
                .stream()
                .filter(log -> log.getCheckInDate() != null && log.getCheckOutDate() != null)
                .collect(Collectors.toList());

        grid.setItems(checkedInLogs);
    }

    public void editLog(Log log) {
        if (log == null) {
            closeEditor();
        }
        else {
            logForm.setLog(log);
            logForm.setVisible(true);
            addClassName("Log-Editing");
        }
    }

    private void closeEditor() {
        logForm.setLog(null);
        logForm.setVisible(false);
        removeClassName("Log-Editing");
    }

    private void addLog() {
        grid.asSingleSelect().clear();
        editLog(new Log());
    }

    private void saveLog(LogForm.SaveEvent event) {
        crmService.saveLog(event.getLog());
        updateList();
        closeEditor();
    }

    private void deleteLog(LogForm.DeleteEvent event) {
        crmService.deleteLog(event.getLog());
        updateList();
        closeEditor();
    }

    /**
     * Sets the grid items by calling the service with the value from the filter text field.
     */
    private void updateList() {
        grid.setItems(crmService.findAllLogs());
    }
}
