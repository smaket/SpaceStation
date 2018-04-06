package iss.chase.com.ispacestation;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.volleyWrapper.networkSDK.Listener.ErrorResponseListener;
import com.volleyWrapper.networkSDK.Listener.ResponseListener;
import com.volleyWrapper.networkSDK.Network.Constants;
import com.volleyWrapper.networkSDK.Network.NetworkHandler;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;
import java.util.Map;

import iss.chase.com.ispacestation.application.AppConstant;
import iss.chase.com.ispacestation.presenter.api.IHttpConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NetworkHandlerUnitTest {

    @Mock
    NetworkHandler networkHandler;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void getRequestObject() {

        networkHandler.getRequestObject(Constants.NetworkRequestType.GET, IHttpConnection.IResponseObserver.RequestTypeEnum.GET_PASSTIME, AppConstant.PASSTIME_URI, new ResponseListener(new ResponseListener.Listener() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onResponseHeaders(Map<String, String> headers, Object requestTAG) {

            }

            @Override
            public void onResponseObject(NetworkResponse response, Response<String> responseObject, Object requestTAG, Object requestParams) {
                assertEquals(response.statusCode , 200);
                assertNotNull(responseObject);

            }
        }), new ErrorResponseListener(new ErrorResponseListener.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public void onErrorResponse(VolleyError error, Object requestTag) {

            }

            @Override
            public void onErrorResponse(VolleyError error, NetworkResponse response, Object requestTAG, Object requestParams) {

            }
        }), new HashMap<>());
    }


}
