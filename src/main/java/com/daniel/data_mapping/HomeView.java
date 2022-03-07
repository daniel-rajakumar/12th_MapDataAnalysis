package com.daniel.data_mapping;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Home")
@Route("home")
@JsModule("./script.js")
@JsModule("./headerScript.js")
@CssImport(value = "home-style.css")
public class HomeView extends VerticalLayout {

    public HomeView() {
        Tab details = new Tab("Details");
        Tab payment = new Tab("Payment");
        Tab shipping = new Tab("Shipping");

        Tabs tabs = new Tabs(details, payment, shipping);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.getElement().getStyle().set("margin-top", "85px");
        tabs.getElement().getStyle().set("background-color", "#021721");
        setPadding(false);
        tabs.setHeight("1000px");
        tabs.setWidth("250px");



        Button button = new Button("click me");
        button.getElement().getStyle().set("margin-top", "100px");
//        add(new HorizontalLayout(new Button(), tabs));
        add(new HorizontalLayout(tabs,button));
    }

}
