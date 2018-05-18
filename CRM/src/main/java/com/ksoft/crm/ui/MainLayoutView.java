package com.ksoft.crm.ui;

import com.ksoft.crm.data.Deduction;
import com.ksoft.crm.service.DeductionService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by prasadh on 9/25/2016.
 */
public class MainLayoutView extends VerticalLayout implements View {

    HorizontalLayout mainLayoutContainer;
    VerticalLayout mainLayout;
    public static final String VIEW_NAME = "main_layout_view";


    public MainLayoutView()
    {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
