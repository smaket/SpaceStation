package iss.chase.com.ispacestation.presenter.api;

/**
 * Interface for request method and response observer
 * @author Bikash
 *
 */
public interface IHttpConnection {
	interface IResponseObserver
	{

		enum RequestTypeEnum {
            GET_CITY_WEATHER_CONDITIONS,
			GET_PASSTIME,
			POST_CONTENT
        }

	       
	}


}
