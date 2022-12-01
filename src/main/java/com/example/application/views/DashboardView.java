package com.example.application.views;

import com.example.application.data.service.CRMService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Dashboard | Mila Library")
public class DashboardView extends VerticalLayout {

    private final CRMService service;

    public DashboardView(CRMService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getCustomerStats(),
                getCustomerChart(),
                getBookStats(),
                getBookChart(),
                getLogStats(),
                getLogChart());
    }

    private Component getCustomerStats() {
        Span stats = new Span(service.countCustomers() + " customers");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Chart getCustomerChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();

        service.findAllCustomers("")
                .forEach(customer ->
                        dataSeries.add(
                                new DataSeriesItem(
                                        customer.getFirstName().concat(" ").concat(customer.getLastName()),
                                        service.countCustomers()
                                )
                        )
                );

        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Component getBookStats() {
        Span stats = new Span(service.countBooks() + " books");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Chart getBookChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();

        service.findAllBooks("")
                .forEach(book ->
                        dataSeries.add(
                                new DataSeriesItem(
                                        book.getTitle(),
                                        service.countBooks()
                                )
                        )
                );

        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Component getLogStats() {
        Span stats = new Span(service.countLogs() + " logs");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Chart getLogChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();

        service.findAllLogs()
                .forEach(log ->
                        dataSeries.add(
                                new DataSeriesItem(
                                        log.getState(),
                                        service.countLogs()
                                )
                        )
                );

        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}