package DOA;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * group of Observable Lists storing the objects used in the program
 */
public class DoaLists {
    private static ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();
    private static ObservableList<Contact> contactsList = FXCollections.observableArrayList();
    private static ObservableList<Country> countriesList = FXCollections.observableArrayList();
    private static ObservableList<Customer> customersList = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivisions> firstLevelDivisionsList = FXCollections.observableArrayList();
    private static ObservableList<User> usersList = FXCollections.observableArrayList();
    private static ObservableList<User> allUsersList = FXCollections.observableArrayList();


    /**
     * @return ObservableList of appointment objects
     */
    public static ObservableList<Appointment> getAppointmentsList() {
        return appointmentsList;
    }


    /**
     * @return ObservableList of contact objects
     */
    public static ObservableList<Contact> getContactsList() {
        return contactsList;
    }


    /**
     * @return ObservableList of country objects
     */
    public static ObservableList<Country> getCountriesList() {
        return countriesList;
    }

    /**
     * @return ObservableList of customer objects
     */
    public static ObservableList<Customer> getCustomersList() {
        return customersList;
    }

    /**
     * @return ObservableList of firstLevelDivision objects
     */
       public static ObservableList<FirstLevelDivisions> getFirstLevelDivisionsList() {
        return firstLevelDivisionsList;
    }


    /** this only has the user currently logged in
     * @return ObservableList of user objects
     */
    public static ObservableList<User> getUsersList() {
        return usersList;
    }

    /** I created this one for the third report, if I modified the list the method getUsersList accesses I would have messed up too much stuff, so I made a new one.
     * @return ObservableList of all the Users
     */
    public static ObservableList<User> getAllUsersList(){
        return allUsersList;
    }

    /**
     * @param appointment adds a new appointment to the appointmentsList
     */
    public static void addAppointment(Appointment appointment){
        appointmentsList.add(appointment);
    }

    /**
     * @param contact adds a new contact to the contactsList
     */
    public static void addContact(Contact contact){
        contactsList.add(contact);
    }

    /**
     * @param country adds a new country to the countriesList
     */
    public static void addCountry(Country country){
        countriesList.add(country);
    }

    /**
     * @param customer adds a new customer to the customersList
     */
    public static void addCustomer(Customer customer){
        customersList.add(customer);
    }

    /** updates customer object within the program itself
     * @param index customer id
     * @param customer customer object to get updated
     */
    public static void updateCustomer(int index, Customer customer){
        int i = -1;
        for (Customer c : getCustomersList()){
            i++;
            if (c.getID() == index){
                getCustomersList().set(i, customer);
            }
        }
    }

    /**
     * @param firstLevelDivisions adds a new firstLevelDivision to the firstLevelDivisionsList
     */
    public static void addFirstLevelDivision(FirstLevelDivisions firstLevelDivisions){
        firstLevelDivisionsList.add(firstLevelDivisions);
    }

    /**
     * @param user add current user logged in to the UserList
     */
    public static void addUser(User user){
        usersList.add(user);
    }

    /**
     * @param user add all users to the allUsersList
     */
    public static void addAllUser(User user) { allUsersList.add(user);}
}
