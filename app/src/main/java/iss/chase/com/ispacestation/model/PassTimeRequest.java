package iss.chase.com.ispacestation.model;

/**
 * Created by Bikash on 3/9/2018.
 *Satellite Passtime Lat long pass time data
 * "request": {
 "altitude": 100,
 "datetime": 1515203526,
 "latitude": 41.05182,
 "longitude": -73.54223,
 "passes": 5
 }

 */

public class PassTimeRequest {
    int altitude;
    double latitude;
    double longitude;
    int passes;
    long datetime;

    /**
     * Return Altitude of the satellite passes your location.
     * @return int Altitude
     */
    public int getAltitude() {
        return altitude;
    }

    /**
     * Set the altitude
     * @param altitude
     */
    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    /**
     * Return the latitude of the satellite
     * @return double  latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * set the latitude of the passing satellite
     * @param latitude passing satellite lat.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Return the Longitude
     * @return double longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set the longitude of the passing satellite
     * @param longitude passing satellite longitude.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Return number of passes
     * @return int passes number
     */
    public int getPasses() {
        return passes;
    }

    /**
     * Set the passes
     * @param passes number of passes
     */
    public void setPasses(int passes) {
        this.passes = passes;
    }

    /**
     * Return Date time of passing Satellite
     * @return long Date time
     */
    public long getDatetime() {
        return datetime;
    }

    /**
     * set Passing date time of the satellite
     * @param datetime Satelite Passing date time
     */
    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }


}
