package healthsystemapp.com.example.tcc.healthsystemapp.home.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

import butterknife.BindView;
import butterknife.ButterKnife;
import healthsystemapp.com.example.tcc.healthsystemapp.R;

public class TimeLineViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.time_marker)
    TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        mTimelineView.initLine(viewType);
    }

    public TextView getDateTextView() {
        return dateTextView;
    }

    public void setDateTextView(TextView dateTextView) {
        this.dateTextView = dateTextView;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }

    public TimelineView getmTimelineView() {
        return mTimelineView;
    }

    public void setmTimelineView(TimelineView mTimelineView) {
        this.mTimelineView = mTimelineView;
    }
}