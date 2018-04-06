package iss.chase.com.ispacestation.presenter;

import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import iss.chase.com.ispacestation.application.AppConstant;
import iss.chase.com.ispacestation.application.ISSApplication;
import iss.chase.com.ispacestation.model.SpaceStationData;
import iss.chase.com.ispacestation.presenter.api.IHttpConnection;
import iss.chase.com.ispacestation.presenter.api.IResponseCallback;
import iss.chase.com.ispacestation.view.IISSView;


/**
 * Created by Bikash on 3/9/2018.
 */

public class ISSPresenterImp implements IISSPresenter,IResponseCallback  {

    private static final String TAG = ISSPresenterImp.class.getSimpleName();
    private static ISSPresenterImp mSelf = null;
    private IISSView mView ;

    public ISSPresenterImp(IISSView aView) {
        this.mView = aView;
    }

    /**
     * Forming getpasstime request
     * @param location Location to fetch the data from server
     * @param responseCallback response received from the server
     */
    @Override
    public synchronized void getPassTime(Location location , IResponseCallback responseCallback) {
        Log.d(TAG,"getWeatherContent()");
        NetworkController gatewayController = NetworkController.getInstance(ISSApplication.getAppContext() , responseCallback);
        HashMap<String, String> params = new HashMap<>();
        params.put(AppConstant.LATITUDE , ""+location.getLatitude());
        params.put(AppConstant.LONGITUDE , ""+location.getLongitude());
        gatewayController.processNetworkRequest(IHttpConnection.IResponseObserver.RequestTypeEnum.GET_PASSTIME,params, Request.Priority.IMMEDIATE);
    }


    @Override
    public synchronized  void responseReceived(int status, String body, IHttpConnection.IResponseObserver.RequestTypeEnum aRespType, Object requestParams) {

        Log.d(TAG, "response received resType is " + aRespType + " status " + status + " body message received =" + body);
        Log.d(TAG, "response received resType is " + aRespType + " status " + status + " body message received =" + body);
        switch (aRespType) {
            case GET_CITY_WEATHER_CONDITIONS:
                switch (status) {
                    case AppConstant.FAILURE_CONNECTION:
                        Log.d(TAG, "GET_CITY_WEATHER_CONDITIONS responseReceived()-->FAILURE_CONNECTION");
//                        break;
                    case AppConstant.INTERNAL_SERVER_ERROR:
                    case AppConstant.FORBIDDEN:
                        Log.d(TAG, "GET_CITY_WEATHER_CONDITIONS responseReceived()-->INTERNAL_SERVER_ERROR");
//                        break;
                    case AppConstant.UNAUTHORIZED:
                        Log.d(TAG, "AuthRequestCheck responseReceived()-->UNAUTHORIZED");
//                        break;
                    case AppConstant.SUCCESS_OK:
                    case AppConstant.NOT_MODIFIED:
                        parseResponse(body,aRespType, requestParams);
                        break;
                    default:
                        Log.d(TAG, "AuthRequestCheck responseReceived()-->default");
                        break;
                }

                break;
            case POST_CONTENT:
                break;
            case GET_PASSTIME:
                switch (status) {
                    case AppConstant.SUCCESS_OK:
                        parseResponse(body,aRespType, requestParams);
                        break;
                }
                break;
        }
    }

    /**
     * Parser to parse data received from server
     * @param aResponse response data
     * @param aRespType Request type to distiguish
     * @param aRequestParam request param sent
     */
    public  void parseResponse(String aResponse, IHttpConnection.IResponseObserver.RequestTypeEnum aRespType, Object aRequestParam) {

        Log.d(TAG , "parseResponse() - response " +aResponse);
        Gson lGson = new Gson();
        try {
            SpaceStationData issDetails = lGson.fromJson(aResponse, SpaceStationData.class);
//            Log.d(TAG , "weather Data " + issDetails.getName()  + "  " +issDetails.getId());
            if( mView!= null) {
                ArrayList<SpaceStationData> lData = new ArrayList<SpaceStationData>();
                lData.add(issDetails);
                mView.notifyDataChange(lData);
            }
        }catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

    }

}
