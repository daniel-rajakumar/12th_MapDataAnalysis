package com.daniel.data_mapping;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import elemental.json.JsonObject;
import org.springframework.boot.json.GsonJsonParser;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

@Route("")
@JsModule("./script.js")
@JsModule("https://code.jquery.com/jquery-3.6.0.min.js")
@JsModule("https://d3js.org/d3.v7.min.js")
public class MainView extends VerticalLayout {

    public MainView() {
        new DataLoading();
        var button = new Button("Click me");
        var image = new Image("https://upload.wikimedia.org/wikipedia/en/3/30/Java_programming_language_logo.svg", "java logo");
        image.setWidthFull();
        add(new HorizontalLayout(button, image));

        Map<String, Student> map = new TreeMap<>();
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("dan", "DAN");
        map.put("a", new Student("1", treeMap));
        treeMap.put("tan", "TAN");
        map.put("b", new Student("2", treeMap));
        treeMap.put("sah", "SAH");
        map.put("c", new Student("3", treeMap));
        treeMap.put("olu", "OLU");
        map.put("d", new Student("4", treeMap));

        button.addClickListener(e -> {
            UI.getCurrent().getPage().executeJs("ns.toggle($0)", image);
            String[] arr = {"one", "two", "three"};
            UI.getCurrent().getPage().executeJs("ns.printArray($0)", arr);
            UI.getCurrent().getPage().executeJs("ns.printMap($0)", new Gson().toJson(map));
        });
    }

    private static class Student {
        private String name;
        private TreeMap<String, String> treeMap;

        Student(String name, TreeMap<String, String> treeMap){
            this.name = name;
            this.treeMap = treeMap;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", treeMap=" + treeMap +
                    '}';
        }
    }
}

