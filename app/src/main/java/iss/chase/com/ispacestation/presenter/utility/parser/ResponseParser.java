package iss.chase.com.ispacestation.presenter.utility.parser;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Vector;

import iss.chase.com.ispacestation.presenter.api.IHttpConnection;
import iss.chase.com.ispacestation.model.SpaceStationData;
import iss.chase.com.ispacestation.view.IISSView;


/**
 * Created by Bikash on 1/8/28018.
 * Helps to parse the respose received from the server.
 */

public class ResponseParser {

    private static final String TAG = ResponseParser.class.getSimpleName();
    /**
     * Data change listener to update to View
     */
    private static IISSView mDataChangeListner;

    /**
     * Register View to receive the Data change update.
     * @param aDataChangeListner UI View listener to receive data change notification.
     */
    public static void registerListner(IISSView aDataChangeListner) {
        mDataChangeListner = aDataChangeListner;
    }

    /**
     * Parse response
     * @param aResponse Response body received from the server
     * @param aRespType Request type to distinguish response received for which request
     * @param aRequestParam Request param
     */
    public static void parseResponse(String aResponse, IHttpConnection.IResponseObserver.RequestTypeEnum aRespType, Object aRequestParam) {

        Log.d(TAG , "parseResponse() - response " +aResponse);
        Gson lGson = new Gson();
        try {
            //Parse response data received from te satellite request server
            SpaceStationData issDetails = lGson.fromJson(aResponse, SpaceStationData.class);
//            Log.d(TAG , "weather Data " + issDetails.getName()  + "  " +issDetails.getId());
            if(mDataChangeListner != null) {
                ArrayList<SpaceStationData> lData = new ArrayList<SpaceStationData>();
                lData.add(issDetails);
                mDataChangeListner.notifyDataChange(lData);
            }
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }
}
