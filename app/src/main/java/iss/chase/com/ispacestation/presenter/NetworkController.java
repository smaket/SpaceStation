package iss.chase.com.ispacestation.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.volleyWrapper.networkSDK.JobQueue.PriorityJobQueue;
import com.volleyWrapper.networkSDK.Listener.ErrorResponseListener;
import com.volleyWrapper.networkSDK.Listener.ResponseListener;
import com.volleyWrapper.networkSDK.Network.Constants;
import com.volleyWrapper.networkSDK.Network.GetRequest;
import com.volleyWrapper.networkSDK.Network.NetworkHandler;
import com.volleyWrapper.networkSDK.Network.PostRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import iss.chase.com.ispacestation.application.AppConstant;
import iss.chase.com.ispacestation.presenter.api.IHttpConnection;
import iss.chase.com.ispacestation.presenter.api.IResponseCallback;


/**
 * Created by Bikash on 3/9/2018.
 * This class helps to create the network request by using customized network SDK which is build as an wrapper on top of Volley to handle all kind of request (Post,Get,Put,Delete etc)
 * and response received from the server on call back.
 */

public class NetworkController implements ResponseListener.Listener,ErrorResponseListener.ErrorListener{
    /**
     * Self instance
     */
    private static NetworkController instance = null;
    /**
     * Response call back for response received from the server
     */
    private IResponseCallback presenter;
    /**
     * UI context
     */
    private Context mContext;
    /**
     * Network handler instance for network calls derived from network SDK to handle all N/W rquest response.
     */
    private NetworkHandler networkHandler;
    /**
     * Priority of the request to process like Immediate will always have highes priority than others.
     */
    private PriorityJobQueue priorityJobQueue;
    /**
     * Request type to distinguish the response received for which request
     */
    private IHttpConnection.IResponseObserver.RequestTypeEnum mResponseType;
    /**
     * TAG
     */
    private String TAG = NetworkController.class.getName();

    /**
     * Constructor for object initialization
     * @param aCxt UI context
     * @param presenter Presenter instance to receive the response data as callback.
     */
    private NetworkController(Context aCxt , IResponseCallback presenter) {
        this.mContext = aCxt;
        this.presenter = presenter;
        this.networkHandler = NetworkHandler.getInstance(mContext);
        this.priorityJobQueue = networkHandler.getJobQueue();
        Log.d(TAG, "Inside GatewayController ()");
    }

    /**
     * Return self instance
     * @param mContext App context
     * @param presenter
     * @return NetworkController instance
     */
    public static NetworkController getInstance(Context mContext , IResponseCallback presenter) {
        if (instance == null) instance = new NetworkController(mContext , presenter);
        return instance;
    }

    /**
     * Methods to process the network request
     */
    public void processNetworkRequest(IHttpConnection.IResponseObserver.RequestTypeEnum mResponseType, Object requestParams, Request.Priority priority) {
        this.mResponseType = mResponseType;
        switch (mResponseType) {
            case POST_CONTENT:
                Log.v(TAG, "LOGIN_AUTH_SERVICE processNetworkRequest()-->");
                postRequest(requestParams, mResponseType, priority);
                break;
            case GET_PASSTIME:
                Log.v(TAG, "Passtime processNetworkRequest()-->");
                getPassTime(requestParams, mResponseType, priority);
                break;

        }
    }

    /**
     * create  Satellite passing time request
     * @param requestParams Passing time request parameter to send it to server
     * @param mResponseType Request type
     * @param priority to process the request Immediate is always high priority than others.
     */
    private void getPassTime(Object requestParams, IHttpConnection.IResponseObserver.RequestTypeEnum mResponseType, Request.Priority priority) {
        HashMap<String, String> params = (HashMap<String, String>) requestParams;
        Log.v(TAG, "getWeatherContent() Request-->");

        Log.d(TAG, "Request is about to get the Contents  = ");
        //Hard coded URL , It can be put on config file
        String lUrl = AppConstant.PASSTIME_URI + params.get(AppConstant.LATITUDE) +"&lon="+ params.get(AppConstant.LONGITUDE) +"";

        // Forming Get request  to send it to server to receive the Satellite passtime details
        GetRequest getSanboxdata = (GetRequest) networkHandler.getRequestObject(Constants.NetworkRequestType.GET, mResponseType, lUrl, new ResponseListener(this), new ErrorResponseListener(this), requestParams);
        Log.d(TAG, "List Data Request() ->  Url=" + lUrl);
        getSanboxdata.setPriority(priority);
        getSanboxdata.setRetryPolicy(new DefaultRetryPolicy(AppConstant.SOCKET_TIMEOUT_TIME, AppConstant.RE_TRY_COUNT, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        priorityJobQueue.addToRequestQueue(getSanboxdata, AppConstant.TAG_CONTENT_RESPONSE_TYPE);
    }


    /**
     * Post Method added for Demonstration
     * @param requestParams Request parameter to post the data to server
     * @param mResponseType Request type
     * @param priority request priority type, its order from high to low are mentioned  Immediate, High,Normal low.
     */
    private void postRequest(Object requestParams, IHttpConnection.IResponseObserver.RequestTypeEnum mResponseType, Request.Priority priority) {
        Log.v(TAG, "postRequestpostRequest()-->");
        if (requestParams instanceof HashMap) {
            Log.v(TAG, "postRequest postRequest()-->requestParams check");
            HashMap<String, String> params = (HashMap<String, String>) requestParams;
            //TODO write the logic to make network call
            JSONObject luserRegistrationdata = new JSONObject();

            String lUrl = "http://lyrics.wikia.com/api.php?func=getSong&artist=Tom+Waits&song=new+coat+of+paint&fmt=json";
            // String lUrl = "https://user-auth-service-uat.cfapps.scus-10.test.cf.fedex.com/v1/user/auth";
            int requestType = Constants.NetworkRequestType.POST;
            PostRequest authRequest = (PostRequest) networkHandler.getRequestObject(requestType, mResponseType, lUrl, new ResponseListener(this), new ErrorResponseListener(this), requestParams);
            authRequest.addHeader("Content-Type", "application/json");
            authRequest.addHeader("X-locale", "en_US"); // As per working param
            authRequest.addHeader("X-version", "1");
            authRequest.setRequestBody(luserRegistrationdata.toString());
            authRequest.setEncodingType("utf-8");
            authRequest.setPriority(priority);
            authRequest.setRetryPolicy(new DefaultRetryPolicy(AppConstant.SOCKET_TIMEOUT_TIME, AppConstant.RE_TRY_COUNT, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            priorityJobQueue.addToRequestQueue(authRequest, AppConstant.TAG_POST_RESPONSE_TYPE);
        }
    }


    @Override
    public void onResponse(String response) {

    }

    @Override
    public void onResponseHeaders(Map<String, String> headers, Object requestTAG) {

    }

    /**
     * Receive response object from the server
     * @param response Network Response received from server with status code
     * @param responseObject Response body as result
     * @param requestTAG Request tag to distinguish, response received for which request
     * @param requestParams Request parameter
     */
    @Override
    public void onResponseObject(NetworkResponse response, Response<String> responseObject, Object requestTAG, Object requestParams) {

        Log.v(TAG, "onResponseObject()-->pre");
        IHttpConnection.IResponseObserver.RequestTypeEnum mResponseTypeFromRequest = (IHttpConnection.IResponseObserver.RequestTypeEnum) requestTAG;
        //Reset if response received
        presenter.responseReceived(response.statusCode, responseObject.result, mResponseTypeFromRequest, requestParams);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onErrorResponse(VolleyError error, Object requestTag) {

    }

    /**
     * Receive error response if any
     * @param error The Volley error input
     * @param response Response Object from Volley
     * @param requestTAG The request tag
     * @param requestParams The request parameters that are passed to request.
     */
    @Override
    public void onErrorResponse(VolleyError error, NetworkResponse response, Object requestTAG, Object requestParams) {
        IHttpConnection.IResponseObserver.RequestTypeEnum mResponseTypeFromRequest = (IHttpConnection.IResponseObserver.RequestTypeEnum) requestTAG;
        try {

            if (response == null || (response != null && (Integer) response.statusCode == null)) {
                presenter.responseReceived(-1, null, mResponseTypeFromRequest, requestParams);
            } else {
                presenter.responseReceived(response.statusCode, null, mResponseTypeFromRequest, requestParams);
            }
        } catch (Exception ex) {
            presenter.responseReceived(-1, null, mResponseTypeFromRequest, requestParams);
            ex.printStackTrace();
            Log.e(TAG, "error==" + ex.toString());
        }
    }
}
