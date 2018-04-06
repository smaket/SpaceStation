package iss.chase.com.ispacestation.model;

/**
 * Created by Bikash on 3/9/2018.
 */

public class SpaceStationData {
    String message;
    PassTimeRequest request;
    PassTime[] response;

    /**
     * Return message
     * @return String message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Return pass time Request
     * @return PassTimeRequest object
     */
    public PassTimeRequest getRequest() {
        return request;
    }

    /**
     * set Request object
     * @param request PassTimeRequest
     */
    public void setRequest(PassTimeRequest request) {
        this.request = request;
    }

    /**
     * Return Pass Time Response
     * @return Passtime[] Response
     */
    public PassTime[] getResponse() {
        return response;
    }

    /**
     * set the Passtime array Response
     * @param response Passtime array
     */
    public void setResponse(PassTime[] response) {
        this.response = response;
    }


}
