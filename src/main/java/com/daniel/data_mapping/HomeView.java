package com.daniel.data_mapping;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Arrays;

@PageTitle("Home")
@Route("")
@JsModule("./script.js")
@JsModule("./headerScript.js")
@CssImport(value = "home-style.css")
public class HomeView extends FlexLayout {
    String[] tabTitles = {"Topic", "Poll", "Hypothesis", "Citation", "Result", "Conclusion", "Poll Result"};
    Span span;

    public HomeView() {
        setSizeFull();
        span = getLabel();
        add(
                setUpTabs(tabTitles),
                span
        );
    }

    Span getLabel(){
        span = new Span();
        span.setClassName("home-Label");
        span.setWidthFull();
        return span;
    }

    Tabs setUpTabs(String... tab){
        Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.getElement().getStyle().set("margin-top", "85px");
        tabs.getElement().getStyle().set("background-color", "#021721");
        tabs.setWidth("300px");
        tabs.addSelectedChangeListener(e -> {
            if (e.getSelectedTab().getLabel().equals("Poll Result")){
                span.getElement().setProperty("innerHTML", "Poll Result");
                String url = "https://forms.office.com/pages/designpagev2.aspx?auth_pvr=WindowsLiveId&auth_upn=danielelvisr%40outlook.com&origin=OfficeDotCom&lang=en-US&sessionid=0232cda8-9673-4af3-8fcf-59b324198eca&route=Start&subpage=design&id=DQSIkWdsW0yxEjajBLZtrQAAAAAAAAAAAANAAR4bfJxUM1JOTUtNQkpEUEtYTFdZM0s1QzNOUjFRNy4u&analysis=true&branchingelementid=r0c9a6b29331743f29df45a0062b9c0ba&tab=0";
                UI.getCurrent().getPage().executeJs("window.open(\""+ url + "\", \"Result\" , \"popup\");");
            } else {
                span.getElement().setProperty("innerHTML", getTabInfo(e.getSelectedTab().getLabel()));
            }
        });
        Arrays.stream(tab).forEach(e -> tabs.add(new Tab(e)));
        return tabs;
    }

    String getTabInfo(String title){
        switch (title){
            case "Topic": return "Starting Salary of College Majors vs Unemployment Rate of College Majors.";
            case "Hypothesis": return "The higher the starting salary of a college major is, the higher the unemployment rate.";
            case "Poll": return "<img class=\"qrCode\" src=\"https://raw.githubusercontent.com/kingDaniel2004/12th_MapDataAnalysis/main/res/code.png\" alt=\"QRCode\">";
            case "Result": return "I used linear regression to analyze my data sets. <br> x = Salary <br> y = Unemployment Rate <br><br> Visualizing the data shows that there is hardly any relationship between starting salary and the unemployment of a college major as the data plots are all over the graphs. ";
            case "Conclusion": return "My hypothesis was proven to be incorrect based on the data analysis outcomes. Therefore, I can conclude that there is no relationship between the starting salary of a major vs its unemployment.  I could have gotten data from the same year in order to find the most accurate conclusion. There are many factors that play a role in the unemployment of a major, for example, the type of college the graduate attended or the location.";
            case "Citation": return
                    "[First dataset: <a class=\"cite\" target=\"_blank\" href=\"https://www.kaggle.com/cdelany7/exploration-of-college-salaries-by-major/data\"> Kaggle.com </a>] <br> " +
                    "[Second dataset: <a class=\"cite\" target=\"_blank\" href=\"https://www.newyorkfed.org/research/college-labor-market/college-labor-market_compare-majors.html\"> newyorkfed.org </a>] <br>" +
                    "One potential issue from my two datasets is the gaps between the two datasets. The salary dataset is from 2017 while the unemployment rate was last updated in 2021. This could have played a little role in the data analysis; however, it is highly doubtful that the outcome of my conclusion would have been changed, otherwise. ";
//            case "End": return "<iframe src = \"https://forms.office.com/pages/designpagev2.aspx?auth_pvr=WindowsLiveId&auth_upn=danielelvisr%40outlook.com&origin=OfficeDotCom&lang=en-US&sessionid=0232cda8-9673-4af3-8fcf-59b324198eca&route=Start&subpage=design&id=DQSIkWdsW0yxEjajBLZtrQAAAAAAAAAAAANAAR4bfJxUM1JOTUtNQkpEUEtYTFdZM0s1QzNOUjFRNy4u&analysis=true&branchingelementid=r0c9a6b29331743f29df45a0062b9c0ba&tab=0\" width=\"600px\" height=\"600px\"> </iframe>";

            default: return "";
        }

    }


}
