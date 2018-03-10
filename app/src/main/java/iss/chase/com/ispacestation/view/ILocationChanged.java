package iss.chase.com.ispacestation.view;

import android.location.Location;

/**
 * Created by Bikash on 3/9/2018.
 */

public interface ILocationChanged {
    /**
     * Callback to receive location change
     * @param location Current location
     */
    void onLocationChanged(Location location);
}
