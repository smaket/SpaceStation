package iss.chase.com.ispacestation.application;

/**
 * Created by Bikash on 03/9/2018.
 */

public final class AppConstant {
    // Request URI
    public static final String PASSTIME_URI = "http://api.open-notify.org/iss-pass.json?lat=";

    // Lat long keys
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "long";

    // Network time out constants
    public static final int SOCKET_TIMEOUT_TIME = 3000;
    public static final int RE_TRY_COUNT = 3;

    //Request Tag constants
    public static final String TAG_CONTENT_RESPONSE_TYPE = "get-content";
    public static final String TAG_POST_RESPONSE_TYPE = "post-content";


    //Response Code Constants
    public static final int SUCCESS_OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;
    public static final int FAILURE_CONNECTION = -1;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int CREDENTIAL_CHANGE = 402;
    public static final int FORBIDDEN = 403;
    public static final int FILE_NOT_FOUND = 404;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_MODIFIED = 304;

}
