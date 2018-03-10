package iss.chase.com.ispacestation.presenter;

import android.location.Location;

import iss.chase.com.ispacestation.presenter.api.IResponseCallback;

/**
 * Created by Bikash on 3/9/2018.
 */

public interface IISSPresenter {
    /**
     * Receive Passtime from the server by providing the location
     * @param location location lat long
     * @param responseCallback Response call back
     */
    void getPassTime(Location location , IResponseCallback responseCallback) ;

}
