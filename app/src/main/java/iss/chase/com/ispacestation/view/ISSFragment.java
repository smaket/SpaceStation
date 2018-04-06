package iss.chase.com.ispacestation.view;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import iss.chase.com.ispacestation.R;
import iss.chase.com.ispacestation.presenter.ISSPresenterImp;
import iss.chase.com.ispacestation.presenter.gps.GPSManager;
import iss.chase.com.ispacestation.presenter.gps.GPSService;
import iss.chase.com.ispacestation.model.PassTime;
import iss.chase.com.ispacestation.model.SpaceStationData;
import iss.chase.com.ispacestation.view.adapter.ISSAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import iss.chase.com.ispacestation.view.widget.ISSLinearLayoutManager;
import iss.chase.com.ispacestation.utils.ISSUtils;

/**
 * Created by Bikash on 3/9/2018.
 */

public class ISSFragment extends Fragment implements IISSView , ILocationChanged{


    private ISSAdapter mIssAdapter;
    private ArrayList<PassTime> mRecyclerItemList = new ArrayList<PassTime>();
    GPSManager mGpsTracker;
    ISSPresenterImp preseterimpl;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);

        preseterimpl = new ISSPresenterImp(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        GPSManager.registerListener(this);
        GPSService.registerListner(this);
    }

    public static final String EXTRA_ISS_DURATION = "EXTRA_ISS_DURATION";
    public static final String EXTRA_ISS_RISETIME = "EXTRA_ISS_RISETIME";

    @BindView(R.id.txtview_welcome_name)
    TextView welcomename ;
    @BindView(R.id.latlong)
    TextView latlong;
    @BindView(R.id.datetime)
    TextView datetime;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerViewScroll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.passtime_view, container, false);
        ButterKnife.bind(this,lView);
        welcomename.setText("Welcom to Space Station!! ");
        prepareRecyclerView();
        return lView;
    }

    /**
     * Preparing to load the view
     */
    private void prepareRecyclerView() {
        mIssAdapter = new ISSAdapter(getActivity(), mRecyclerItemList);

        ISSLinearLayoutManager wmLayout = new ISSLinearLayoutManager(getContext());
        mRecyclerViewScroll.setLayoutManager(wmLayout);

        mRecyclerViewScroll.setAdapter(mIssAdapter);
        mRecyclerViewScroll.setNestedScrollingEnabled(true);
        mRecyclerViewScroll.setHasFixedSize(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void notifyDataChange(final ArrayList<SpaceStationData> spaceStationData1) {

        /**
         * Handler to post the data to UI thread
         */
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                SpaceStationData spaceStationData = spaceStationData1.get(0);
                latlong.setText(spaceStationData.getRequest().getLatitude() + "," + spaceStationData.getRequest().getLongitude() + "Alt:" + spaceStationData.getRequest().getAltitude());
                datetime.setText(ISSUtils.getInstance().convertLong2Date(spaceStationData.getRequest().getDatetime()));
                if (spaceStationData.getResponse() != null && spaceStationData.getResponse() .length > 0) {
                    mRecyclerItemList.clear();

                    for (PassTime aPassTime : spaceStationData.getResponse()) {
                        mRecyclerItemList.add(aPassTime);
                    }
                    if (mRecyclerItemList.size() > 0) {
                        mIssAdapter.notifyDataSetChanged();

                    }
                }
            }
        });

    }

    /**
     * Call back when location changed
     * @param location current location
     */
    @Override
    public void onLocationChanged(final Location location) {
        double lat = (double) (location.getLatitude());
        double lng = (double) (location.getLongitude());
        preseterimpl.getPassTime(location ,preseterimpl);

    }
}
