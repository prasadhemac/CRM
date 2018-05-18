package com.ksoft.crm.ui;

import com.ksoft.crm.data.Salary;
import com.ksoft.crm.service.DeductionService;
import com.ksoft.crm.data.Deduction;
import com.ksoft.crm.service.SalaryService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by prasadh on 9/24/2016.
 */
public class SalaryView extends VerticalLayout implements View {

    HorizontalLayout mainLayoutContainer;
    VerticalLayout mainLayout;
    public static final String VIEW_NAME = "salary_details";

    private SalaryService salaryService;
    private DeductionService deductionService;

    private Salary salary;

    private Grid grid = new Grid();
    private VerticalLayout layout2 = new VerticalLayout();
    // Create the Upload component.
    private TextField basicSalary = new TextField("Basic Salary");
    private TextField allowance = new TextField("Allowance");
    private TextField otRate = new TextField("OT Rate");
    private ComboBox deductionList = new ComboBox("Deduction ID");

    private Button save = new Button("Save", e -> saveSalary());
    private Button add = new Button("Add Salary", e -> addSalary());
    private Button edit = new Button("Edit Salary", e -> editSalary());
    private Button delete = new Button("Delete Salary", e -> deleteSalary());

    private Button cancel = new Button("Cancel", e -> cancelEdit());

    private boolean isEdit = true;

    public SalaryView()
    {
        updateGrid();
        updateCombo(1);
        grid.setColumns("id","basicSalary", "allowance", "otRate", "deduction");
        grid.addSelectionListener(e -> updateForm());

        Label header1 = new Label("All Salaries");
        VerticalLayout layout1 = new VerticalLayout(header1, grid, add);
        layout1.setMargin(true);
        layout1.setSpacing(true);

        Label header = new Label("Salary Details");
        HorizontalLayout saveCancel = new HorizontalLayout(save, cancel);
        saveCancel.setMargin(true);
        saveCancel.setSpacing(true);

        HorizontalLayout editDelete = new HorizontalLayout(edit, delete);
        editDelete.setMargin(true);
        editDelete.setSpacing(true);

        basicSalary.setConverter(Float.class);
        allowance.setConverter(Float.class);
        otRate.setConverter(Float.class);


        layout2.addComponents(header, basicSalary, allowance, otRate, deductionList, editDelete, saveCancel);
        save.setVisible(false);
        layout2.setMargin(true);
        layout2.setSpacing(true);

        HorizontalLayout layout = new HorizontalLayout(layout1, layout2);
        layout.setMargin(true);
        layout.setSpacing(true);
        addComponent(layout);
    }
    private void updateCombo(int select){

        deductionService = new DeductionService();
        List<Deduction> deductions = deductionService.getAll();
        deductionList.setContainerDataSource(new BeanItemContainer<>(Deduction.class, deductions));
        deductionList.setItemCaptionPropertyId("id");
        deductionList.setNullSelectionAllowed(false);

        for(int i = 0; i < deductions.size(); i++)
        {
            Deduction deduction = deductions.get(i);
            if(deduction.getId() == select)
            {
                deductionList.select(deduction);
            }
        }

        setFormVisible(false);
    }
    private void updateGrid() {
        salaryService = new SalaryService();
        List<Salary> salaries = salaryService.getAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Salary.class, salaries));
        setFormVisible(false);
        //makeReadOnly(true);
    }

    private void setFormVisible(boolean visible) {
        layout2.setVisible(visible);
    }

    private void updateForm() {
        if (grid.getSelectedRows().isEmpty()) {
            //setFormVisible(false);
        } else {
            enableViewMode(false);
            salary = (Salary) grid.getSelectedRow();
            BeanFieldGroup.bindFieldsUnbuffered(salary, this);
            updateCombo(salary.getDeduction());
            //makeReadOnly(true);
            setFormVisible(true);
            enableViewMode(true);
        }
    }

    private void enableAddButton(boolean enable)
    {
        add.setVisible(enable);
    }

    private void enableViewMode(boolean viewMode)
    {
        save.setVisible(!viewMode);
        cancel.setVisible(!viewMode);

        //addProfile.setVisible(viewMode);
        edit.setVisible(viewMode);
        delete.setVisible(viewMode);
        basicSalary.setReadOnly(viewMode);
        allowance.setReadOnly(viewMode);
        otRate.setReadOnly(viewMode);
        deductionList.setReadOnly(viewMode);
    }

    private void makeReadOnly(boolean readOnly)
    {
        basicSalary.setReadOnly(true);
        allowance.setReadOnly(true);
        otRate.setReadOnly(true);
    }

    private void saveSalary() {

        if(isEdit) editSalaryEntry();
        else    addSalaryEntry();

        enableAddButton(true);
    }

    private void addSalary() {

        enableAddButton(false);
        isEdit = false;
        enableViewMode(false);
        setFormVisible(true);
        addSalaryAdditionalSettings();
    }

    private void editSalary() {
        enableAddButton(false);
        isEdit = true;

        enableViewMode(false);
    }

    private void addSalaryEntry()
    {
        salaryService.insert(new Salary(1, (Float)basicSalary.getConvertedValue(), (Float)allowance.getConvertedValue()
                , (Float)otRate.getConvertedValue(), ((Deduction)deductionList.getConvertedValue()).getId()));
        updateGrid();
    }

    private void editSalaryEntry()
    {
        salary.setDeduction(((Deduction)deductionList.getConvertedValue()).getId());
        salaryService.update(salary);
        updateGrid();
    }

    private void deleteSalary() {

        salaryService.delete(salary);
        updateGrid();
        enableAddButton(true);
    }

    private void cancelEdit() {

        if(isEdit) enableViewMode(true);
        else    updateGrid();

        enableAddButton(true);
    }

    private void addSalaryAdditionalSettings()
    {
        basicSalary.setConvertedValue(0.0);
        allowance.setConvertedValue(0.0);
        otRate.setConvertedValue(0.0);
        deductionList.select(0);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
