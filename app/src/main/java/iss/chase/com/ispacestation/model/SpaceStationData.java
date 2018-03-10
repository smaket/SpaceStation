package iss.chase.com.ispacestation.model;

/**
 * Created by Bikash on 3/9/2018.
 */

public class SpaceStationData {
    String message;
    PassTimeRequest request;
    PassTime[] response;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PassTimeRequest getRequest() {
        return request;
    }

    public void setRequest(PassTimeRequest request) {
        this.request = request;
    }

    public PassTime[] getResponse() {
        return response;
    }

    public void setResponse(PassTime[] response) {
        this.response = response;
    }


}
