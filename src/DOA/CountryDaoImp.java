package DOA;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Country Doa Implements CRUD methods for the country method
 */
public class CountryDaoImp implements CountryDao{

    /**
     * constructor for CountryDaoImpl
     */
    public CountryDaoImp() {
    }

    /** Read method for the countries table
     * @return list of countries
     * @throws SQLException
     */
    @Override
    public ObservableList<Country> getAllCountries() throws SQLException{
        ResultSet rs = DBQuery.getResultSet("SELECT * FROM countries");

        while (rs.next()) {

            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");

            DoaLists.addCountry(new Country(countryId, countryName));
        }
        return DoaLists.getCountriesList();
    }




}
