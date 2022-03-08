package com.daniel.data_mapping;

import com.google.gson.Gson;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;

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
@CssImport("chart-style.css")
public class ChartView extends HorizontalLayout {
    String[] tabTitles = {"Overview", "Compare"};
    Component component_left, component_right;

    public ChartView() {
        setSizeFull();
        VerticalLayout layout_left = new VerticalLayout();
        VerticalLayout layout_right = new VerticalLayout();
//        add(
//                getLayout(layout_left),
//                getLayout(layout_right)
//        );

        UI.getCurrent().getPage().executeJs("ns.printMap($0)", new Gson().toJson(Storage.MAP_SALARY));
//        LinearRegression linearRegression = new LinearRegression(null, null);


    }
    VerticalLayout getLayout(VerticalLayout label){
//        label = new Label();
        label.setClassName("chart-vertical-layout");
        label.setWidthFull();
        return label;
    }

    Tabs setUpTabs(String... tab){
        Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.getElement().getStyle().set("margin-top", "85px");
        tabs.getElement().getStyle().set("background-color", "#021721");
//        tabs.setWidth("300px");
        tabs.setWidth("300px");
//        tabs.addSelectedChangeListener(e -> label1.setText(getTabInfo(e.getSelectedTab().getLabel())));
        Arrays.stream(tab).forEach(e -> tabs.add(new Tab(e)));
        return tabs;
    }


    String getTabInfo(String title){
        switch (title){
            case "Topic": return "Topic goes here";
            case "Hypothesis": return "Hypothesis goes here";
            case "Result": return "Result goes here";
            case "Conclusion": return "Conclusion goes here";
            case "Citation": return "Citation goes here";
            default: return "";
        }
    }

}
