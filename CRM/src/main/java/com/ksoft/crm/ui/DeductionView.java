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
public class DeductionView extends VerticalLayout implements View {

    HorizontalLayout mainLayoutContainer;
    VerticalLayout mainLayout;
    public static final String VIEW_NAME = "deduction_details";

    private DeductionService deductionService;

    private Deduction deduction;

    private Grid grid = new Grid();
    private VerticalLayout layout2 = new VerticalLayout();
    // Create the Upload component.
    private TextField epf = new TextField("EPF");
    private TextField etf = new TextField("ETF");
    private TextField welfare = new TextField("Welfare");

    private Button save = new Button("Save", e -> saveDeduction());
    private Button add = new Button("Add Deduction", e -> addDeduction());
    private Button edit = new Button("Edit Deduction", e -> editDeduction());
    private Button delete = new Button("Delete Deduction", e -> deleteDeduction());

    private Button cancel = new Button("Cancel", e -> cancelEdit());

    private boolean isEdit = true;

    public DeductionView()
    {
        updateGrid();
        grid.setColumns("id", "epf", "etf", "welfare");
        grid.addSelectionListener(e -> updateForm());

        Label header1 = new Label("All Deductions");
        VerticalLayout layout1 = new VerticalLayout(header1, grid, add);
        layout1.setMargin(true);
        layout1.setSpacing(true);

        Label header = new Label("Deduction Details");
        HorizontalLayout saveCancel = new HorizontalLayout(save, cancel);
        saveCancel.setMargin(true);
        saveCancel.setSpacing(true);


        epf.setConverter(Float.class);
        etf.setConverter(Float.class);
        welfare.setConverter(Float.class);

        HorizontalLayout editDelete = new HorizontalLayout(edit, delete);
        editDelete.setMargin(true);
        editDelete.setSpacing(true);

        layout2.addComponents(header, epf, etf, welfare, editDelete, saveCancel);
        save.setVisible(false);
        layout2.setMargin(true);
        layout2.setSpacing(true);

        HorizontalLayout layout = new HorizontalLayout(layout1, layout2);
        layout.setMargin(true);
        layout.setSpacing(true);
        addComponent(layout);
    }
    private void updateGrid() {
        deductionService = new DeductionService();
        List<Deduction> deductions = deductionService.getAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Deduction.class, deductions));
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
            deduction = (Deduction) grid.getSelectedRow();
            BeanFieldGroup.bindFieldsUnbuffered(deduction, this);
            makeReadOnly(true);
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
        epf.setReadOnly(viewMode);
        etf.setReadOnly(viewMode);
        welfare.setReadOnly(viewMode);
    }

    private void makeReadOnly(boolean readOnly)
    {
        epf.setReadOnly(true);
        etf.setReadOnly(true);
        welfare.setReadOnly(true);
    }

    private void saveDeduction() {

        if(isEdit) editDeductionEntry();
        else    addDeductionEntry();

        enableAddButton(true);
    }

    private void addDeduction() {

        enableAddButton(false);
        isEdit = false;
        enableViewMode(false);
        setFormVisible(true);
        addDeductionAdditionalSettings();
    }

    private void editDeduction() {
        enableAddButton(false);
        isEdit = true;

        enableViewMode(false);
    }

    private void addDeductionEntry()
    {
        deductionService.insert(new Deduction(1, (Float)epf.getConvertedValue(), (Float)etf.getConvertedValue()
                , (Float)welfare.getConvertedValue()));
        updateGrid();
    }

    private void editDeductionEntry()
    {
        deductionService.update(deduction);
        updateGrid();
    }

    private void deleteDeduction() {

        deductionService.delete(deduction);
        updateGrid();
        enableAddButton(true);
    }

    private void cancelEdit() {

        if(isEdit) enableViewMode(true);
        else    updateGrid();

        enableAddButton(true);
    }

    private void addDeductionAdditionalSettings()
    {
        epf.setConvertedValue(0.0);
        etf.setConvertedValue(0.0);
        welfare.setConvertedValue(0.0);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
