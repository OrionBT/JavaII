package Controller;

import DOA.*;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDivisions;
import Model.User;
import Utils.SwitchView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Adds a new customer as per the class title
 */
public class AddCustomerController implements Initializable {
    //private FirstLevelDivisionDao divisionDao = new FirstLevelDivisionDaoImpl();
    //private CountryDao countryDao = new CountryDaoImp();
    private ObservableList<FirstLevelDivisions> divisions = DoaLists.getFirstLevelDivisionsList();
    private ObservableList<Country> countries = DoaLists.getCountriesList();
    private ObservableList<FirstLevelDivisions> div = FXCollections.observableArrayList();
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
    private Text addCustomerLabel;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private Text countryLabel;


    /** gets the ID of the country selected in the combo box to set the first level division box with the divisions relevant to the country selected
     * @param event on country selected
     */
    @FXML
    void onActionGetCountryID(ActionEvent event) {

        div.clear();
        try {
            int countryID = countryComboBox.getSelectionModel().getSelectedItem().getID();


            for (FirstLevelDivisions d : divisions) {
                if (d.getCountryID() == countryID) {
                    div.add(d);
                }
            }
        }
        catch (NullPointerException e){
            //ignore
        }
        divisionComboBox.setItems(div);
    }

    /** changes views back to the main screen
     * @param event on cancel button clicked
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

           // DoaLists.getFirstLevelDivisionsList().clear();
            countries.clear();
            //div.clear();
            SwitchView.mainPage(event);

    }

    /** creates a customer object and saves it to the database
     * @param event on save button clicked
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws SQLException, IOException {
        String customerName = nameTxtField.getText();
        String customerAddress = addressTxtField.getText();
        String customerPostalCode = postalCodeTxtField.getText();
        FirstLevelDivisions divisions = divisionComboBox.getSelectionModel().getSelectedItem();
        int divisionID = divisions.getDivisionID();
        String customerPhoneNumber = phoneTxtField.getText();
        Customer customer = new Customer(-1, customerName, customerAddress, customerPostalCode, customerPhoneNumber, divisionID);
        /*if (DBQuery.insertStatement("INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) " +
                "Values ('" + customerName + "', '" + customerAddress + "', '" + customerPostalCode + "', '" + customerPhoneNumber + "', '" + user + "' ,'" + user + "', " + divisionID + ")"))

         */
       CustomerDao customerDao = new CustomerDaoImp();
       if(customerDao.addCustomer(customer))
        {
            DoaLists.getCustomersList().clear();
            SwitchView.mainPage(event);
        }
        else System.out.println("Error");
    }

    /** initializes country combo box
     * @param url
     * @param resourceBundle sets all the words to french if the Locale is french (before I learned I only needed to do that for the login page, oops)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       countryComboBox.setItems(countries);
        resourceBundle = ResourceBundle.getBundle("Languages_fr");
        if (Locale.getDefault().getLanguage().contains("fr")){
            addCustomerLabel.setText(resourceBundle.getString("AddCustomer"));
            nameLabel.setText(resourceBundle.getString("Name"));
            addressLabel.setText(resourceBundle.getString("StreetAddress"));
            postalCodeLabel.setText(resourceBundle.getString("PostalCode"));
            countryLabel.setText(resourceBundle.getString("Country"));
            divisionLabel.setText(resourceBundle.getString("State/Province/Division"));
            phoneLabel.setText(resourceBundle.getString("Phone"));
            saveBtn.setText(resourceBundle.getString("Save"));
            cancelBtn.setText(resourceBundle.getString("Cancel"));

        }

    }
}
