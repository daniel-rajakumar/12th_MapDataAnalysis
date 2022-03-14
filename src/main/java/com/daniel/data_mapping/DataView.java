package com.daniel.data_mapping;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

@PageTitle("Home")
@Route("data")
@JsModule("./script.js")
@JsModule("./headerScript.js")
@JsModule("https://code.jquery.com/jquery-3.6.0.min.js")
@JsModule("https://d3js.org/d3.v7.min.js")
@CssImport(
        themeFor = "vaadin-grid",
        value = "vaadin-grid.css"
)
@CssImport(value = "header-style.css")
@CssImport(value = "search-style.css")
public class DataView extends VerticalLayout {
    Grid<String> grid = setUpGrid();
    TextField textField = setUpTextField();

    public DataView() {
        setWidthFull();
        setHeightFull();
        add(textField, grid);
    }

    TextField setUpTextField(){
        Set<String> people = Storage.MAP_SALARY.keySet();
        GridListDataView<String> dataView = grid.setItems(people);

        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Search");
        searchField.setClassName("custom-search-color");

        Icon icon = new Icon(VaadinIcon.SEARCH);
        icon.setClassName("custom-vaadin-icon");
        searchField.setPrefixComponent(icon);
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());


        dataView.addFilter(person -> {
            String searchTerm = searchField.getValue().trim();

            if (searchTerm.isEmpty())
                return true;


            return matchesTerm(person,
                    searchTerm);
        });

        getElement().getStyle().set("padding-top", "100px");

        return searchField;
    }


    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }

    Grid<String> setUpGrid(){
        Grid<String> grid = new Grid<>();
        grid.setItems(Storage.MAP_SALARY.keySet());
        grid.addColumn(key -> key)
                .setHeader("Major")
                .setFlexGrow(1)
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(key -> toCurrencyFormat(Storage.MAP_SALARY.get(key).getStarting_salary()))
                .setHeader("Starting Salary")
                .setFlexGrow(1)
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(key -> Storage.MAP_UNEMPLOYMENT.get(key).getUnemployment_rate() + " %")
                .setHeader("Unemployment Rate")
                .setFlexGrow(1)
                .setSortable(true)
                .setAutoWidth(true);

        return grid;
    }

    String toCurrencyFormat(float amount){
        return "$ " + NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                .format(amount).substring(1);
    }

}
