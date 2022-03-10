package com.daniel.data_mapping;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
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

    public DataView() {
        setWidthFull();
        setHeightFull();

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
//        searchField.getStyle().set("color", "white");
//        searchField.getElement().getStyle()("vaadin-icon")


        dataView.addFilter(person -> {
            String searchTerm = searchField.getValue().trim();

            if (searchTerm.isEmpty())
                return true;

            boolean matchesFullName = matchesTerm(person,
                    searchTerm);


            return matchesFullName;
        });

        getElement().getStyle().set("padding-top", "100px");


        add(searchField, grid);
    }


    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }

    Grid<String> setUpGrid(){
        Grid<String> grid = new Grid<>();
        grid.setItems(Storage.MAP_SALARY.keySet());
        grid.addColumn(key -> key)
                .setHeader(customHeaderStyle("Starting Salary"))
                .setFlexGrow(1)
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(key -> "$ " + Storage.MAP_SALARY.get(key).getStarting_salary())
                .setHeader(customHeaderStyle("Starting Salary"))
                .setFlexGrow(1)
                .setSortable(true)
                .setAutoWidth(true);

        grid.addColumn(key -> Storage.MAP_UNEMPLOYMENT.get(key).getUnemployment_rate() + " %")
                .setHeader(customHeaderStyle("Unemployment Rate"))
                .setFlexGrow(1)
                .setSortable(true)
                .setAutoWidth(true);

        grid.addSortListener(e -> {
//            new Notification(String.valueOf(System.currentTimeMillis())).open();
        });
        return grid;
    }

    String customHeaderStyle(String text){
        String fontColor = "#D2E8C3";
//        return new Html("<h2 style=\"color:" + fontColor + ";\">"+ text + " </h2>");
        return text;
    }
}







//        var button = new Button("Click me");
//        var image = new Image("https://upload.wikimedia.org/wikipedia/en/3/30/Java_programming_language_logo.svg", "java logo");
//        image.setWidthFull();
//        add(new HorizontalLayout(button, image));
//
//        Map<String, Student> map = new TreeMap<>();
//        TreeMap<String, String> treeMap = new TreeMap<>();
//        treeMap.put("dan", "DAN");
//        map.put("a", new Student("1", treeMap));
//        treeMap.put("tan", "TAN");
//        map.put("b", new Student("2", treeMap));
//        treeMap.put("sah", "SAH");
//        map.put("c", new Student("3", treeMap));
//        treeMap.put("olu", "OLU");
//        map.put("d", new Student("4", treeMap));
//
//        button.addClickListener(e -> {
//            UI.getCurrent().getPage().executeJs("ns.toggle($0)", image);
//            String[] arr = {"one", "two", "three"};
//            UI.getCurrent().getPage().executeJs("ns.printArray($0)", arr);
//            UI.getCurrent().getPage().executeJs("ns.printMap($0)", new Gson().toJson(map));
//        });
//    }
//
//    private static class Student {
//        private String name;
//        private TreeMap<String, String> treeMap;
//
//        Student(String name, TreeMap<String, String> treeMap){
//            this.name = name;
//            this.treeMap = treeMap;
//        }
//
//        @Override
//        public String toString() {
//            return "Student{" +
//                    "name='" + name + '\'' +
//                    ", treeMap=" + treeMap +
//                    '}';
//        }
//    }
//}
//
