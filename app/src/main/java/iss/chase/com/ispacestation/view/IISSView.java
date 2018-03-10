package iss.chase.com.ispacestation.view;

import java.util.ArrayList;
import java.util.Vector;

import iss.chase.com.ispacestation.model.SpaceStationData;


/**
 * Created by Bikash on 3/9/2018.
 */

public interface IISSView {
    /**
     * Notify to the UI for data changed
     * @param spaceStationsData Space station data received from the server.
     */
    public void notifyDataChange(ArrayList<SpaceStationData> spaceStationsData);
}
