package iss.chase.com.ispacestation.view.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import iss.chase.com.ispacestation.R;
import iss.chase.com.ispacestation.model.PassTime;
import iss.chase.com.ispacestation.utils.ISSUtils;


/**
 * Created by Bikash on 3/9/2018.

 */

public class ISSAdapter extends RecyclerView.Adapter<ISSAdapter.ISSViewHolder> {

    private static final String TAG = ISSAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<PassTime> mAdapterItemList = new ArrayList<PassTime>();
    private View mView;

    public ISSAdapter(Activity mContext, ArrayList<PassTime>aAdapterItemList) {
        super();
        this.mContext = mContext;
        this.mAdapterItemList = aAdapterItemList;
    }

    @Override
    public ISSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.passtime_rcylr_item, parent, false);
        return new ISSViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ISSViewHolder holder, int position) {
        Log.d(TAG , "onBindViewHolder" );
        if (holder!=null  && holder instanceof ISSViewHolder ) {
            //Data Received from the server
            String timeString = ISSUtils.getInstance().convertSec2HrMin(mAdapterItemList.get(position).getDuration());
            String date = ISSUtils.getInstance().convertLong2Date(mAdapterItemList.get(position).getRisetime());

            holder.duration.setText(timeString);
            holder.riseTime.setText(date);

        }

    }
    @Override
    public void onViewAttachedToWindow(ISSViewHolder holder) {
        super.onViewAttachedToWindow(holder);

    }
    @Override
    public int getItemCount() {

       return mAdapterItemList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public void updateAdapterList(ArrayList<PassTime> aRecyclerItemList) {
//        mAdapterItemList.clear();
        mAdapterItemList = aRecyclerItemList;
        notifyDataSetChanged();


    }

    /**
     * View holder for adaptor.
     */
    public class ISSViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.duration)
        TextView duration;
        @BindView(R.id.risetime)
        TextView riseTime;
        @BindView(R.id.item_layout)
        RelativeLayout mItemLayout;

        public ISSViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
