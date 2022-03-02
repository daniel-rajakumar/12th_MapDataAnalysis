package com.daniel.data_mapping;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.awt.*;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        var button = new Button("Click me");
        var textField = new TextField();

        add(new HorizontalLayout(button, textField));

        button.addClickListener(e -> {
            add(new Paragraph("I said: " + textField.getValue()));
            textField.clear();
        });
    }
}

