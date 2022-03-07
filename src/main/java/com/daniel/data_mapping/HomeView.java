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
    Label label;

    public HomeView() {
        setSizeFull();
//        setPadding(false);
        Button button = new Button("click me");
        button.getElement().getStyle().set("margin-top", "100px");
//        flexLayout.setFlexGrow(30);
//        flexLayout.setFlexBasis("30px");
//        FlexLayout flexLayout = new FlexLayout(setUpTabs(tabTitles), getLabel());
////        flexLayout.setFlexGrow(0);
//        flexLayout.setWidthFull();
//        flexLayout.
        label = getLabel();
        add(
                setUpTabs(tabTitles),
                label
        );

    }

    Label getLabel(){
        label = new Label();
        label.setClassName("home-Label");
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
        tabs.addSelectedChangeListener(e -> label.setText(getTabInfo(e.getSelectedTab().getLabel())));
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
