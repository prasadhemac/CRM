package com.ksoft.crm.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Window;

/**
 * Created by prasadh on 9/18/2016.
 */
@Theme("mytheme")
public class NavigatorUI extends UI {
    Navigator navigator;
    protected static final String MAINVIEW = "main";
    static Window main = new Window("Payroll Application");


    public static class Servlet extends VaadinServlet {
    }
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //setMainWindow(main);
        setContent(new MainLayout());

    }
    

}
