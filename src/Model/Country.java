package Model;


/**
 * country class for the countries table
 */
public class Country {
    private final int ID;
    private final String name;


    /** constructor
     * @param ID country id
     * @param name country name
     */
    public Country(int ID, String name) {
        this.ID = ID;
        this.name = name;

    }

    /**get country id
     * @return
     */
    public int getID() {
        return ID;
    }


    /**to string country method
     * @return
     */
    public String toString() {return (name);}
}
