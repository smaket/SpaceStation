package iss.chase.com.ispacestation;

import android.content.Context;
import android.location.Location;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import iss.chase.com.ispacestation.presenter.IISSPresenter;
import iss.chase.com.ispacestation.presenter.ISSPresenterImp;
import iss.chase.com.ispacestation.presenter.api.IHttpConnection;
import iss.chase.com.ispacestation.presenter.api.IResponseCallback;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ISSInstrumentedTest {
    @Mock
    Location location;
    @Mock
    IISSPresenter iissPresenter;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();




    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("iss.chase.com.ispacestation", appContext.getPackageName());
    }
    @Test
    public void getPassTimeTest() {
        iissPresenter.getPassTime(location, new IResponseCallback() {
            @Override
            public void responseReceived(int status, String body, IHttpConnection.IResponseObserver.RequestTypeEnum aRespType, Object requestParams) {
                assertEquals(200, status);
                assertNotNull(body);
                assertEquals(IHttpConnection.IResponseObserver.RequestTypeEnum.GET_PASSTIME , aRespType);


            }
        });
    }

}
