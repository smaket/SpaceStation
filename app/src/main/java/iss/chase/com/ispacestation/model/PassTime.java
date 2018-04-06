package iss.chase.com.ispacestation.model;

/**
 * Created by Bikash on 3/9/2018.
 */

public class PassTime {

    //Pass time data
    int duration;
    long risetime;

    /**
     * Rerturn Duration of the satellite
     * @return int duration of satellite on your location
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Set the duration of satellite moving on your location
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Return Rise time of satellite
     * @return
     */
    public long getRisetime() {
        return risetime;
    }

    /**
     * Set the Rise time of the satellite
     * @param risetime satellite rise time.
     */
    public void setRisetime(long risetime) {
        this.risetime = risetime;
    }
}
