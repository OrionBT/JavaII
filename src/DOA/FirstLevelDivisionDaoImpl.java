package DOA;

import Model.FirstLevelDivisions;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the first Level Division data access object for CRUD operations
 */
public class FirstLevelDivisionDaoImpl implements FirstLevelDivisionDao{

    /** Read operation for the firstLevelDivision
     * @return ObservableList of FirstLevelDivision objects
     * @throws SQLException
     */
    @Override
    public ObservableList<FirstLevelDivisions> getAllFirstLevelDivisions() throws SQLException {

        ResultSet rs = DBQuery.getResultSet("select * from [first-level-divisions]");
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("COUNTRY_ID");
            FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, divisionName, countryID);
            DoaLists.addFirstLevelDivision(firstLevelDivisions);

        }
        return DoaLists.getFirstLevelDivisionsList();
    }
}
