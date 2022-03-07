package com.daniel.data_mapping;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Chart")
@Route("chart")
@JsModule("./script.js")
@JsModule("./headerScript.js")
@JsModule("https://code.jquery.com/jquery-3.6.0.min.js")
@JsModule("https://d3js.org/d3.v7.min.js")
@CssImport(
        themeFor = "vaadin-grid",
        value = "vaadin-grid.css"
)
@CssImport(value = "header-style.css")
public class ChartView extends VerticalLayout {

    public ChartView() {
        setWidthFull();
        setHeightFull();
    }

    Grid<String> setUpGrid(){
        Grid<String> grid = new Grid<>();
        grid.setItems(Storage.MAP_SALARY.keySet());
        grid.addColumn(key -> key)
                .setHeader(customHeaderStyle("Starting Salary"))
                .setFlexGrow(1)
                .setAutoWidth(true);

        grid.addColumn(key -> "$ " + Storage.MAP_SALARY.get(key).getStarting_salary())
                .setHeader(customHeaderStyle("Starting Salary"))
                .setFlexGrow(1)
                .setAutoWidth(true);

        grid.addColumn(key -> Storage.MAP_UNEMPLOYMENT.get(key).getUnemployment_rate() + " %")
                .setHeader(customHeaderStyle("Unemployment Rate"))
                .setFlexGrow(1)
                .setAutoWidth(true);

        grid.getElement().getStyle().set("margin-top", "100px");
        return grid;
    }

    Html customHeaderStyle(String text){
        String fontColor = "#D2E8C3";
        return new Html("<h2 style=\"color:" + fontColor + ";\">"+ text + " </h2>");
    }
}
