package Utils;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * I noticed the local timezone was being called a lot so I made this class to simplify the code
 */
public class TimeZoneClass {
    private static ZoneId localZoneID = ZoneId.of(TimeZone.getDefault().getID());

    /**
     * @return local Zone id
     */
    public static ZoneId getLocalZoneID() {
        return localZoneID;
    }
}
