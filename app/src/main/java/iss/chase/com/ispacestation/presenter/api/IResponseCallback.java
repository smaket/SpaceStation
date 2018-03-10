package iss.chase.com.ispacestation.presenter.api;

/**
 * Created by Bikash on 3/8/2018.
 */

public interface IResponseCallback {
    void responseReceived(int status, String body, IHttpConnection.IResponseObserver.RequestTypeEnum aRespType, Object requestParams) ;
}
