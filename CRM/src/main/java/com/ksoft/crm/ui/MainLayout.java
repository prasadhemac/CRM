package com.ksoft.crm.ui;


import com.ksoft.crm.core.EmailSender;
import com.ksoft.crm.data.Customer;
import com.ksoft.crm.service.CustomerService;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by prasadh on 9/18/2016.
 */
public class MainLayout extends MLDesign {

    private CustomerService customerService;
    private Notification notification;
    public MainLayout() {

        notification = new Notification("notification");
        console_label_view.setStyleName("black");

        info_button.setStyleName("blue");
        logout_button.setStyleName("blue");
        clear_filters_button.setStyleName("blueNormalwithoutBorder");
        client_type_label.setStyleName("blueNormalwithoutBorder");
        send_email.setStyleName("blueNormalWithBorder");
        send_sms.setStyleName("blueNormalWithBorder");
        generate_report.setStyleName("blueNormalWithBorder");

        ClassResource resource1 = new ClassResource("waters_edge.jpg");
        image_left_top.setSource(resource1);

        ClassResource resource2 = new ClassResource("cropped_WD.png");
        image_right_bottom.setSource(resource2);

        ClassResource resource3 = new ClassResource("icone_plus.png");
        image_add_button.setSource(resource3);

        middle_vl.setStyleName("white");
        table1.setStyleName("white");

        left_hl.setStyleName("layoutline");

        image_add_button.setStyleName("left_menu_title");

        image_add_button.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent clickEvent) {

                setAddClientWindow();
            }
        });

        send_email.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Object rowId = table1.getValue();
                String email = "";
                if(rowId != null)
                {
                     email = (String)table1.getContainerProperty(rowId,"email").getValue();
                }
                setSendEmailWindow(email );
            }
        });
        send_sms.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Object rowId = table1.getValue();
                String phone = "";
                if(rowId != null)
                {
                    phone = (String)table1.getContainerProperty(rowId,"phone").getValue();
                }
                setSendSMSWindow(phone);
            }
        });

        updateTabel();
        table1.addListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                if(itemClickEvent.isDoubleClick())
                {
                    Customer customer = new Customer((int)table1.getItem(itemClickEvent.getItemId()).getItemProperty("id").getValue(),
                            (String)table1.getItem(itemClickEvent.getItemId()).getItemProperty("name").getValue(),
                            (String)table1.getItem(itemClickEvent.getItemId()).getItemProperty("phone").getValue(),
                            (String)table1.getItem(itemClickEvent.getItemId()).getItemProperty("email").getValue(),
                            (String)table1.getItem(itemClickEvent.getItemId()).getItemProperty("clientType").getValue(),
                            (String)table1.getItem(itemClickEvent.getItemId()).getItemProperty("address").getValue());
                    setEditClientWindow(customer);
                }
            }
        });
        check_box_new.setValue(true);
        check_box_old.setValue(true);
        check_box_unknown.setValue(true);

        check_box_new.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                filterTable("");
            }
        });
        check_box_unknown.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                filterTable("");
            }
        });
        check_box_old.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                filterTable("");
            }
        });

        clear_filters_button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                check_box_new.setValue(false);
                check_box_unknown.setValue(false);
                check_box_old.setValue(false);
            }
        });
    }

    private void filterTable(String checkbox)
    {
        if(!checkbox.isEmpty())
        {
            if(checkbox.equals("Old")) check_box_old.setValue(true);
            if(checkbox.equals("Unknown")) check_box_unknown.setValue(true);
            if(checkbox.equals("New")) check_box_new.setValue(true);
        }
        String[] values = new String[3];
        int i = 0;
        if(check_box_old.getValue()) {
            values[i] = "Old";
            i++;
        }
        if(check_box_unknown.getValue()){
            values[i] = "Unknown";
            i++;
        }
        if(check_box_new.getValue()) {
            values[i] = "New";
        }
        customerService = new CustomerService();
        List<Customer> customers = customerService.getAllForFilter("clientType", values);
        table1.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));

        table1.setVisibleColumns(new Object[] {"id", "name", "phone", "email", "clientType", "address"});
        table1.setColumnHeaders(new String[] {"ID", "NAME", "PHONE", "E-MAIL", "CLIENT TYPE", "ADDRESS"});
        table1.setColumnReorderingAllowed(false);

    }
    private void setEditClientWindow(Customer item) {

        final Window window = new Window("Edit/Delete Client");
        window.setWidth("400px");
        window.setHeight("550px");
        final VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(true);
        window.setContent(content);
        window.center();
        window.setModal(true);
        window.setResizable(false);
        UI.getCurrent().addWindow(window);
        ///
        TextField name = new TextField("Client Name");
        name.setStyleName("text_line_blue");
        name.setWidth("300px");
        content.addComponent(name);
        ///
        TextField phone = new TextField("Phone");
        phone.setStyleName("text_line_blue");
        phone.setWidth("300px");
        content.addComponent(phone);
        ///
        TextField email = new TextField("e-mail");
        email.setStyleName("text_line_blue");
        email.setWidth("300px");
        content.addComponent(email);
        ///
        OptionGroup clientType = new OptionGroup("Client Type");
        clientType.addItem(1);
        clientType.addItem(2);
        clientType.addItem(3);
        clientType.setItemCaption(1,"Old");
        clientType.setItemCaption(2,"Unknown");
        clientType.setItemCaption(3,"New");
        content.addComponent(clientType);
        ///
        TextField address = new TextField("Address");
        address.setStyleName("text_line_blue");
        address.setWidth("300px");
        content.addComponent(address);
        ///
        HorizontalLayout cancel_edit_delete = new HorizontalLayout();
        cancel_edit_delete.setSpacing(true);
        content.addComponent(cancel_edit_delete);
        content.setComponentAlignment(cancel_edit_delete, Alignment.BOTTOM_RIGHT);
        //
        Button cancel = new Button("CANCEL");
        cancel.setStyleName("blueNormalwithoutBorder");
        cancel_edit_delete.addComponent(cancel);
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().removeWindow(window);
            }
        });
        //
        Button edit = new Button("EDIT");
        edit.setStyleName("blueNormalWithBorder");
        cancel_edit_delete.addComponent(edit);
        //
        Button delete = new Button("DELETE");
        delete.setStyleName("blueNormalWithBorder");
        cancel_edit_delete.addComponent(delete);

        cancel_edit_delete.setComponentAlignment(delete,Alignment.MIDDLE_RIGHT);
        cancel_edit_delete.setComponentAlignment(edit,Alignment.MIDDLE_RIGHT);
        cancel_edit_delete.setComponentAlignment(cancel,Alignment.MIDDLE_RIGHT);

        //
        name.setValue(item.getName());
        phone.setValue(item.getPhone());
        email.setValue(item.getEmail());
        if(item.getClientType().equals("Old"))
            clientType.select(1);
        else if(item.getClientType().equals("Unknown"))
            clientType.select(2);
        else
            clientType.select(3);

        address.setValue(item.getAddress());
        edit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                customerService.update(new Customer(item.getId(),name.getValue(),
                        phone.getValue(),
                        email.getValue(),
                        clientType.getItemCaption(clientType.getValue()),
                        address.getValue()));
                UI.getCurrent().removeWindow(window);
                filterTable(clientType.getItemCaption(clientType.getValue()));
            }
        });

        delete.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                customerService.delete(new Customer(item.getId(),name.getValue(),
                        phone.getValue(),
                        email.getValue(),
                        clientType.getItemCaption(clientType.getValue()),
                        address.getValue()));
                UI.getCurrent().removeWindow(window);
                filterTable("");
            }
        });
    }

    private void updateTabel() {
        customerService = new CustomerService();
        List<Customer> customers = customerService.getAll();
        table1.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));

        table1.setVisibleColumns(new Object[] {"id", "name", "phone", "email", "clientType", "address"});
        table1.setColumnHeaders(new String[] {"ID", "NAME", "PHONE", "E-MAIL", "CLIENT TYPE", "ADDRESS"});
        table1.setColumnReorderingAllowed(false);
    }

    private void setSendEmailWindow(String emailR) {

        final Window window = new Window("Send SMS");
        window.setWidth("350px");
        window.setHeight("500px");
        final VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(true);
        window.setContent(content);
        window.center();
        window.setModal(true);
        window.setResizable(false);
        UI.getCurrent().addWindow(window);
        ///
        TextField email = new TextField("e-mail");
        email.setStyleName("text_line_blue");
        email.setWidth("250px");
        email.setValue(emailR);
        content.addComponent(email);
        ///
        TextField heading = new TextField("Heading");
        heading.setStyleName("text_line_blue");
        heading.setWidth("250px");
        content.addComponent(heading);
        ///
        TextArea text = new TextArea("Text");
        //text.setStyleName("text_line_blue");
        text.setSizeFull();
        text.setHeight("200px");
        text.setValue("This is a example mail content\n\n\n\n\n\nFrom,\nWaters Edge.");
        content.addComponent(text);
        ///
        HorizontalLayout cancel_save = new HorizontalLayout();
        cancel_save.setSpacing(true);
        content.addComponent(cancel_save);
        content.setComponentAlignment(cancel_save, Alignment.BOTTOM_RIGHT);
        //
        Button cancel = new Button("CANCEL");
        cancel.setStyleName("blueNormalwithoutBorder");
        cancel_save.addComponent(cancel);
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().removeWindow(window);
            }
        });
        //
        Button save = new Button("SEND E-MAIL");
        save.setStyleName("blueNormalWithBorder");
        save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                EmailSender emailSender = new EmailSender();
                if(emailSender.SendTheEmail(email.getValue(), heading.getValue(),text.getValue()))
                {
                    notification.setCaption("Successfully sent");
                    notification.show(UI.getCurrent().getPage());
                    UI.getCurrent().removeWindow(window);
                }
                else{
                    notification.setCaption("Failed");
                    notification.show(UI.getCurrent().getPage());
                }
            }
        });
        cancel_save.addComponent(save);

        cancel_save.setComponentAlignment(save,Alignment.MIDDLE_RIGHT);
        cancel_save.setComponentAlignment(cancel,Alignment.MIDDLE_RIGHT);
    }

    private void setSendSMSWindow(String phonenum) {
        final Window window = new Window("Send SMS");
        window.setWidth("350px");
        window.setHeight("450px");
        final VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(true);
        window.setContent(content);
        window.center();
        window.setModal(true);
        window.setResizable(false);
        UI.getCurrent().addWindow(window);
        ///
        TextField phone = new TextField("Phone");
        phone.setStyleName("text_line_blue");
        phone.setWidth("150px");
        phone.setValue(phonenum);
        content.addComponent(phone);
        ///
        TextArea text = new TextArea("Text");
        //text.setStyleName("text_line_blue");
        text.setSizeFull();
        text.setHeight("200px");
        text.setValue("This is a example SMS content\n\n\n\n\n\nFrom,\nWaters Edge.");
        content.addComponent(text);
        ///
        HorizontalLayout cancel_save = new HorizontalLayout();
        cancel_save.setSpacing(true);
        content.addComponent(cancel_save);
        content.setComponentAlignment(cancel_save, Alignment.BOTTOM_RIGHT);
        //
        Button cancel = new Button("CANCEL");
        cancel.setStyleName("blueNormalwithoutBorder");
        cancel_save.addComponent(cancel);
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().removeWindow(window);
            }
        });
        //
        Button save = new Button("SEND SMS");
        save.setStyleName("blueNormalWithBorder");
        cancel_save.addComponent(save);

        cancel_save.setComponentAlignment(save,Alignment.MIDDLE_RIGHT);
        cancel_save.setComponentAlignment(cancel,Alignment.MIDDLE_RIGHT);
    }

    void setAddClientWindow()
    {
        final Window window = new Window("Add Client");
        window.setWidth("400px");
        window.setHeight("550px");
        final VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(true);
        window.setContent(content);
        window.center();
        window.setModal(true);
        window.setResizable(false);
        UI.getCurrent().addWindow(window);
        ///
        TextField name = new TextField("Client Name");
        name.setStyleName("text_line_blue");
        name.setWidth("300px");
        content.addComponent(name);
        ///
        TextField phone = new TextField("Phone");
        phone.setStyleName("text_line_blue");
        phone.setWidth("300px");
        content.addComponent(phone);
        ///
        TextField email = new TextField("e-mail");
        email.setStyleName("text_line_blue");
        email.setWidth("300px");
        content.addComponent(email);
        ///
        OptionGroup clientType = new OptionGroup("Client Type");
        clientType.addItem(1);
        clientType.addItem(2);
        clientType.addItem(3);
        clientType.setItemCaption(1,"Old");
        clientType.setItemCaption(2,"Unknown");
        clientType.setItemCaption(3,"New");
        content.addComponent(clientType);
        ///
        TextField address = new TextField("Address");
        address.setStyleName("text_line_blue");
        address.setWidth("300px");
        content.addComponent(address);
        ///
        HorizontalLayout cancel_save = new HorizontalLayout();
        cancel_save.setSpacing(true);
        content.addComponent(cancel_save);
        content.setComponentAlignment(cancel_save, Alignment.BOTTOM_RIGHT);
        //
        Button cancel = new Button("CANCEL");
        cancel.setStyleName("blueNormalwithoutBorder");
        cancel_save.addComponent(cancel);
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().removeWindow(window);
            }
        });
        //
        Button save = new Button("ADD CLIENT");
        save.setStyleName("blueNormalWithBorder");
        cancel_save.addComponent(save);

        cancel_save.setComponentAlignment(save,Alignment.MIDDLE_RIGHT);
        cancel_save.setComponentAlignment(cancel,Alignment.MIDDLE_RIGHT);

        save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                customerService.insert(new Customer(1,name.getValue(),
                                        phone.getValue(),
                                        email.getValue(),
                                        clientType.getItemCaption(clientType.getValue()),
                                        address.getValue()));
                UI.getCurrent().removeWindow(window);
                filterTable(clientType.getItemCaption(clientType.getValue()));
            }
        });

    }
}


