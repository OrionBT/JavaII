package Model;


/**
 * FirstLevelDivision class for the firleveldivision table
 */
public class FirstLevelDivisions {
    private int divisionID;
    private String divisionName;

    private int countryID;

    /** constructor
     * @param divisionID integer
     * @param divisionName string
     * @param countryID integer
     */
    public FirstLevelDivisions(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**get division id integer
     * @return divisionID integer
     */
    public int getDivisionID() {
        return divisionID;
    }


    /** get country id
     * @return countryID integer
     */
    public int getCountryID() {
        return countryID;
    }


    /**
     * @return string of division name
     */
    public String toString() {return (divisionName);}
}
