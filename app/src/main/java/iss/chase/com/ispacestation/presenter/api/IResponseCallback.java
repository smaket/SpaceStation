package iss.chase.com.ispacestation.presenter.api;

/**
 * Created by Bikash on 3/8/2018.
 */

public interface IResponseCallback {
    /**
     * Response received from the server
     * @param status Response status
     * @param body Response Body
     * @param aRespType Response Type
     * @param requestParams Request Params
     */
    void responseReceived(int status, String body, IHttpConnection.IResponseObserver.RequestTypeEnum aRespType, Object requestParams) ;
}
