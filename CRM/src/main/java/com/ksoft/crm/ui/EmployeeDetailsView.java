package com.ksoft.crm.ui;

import com.ksoft.crm.data.Salary;
import com.ksoft.crm.service.EmployeeService;
import com.ksoft.crm.data.Employee;
import com.ksoft.crm.service.SalaryService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class EmployeeDetailsView extends VerticalLayout implements View, Upload.Receiver, Upload.SucceededListener, Upload.FailedListener, Upload.StartedListener {

    HorizontalLayout mainLayoutContainer;
    VerticalLayout mainLayout;
    public static final String VIEW_NAME = "employee_details";

    private EmployeeService employeeService;
    private SalaryService salaryService;

    private Employee employee;
    private VerticalLayout layout2 = new VerticalLayout();
    // Create the Upload component.
    private final Upload upload = new Upload("Upload the file here", this);
    private Grid grid = new Grid();
    private TextField name = new TextField("Name");
    private TextField address = new TextField("Address");
    private TextField telephone = new TextField("Telephone");
    private TextField role = new TextField("role");
    private Button save = new Button("Save", e -> saveCustomer());
    private Button addProfile = new Button("Add Profile", e -> addCustomer());
    private Button editProfile = new Button("Edit Profile", e -> editCustomer());
    private Button deleteProfile = new Button("Delete Profile", e -> deleteCustomer());

    private ComboBox salaryList = new ComboBox("Salary ID");

    private Button cancel = new Button("Cacel", e -> cancelEdit());


    private boolean isEdit = true;
    private OptionGroup gender = new OptionGroup("Gender");


    VerticalLayout imagePanel;
    File file;

   public EmployeeDetailsView() {

        //setSizeFull();
        updateGrid();
        updateCombo(1);
        grid.setColumns("id","name", "address", "telephone", "role", "salary");
        grid.addSelectionListener(e -> updateForm());
        name.setStyleName("align-right");
        Label header1 = new Label("All Profiles");
        VerticalLayout layout1 = new VerticalLayout(header1, grid, addProfile);
        layout1.setMargin(true);
        layout1.setSpacing(true);

        // Use a custom button caption instead of plain "Upload".
        upload.setButtonCaption("Upload Now");

        // Listen for events regarding the success of upload.
        upload.addListener((Upload.SucceededListener) this);
        upload.addListener((Upload.FailedListener) this);
        upload.addListener((Upload.StartedListener) this);

        gender.addItems("Male", "Female");
        gender.select("Male");

        imagePanel = new VerticalLayout();
        imagePanel.addComponent(new Label("Profile Image"));
        Label header = new Label("Profile Details");
        HorizontalLayout saveCancel = new HorizontalLayout(save, cancel);
        saveCancel.setMargin(true);
        saveCancel.setSpacing(true);

        HorizontalLayout editDelete = new HorizontalLayout(editProfile, deleteProfile);
        editDelete.setMargin(true);
        editDelete.setSpacing(true);

        layout2.addComponents(header, imagePanel, upload, gender, name, address, telephone, role,salaryList,
                editDelete, saveCancel);
        save.setVisible(false);
        layout2.setMargin(true);
        layout2.setSpacing(true);

        HorizontalLayout layout = new HorizontalLayout(layout1, layout2);
        layout.setMargin(true);
        layout.setSpacing(true);
        addComponent(layout);
        //setContent(layout);
    }
    private void updateGrid() {
        employeeService = new EmployeeService();
        List<Employee> employees = employeeService.getAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Employee.class, employees));
        setFormVisible(false);
        //makeReadOnly(true);
    }

    private void updateCombo(int select){

        salaryService = new SalaryService();
        List<Salary> salaries = salaryService.getAll();
        salaryList.setContainerDataSource(new BeanItemContainer<>(Salary.class, salaries));
        salaryList.setItemCaptionPropertyId("id");
        salaryList.setNullSelectionAllowed(false);

        for(int i = 0; i < salaries.size(); i++)
        {
            Salary salary = salaries.get(i);
            if(salary.getId() == select)
            {
                salaryList.select(salary);
                break;
            }
        }

        //setFormVisible(false);
    }
    private void selectCombo(int select){

        List<Salary> salaries = salaryService.getAll();
        for(int i = 0; i < salaries.size(); i++)
        {
            Salary salary = salaries.get(i);
            if(salary.getId() == select)
            {
                salaryList.select(salary);
                break;
            }
        }

        //setFormVisible(false);
    }
    private void updateForm() {
        if (grid.getSelectedRows().isEmpty()) {
            //setFormVisible(false);
        } else {
            enableViewMode(false);
            employee = (Employee) grid.getSelectedRow();
            BeanFieldGroup.bindFieldsUnbuffered(employee, this);
            makeReadOnly(true);
            name.setStyleName("align-right");
            imagePanel.removeAllComponents();

            Image image = new Image("", createStreamResource());
            imagePanel.addComponent(image);
            updateCombo(employee.getSalary());
            setFormVisible(true);
            enableViewMode(true);
        }
    }
    private void enableViewMode(boolean viewMode)
    {
        upload.setVisible(!viewMode);
        save.setVisible(!viewMode);
        cancel.setVisible(!viewMode);
        gender.setVisible(!viewMode);

        //addProfile.setVisible(viewMode);
        editProfile.setVisible(viewMode);
        deleteProfile.setVisible(viewMode);
        name.setReadOnly(viewMode);
        address.setReadOnly(viewMode);
        telephone.setReadOnly(viewMode);
        role.setReadOnly(viewMode);
        salaryList.setReadOnly(viewMode);
    }
    private void enableAddProfileButtone(boolean enable)
    {
        addProfile.setVisible(enable);
    }
    private void makeReadOnly(boolean readOnly)
    {
        Iterator<Component> i = this.getComponentIterator();
        while (i.hasNext()) {
            Component c = i.next();
            if (c instanceof com.vaadin.ui.AbstractField) {
                AbstractField field = (AbstractField)c;
                field.setReadOnly(true);
            }
        }
        name.setReadOnly(true);
        address.setReadOnly(true);
        telephone.setReadOnly(true);
        role.setReadOnly(true);
    }
    private StreamResource createStreamResource(){
        return new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(employee.getPhoto(), "png", bos);
                    return new ByteArrayInputStream(bos.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }, "dateImage.png");
    }
    private void setFormVisible(boolean visible) {
        //firstName.setVisible(visible);
        //lastName.setVisible(visible);
        //save.setVisible(visible);
        //grid_employee_details.setVisible(visible);
        layout2.setVisible(visible);

    }

    private void saveCustomer() {
        if(isEdit) editEmployee();
        else    addEmployee();

        enableAddProfileButtone(true);
    }
    private void cancelEdit() {
        if(isEdit) enableViewMode(true);
        else    updateGrid();

        enableAddProfileButtone(true);

    }
    private void addProfileAdditionalSettings()
    {
        imagePanel.removeAllComponents();
        name.clear();
        address.clear();
        telephone.clear();
        role.clear();
        gender.setVisible(true);
        salaryList.select(0);

    }
    private void addCustomer() {
        enableAddProfileButtone(false);
        isEdit = false;
        enableViewMode(false);
        setFormVisible(true);
        addProfileAdditionalSettings();

    }
    private void editCustomer() {
        enableAddProfileButtone(false);
        isEdit = true;

        enableViewMode(false);
        gender.setVisible(false);
    }
    private void deleteCustomer() {

        employeeService.delete(employee);
        updateGrid();
        enableAddProfileButtone(true);
    }
    private void editEmployee()
    {
        if(file != null) {
            InputStream photo = null;
            try {
                photo = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            employee.setPhoto(image);
        }
        employee.setSalary(((Salary)salaryList.getConvertedValue()).getId());
        employeeService.update(employee);
        updateGrid();
    }
    private void addEmployee()
    {
        BufferedImage image = null;

        if(file == null) {
            if(gender.isSelected("Male"))
                file = new File("C:/Users/prasadh/Downloads/male_employee.jpg");
            else
                file = new File("C:/Users/prasadh/Downloads/female_employee.png");
        }
        InputStream photo = null;
        try {
            photo = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        employeeService.insert(new Employee(1, name.getValue(), address.getValue(), telephone.getValue(),
                role.getValue(),image, ((Salary)salaryList.getConvertedValue()).getId()));
        updateGrid();
    }

    @Override
    // Callback method to begin receiving the upload.
    public OutputStream receiveUpload(String filename,
                                      String MIMEType) {
        System.out.println("________________ RECEIVED UPLOAD");
        FileOutputStream fos = null; // Output stream to write to
        file = new File("C:/Users/prasadh/Downloads/" + filename);
        try {
            // Open the file for writing.
            fos = new FileOutputStream(file);
        } catch (final java.io.FileNotFoundException e) {
            // Error while opening the file. Not reported here.
            e.printStackTrace();
            return null;
        }

        return fos; // Return the output stream to write to
    }
    // This is called if the upload is finished.
    public void uploadSucceeded(Upload.SucceededEvent event) {
        System.out.println("________________ UPLOAD SUCCEEDED");
        // Log the upload on screen.
//        root.addComponent(new Label("File " + event.getFilename()
//                + " of type '" + event.getMIMEType()
//                + "' uploaded."));

        // Display the uploaded file in the image panel.
        final FileResource imageResource = new FileResource(file);
        imagePanel.removeAllComponents();

        imagePanel.addComponent(new Embedded("", imageResource));
    }

    // This is called if the upload fails.
    public void uploadFailed(Upload.FailedEvent event) {
        System.out.println("________________ UPLOAD FAILED");
        // Log the failure on screen.
//        root.addComponent(new Label("Uploading "
//                + event.getFilename() + " of type '"
//                + event.getMIMEType() + "' failed."));
    }

    public void uploadStarted(Upload.StartedEvent event) {
        System.out.println("________________ UPLOAD STARTET");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
