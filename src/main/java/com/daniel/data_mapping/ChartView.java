package com.daniel.data_mapping;

import com.google.gson.Gson;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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
    VerticalLayout layout_left, layout_right;

    public ChartView() {
        setSizeFull();
        layout_left = getLeftLayout();
        layout_right = getRightLayout();

        add(
                layout_right,
                layout_left
        );

    }

    VerticalLayout getLeftLayout(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setClassName("chart-vertical-layout");
        verticalLayout.setWidthFull();

        verticalLayout.getElement().appendChild((new Html(
                "<div class=\"chart\"></div>"
        )).getElement());


        UI.getCurrent().getPage().executeJs("ns.drawGraphs()");

        return verticalLayout;
    }


    VerticalLayout getRightLayout(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setClassName("chart-vertical-layout");
        verticalLayout.setWidthFull();

        ComboBox<String> one = getComboBox();
        ComboBox<String> two = getComboBox();

        verticalLayout.add(one, two);

        verticalLayout.getElement().appendChild((new Html(
                "<div id=\"chart_pie\"></div>"
        )).getElement());

        lookForMatch(one, two);

        return verticalLayout;
    }

    void lookForMatch(ComboBox<String> one, ComboBox<String> two){
        var obj = new Object() {
            String answer_one = null;
            String answer_two  = null;
        };
        one.addValueChangeListener(e -> {
            obj.answer_one = e.getValue();
            updateChartIfValid(obj.answer_one, obj.answer_two);
        });
        two.addValueChangeListener(e -> {
            obj.answer_two = e.getValue();
            updateChartIfValid(obj.answer_one, obj.answer_two);
        });
    }

    void updateChartIfValid(String one, String two){
        if (one == null || two == null) return;
        Button button_one = new Button("one");
        Button button_two = new Button("two");

        var obj_one = new LinkedHashMap<>();
        obj_one.put("salary", Storage.MAP_SALARY.get(one));
        obj_one.put("unemployment", Storage.MAP_UNEMPLOYMENT.get(one));

        var obj_two = new LinkedHashMap<>();
        obj_two.put("salary", Storage.MAP_SALARY.get(two));
        obj_two.put("unemployment", Storage.MAP_UNEMPLOYMENT.get(two));

        layout_right.add(new HorizontalLayout(button_one, button_two));
        button_one.addClickListener(e -> {
            UI.getCurrent().getPage().executeJs("ns.updatePie($0, $1)"
                    , new Gson().toJson(obj_one)
                    , new Gson().toJson(obj_two)
            );
        });
//
        UI.getCurrent().getPage().executeJs("ns.drawPie($0, $1)"
                , new Gson().toJson(obj_one)
                , new Gson().toJson(obj_two)
        );
    }

    ComboBox<String> getComboBox(){
        ComboBox<String> comboBox = new ComboBox<>("Employee");
        comboBox.setWidthFull();

        comboBox.getStyle().set("--vaadin-combo-box-overlay-width", "350px");
        ComboBox.ItemFilter<String> filter = this::matchesTerm;
        comboBox.setItems(filter, Storage.MAP_SALARY.keySet());
        comboBox.setItemLabelGenerator(person -> person);

        return comboBox;
    }


    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
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
