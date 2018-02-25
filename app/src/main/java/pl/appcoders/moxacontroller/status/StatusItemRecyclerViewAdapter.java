package pl.appcoders.moxacontroller.status;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.appcoders.moxacontroller.R;

public class StatusItemRecyclerViewAdapter extends RecyclerView.Adapter<StatusItemRecyclerViewAdapter.ViewHolder> {

    private final List<StatusItem> mValues;

    public StatusItemRecyclerViewAdapter(Context context) {
        mValues = new ArrayList<>();
        mValues.add(new StatusItem(context.getString(R.string.connection_status), "Online"));
        mValues.add(new StatusItem(context.getString(R.string.model_name), "ioLogik E1214"));
        mValues.add(new StatusItem(context.getString(R.string.device_uptime), "5d 12h 31m 17s"));
        mValues.add(new StatusItem(context.getString(R.string.firmware_version), "17.43.221b"));
        mValues.add(new StatusItem(context.getString(R.string.mac_address), "01:23:45:67:89:ab"));
        mValues.add(new StatusItem(context.getString(R.string.ip_address), "192.168.10.113"));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mDataView.setText(mValues.get(position).getData());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mDataView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(android.R.id.text1);
            mDataView = (TextView) view.findViewById(android.R.id.text2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDataView.getText() + "'";
        }
    }
}
