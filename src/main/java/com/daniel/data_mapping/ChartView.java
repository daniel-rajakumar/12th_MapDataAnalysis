package com.daniel.data_mapping;

import com.google.gson.Gson;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.LinkedHashMap;

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
    VerticalLayout layout_right, layout_left;

    public ChartView() {
        setSizeFull();
        layout_right = getRightLayout();
        layout_left = getLeftLayout();

        add(
                layout_left,
                layout_right
        );

    }

    // setup right layout (linear reg graph)
    VerticalLayout getRightLayout(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setClassName("chart-vertical-layout");
        verticalLayout.setWidthFull();

        Label label = new Label("College Major Starting Salary verses College Major Unemployment Rate");
        label.getElement().getStyle().set("color", "white");
        label.getElement().getStyle().set("font-size", "1.5em");
        verticalLayout.add(label);
        verticalLayout.getElement().appendChild((new Html(
                "<div class=\"chart\"></div>"
        )).getElement());

        verticalLayout.addClassName("toCenter");

        // pass values to javascript
        UI.getCurrent().getPage().executeJs("ns.drawLinearReg($0, $1)"
                , new Gson().toJson(Storage.MAP_SALARY)
                , new Gson().toJson(Storage.MAP_UNEMPLOYMENT)
        );

        return verticalLayout;
    }


    // setup left layout (pie charts and dropdowns)
    VerticalLayout getLeftLayout(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setClassName("chart-vertical-layout");
        verticalLayout.setWidthFull();

        ComboBox<String> one = getComboBox("Major 1");
        ComboBox<String> two = getComboBox("Major 2");

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
            String answer_two = null;
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

    Button button_one = new Button("Salary");
    Button button_two = new Button("Unemployment");


    boolean isFirstTime = true;

    // update pie charts if the user has chosen two valid majors.
    void updateChartIfValid(String one, String two){
        if (one == null || two == null) return;

        System.out.println("one: " + one);
        System.out.println("two: " + two);

        var obj_one = new LinkedHashMap<>();
        obj_one.put("salary", Storage.MAP_SALARY.get(one));
        obj_one.put("unemployment", Storage.MAP_UNEMPLOYMENT.get(one));

        var obj_two = new LinkedHashMap<>();
        obj_two.put("salary", Storage.MAP_SALARY.get(two));
        obj_two.put("unemployment", Storage.MAP_UNEMPLOYMENT.get(two));


        layout_left.addClassName("toCenter");


        if (isFirstTime) {
            HorizontalLayout horizontalLayout = new HorizontalLayout(button_one, button_two);
            horizontalLayout.addClassName("toCenter");
            layout_left.add(horizontalLayout);

            // pass values to javascript
            UI.getCurrent().getPage().executeJs("ns.drawPie($0, $1)"
                    , new Gson().toJson(obj_one)
                    , new Gson().toJson(obj_two)
            );
        } else {
            // pass values to javascript
            UI.getCurrent().getPage().executeJs("ns.updatePie($0, $1, $2)"
                    , new Gson().toJson(obj_one)
                    , new Gson().toJson(obj_two)
                    , true);
        }

        button_one.addClickListener(e ->  {
            // pass values to javascript
            UI.getCurrent().getPage().executeJs("ns.updatePie($0, $1, $2)"
                    , new Gson().toJson(obj_one)
                    , new Gson().toJson(obj_two)
                    , true);
        });

        button_two.addClickListener(e ->  {
            // pass values to javascript
            UI.getCurrent().getPage().executeJs("ns.updatePie($0, $1, $2)"
                    , new Gson().toJson(obj_one)
                    , new Gson().toJson(obj_two)
                    , false);
        });


        isFirstTime = false;
    }

    // the dropdown menu.
    ComboBox<String> getComboBox(String title){
        ComboBox<String> comboBox = new ComboBox<>(title);
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

}
