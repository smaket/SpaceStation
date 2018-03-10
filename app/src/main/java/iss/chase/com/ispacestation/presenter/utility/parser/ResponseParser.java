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
 */

public class ResponseParser {

    private static final String TAG = ResponseParser.class.getSimpleName();

    private static IISSView mDataChangeListner;
    public static void registerListner(IISSView aDataChangeListner) {
        mDataChangeListner = aDataChangeListner;
    }

    public static void parseResponse(String aResponse, IHttpConnection.IResponseObserver.RequestTypeEnum aRespType, Object aRequestParam) {

        Log.d(TAG , "parseResponse() - response " +aResponse);
        Gson lGson = new Gson();
        try {
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
