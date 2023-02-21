package Controller;

import DOA.CustomerDao;
import DOA.CustomerDaoImp;
import DOA.DoaLists;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivisions;
import Utils.AlertMessage;
import Utils.SwitchView;
import Utils.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * modify a selected customer from a populated list
 */
public class ModifyCustomerController implements Initializable {
    private CustomerDao customerDao = new CustomerDaoImp();
    private ObservableList<FirstLevelDivisions> divisions = FXCollections.observableArrayList();
    @FXML
    private Text nameLabel;

    @FXML
    private Text addressLabel;

    @FXML
    private Text postalCodeLabel;

    @FXML
    private Text divisionLabel;

    @FXML
    private TextField nameTxtField;

    @FXML
    private TextField addressTxtField;

    @FXML
    private TextField postalCodeTxtField;

    @FXML
    private ComboBox<FirstLevelDivisions> divisionComboBox;

    @FXML
    private Text phoneLabel;

    @FXML
    private TextField phoneTxtField;

    @FXML
    private Text countryLabel;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private Text customerIDLabel;

    @FXML
    private TextField customerIDTextField;

    @FXML
    private Text modifyCustomerLabel;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ListView<Customer> customerList;

    @FXML
    private Button deleteCustomerBtn;

    /** deletes customer from list, and db
     *
     * @throws SQLException
     */
    @FXML
    void onActionDelete() throws SQLException {
        Customer customer = customerList.getSelectionModel().getSelectedItem();
        if(!Validation.customerHasAppointment(customer) && AlertMessage.deleteCustomerConfirmation(customer) )
        {
            customerList.setItems(DoaLists.getCustomersList());
        }
        else if (Validation.customerHasAppointment(customer)){
            AlertMessage.customerAppointment(customer);
        }

    }

    /** switches view to main screen
     * @param event on cancel button clicked
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        divisions.clear();
        SwitchView.mainPage(event);
    }

    /** saves customer object to the database, clears and repopulates the customer list
     * @param event on save button clicked
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws SQLException, IOException {
        try {
            int ID = Integer.parseInt(customerIDTextField.getText());
            String name = nameTxtField.getText();
            String address = addressTxtField.getText();
            String postalCode = postalCodeTxtField.getText();
            String phone = phoneTxtField.getText();
            int divisionID = divisionComboBox.getSelectionModel().getSelectedItem().getDivisionID();
            Customer customer = new Customer(ID, name, address, postalCode, phone, divisionID);
            CustomerDao customerDao = new CustomerDaoImp();
            if (customerDao.updateCustomer(customer)) {
                DoaLists.updateCustomer(ID, customer);
                SwitchView.mainPage(event);
            }
        }
        catch (NumberFormatException e){
            AlertMessage.selectCustomer();
        }
    }

    /** sets division combo box with the divisions associated with the selected country
     *
     */
    @FXML
    void onActionSelectDivision() {
        divisions.clear();
        for(FirstLevelDivisions f : DoaLists.getFirstLevelDivisionsList()){
            if(countryComboBox.getSelectionModel().getSelectedItem().getID() == f.getCountryID())
                divisions.add(f);
        }
        divisionComboBox.setItems(divisions);
    }

    /** after selecting a customer in the customer list the text fields, and combo boxes get populated with the correct data
     * <p>The <b>LAMBDA</b> in the stream for the FirstLevelDivision object was created so that I could avoid making a for loop to match the divisionID integer
     * in the customer object to the divisionID integer to the correct division object in the DoaList.getFirstLevelDivisionsList</p>
     * @throws NullPointerException
     */
    @FXML
    void onMouseClicked() throws NullPointerException{
        FirstLevelDivisions divisions = null;
        Customer customer = customerList.getSelectionModel().getSelectedItem();
        customerIDTextField.setText(String.valueOf(customer.getID()));
        nameTxtField.setText(customer.getName());
        addressTxtField.setText(customer.getAddress());
        postalCodeTxtField.setText(customer.getPostalCode());
        phoneTxtField.setText(customer.getPhone());
        divisions = DoaLists.getFirstLevelDivisionsList().stream().filter(d -> d.getDivisionID() == customer.getDivisionId()).findFirst().get();
        divisionComboBox.setValue(divisions);
        for (Country c : DoaLists.getCountriesList()) {
                if (c.getID() == divisions.getCountryID()) {
                    countryComboBox.setValue(c);
                    break;
                }
            }


    }


    /** initializes customerList with all the customers, countryComboBox with all the countries
     * @param url
     * @param resourceBundle sets everything to french if the program user is french speaking
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerList.setItems(DoaLists.getCustomersList());
        countryComboBox.setItems(DoaLists.getCountriesList());
        resourceBundle = ResourceBundle.getBundle("Languages_fr");
        if (Locale.getDefault().getLanguage().contains("fr")){
            modifyCustomerLabel.setText(resourceBundle.getString("ModifyCustomer"));
            customerIDLabel.setText(resourceBundle.getString("CustomerID"));
            nameLabel.setText(resourceBundle.getString("Name"));
            addressLabel.setText(resourceBundle.getString("StreetAddress"));
            postalCodeLabel.setText(resourceBundle.getString("PostalCode"));
            countryLabel.setText(resourceBundle.getString("Country"));
            divisionLabel.setText(resourceBundle.getString("State/Province/Division"));
            phoneLabel.setText(resourceBundle.getString("Phone"));
            saveBtn.setText(resourceBundle.getString("Save"));
            deleteCustomerBtn.setText(resourceBundle.getString("Delete"));
            cancelBtn.setText(resourceBundle.getString("Cancel"));
        }

    }
}
