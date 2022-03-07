package com.daniel.data_mapping;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;

@PageTitle("Home")
@Route("home")
@JsModule("./script.js")
@JsModule("./headerScript.js")
@CssImport(value = "home-style.css")
public class HomeView extends FlexLayout {
    String[] tabTitles = {"Topic", "Hypothesis", "Result", "Conclusion", "Citation"};

    public HomeView() {
        setHeightFull();
//        setPadding(false);
        Button button = new Button("click me");
        button.getElement().getStyle().set("margin-top", "100px");
        Label textField = new Label("hello world, how are you doing? I think you are doing great. This is cool. one two three four");
//        textField.setSizeFull();
        textField.setClassName("home-Label");
        FlexLayout flexLayout = new FlexLayout(setUpTabs(tabTitles), textField);
//        flexLayout.setFlexGrow(30);
//        flexLayout.setFlexBasis("30px");
        add(flexLayout);
    }

    Tabs setUpTabs(String... tab){
        Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.getElement().getStyle().set("margin-top", "85px");
        tabs.getElement().getStyle().set("background-color", "#021721");
//        tabs.setHeight("1000px");
//        tabs.setHeightFull();
        tabs.setWidth("300px");

        Arrays.stream(tab).forEach(e -> tabs.add(new Tab(e)));
        return tabs;
    }


}
