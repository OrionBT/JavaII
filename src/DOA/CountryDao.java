package DOA;

import Model.Country;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Country Doa as per design pattern on tutorialspoint.com/design_pattern/data_access_object_pattern.htm
 */
public interface CountryDao {
    /** Reads the countries table in the db
     * @return ObservableList of countries
     * @throws SQLException
     */
    ObservableList<Country> getAllCountries() throws SQLException;


}
