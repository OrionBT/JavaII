package DOA;

import Model.FirstLevelDivisions;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * FirstLevelDivision data access object for CRUD operations
 */
public interface FirstLevelDivisionDao {
    /** Read operation for the firstLevelDivision db
     * @return list of firstLevelDivisions
     * @throws SQLException
     */
    ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() throws SQLException;
}
