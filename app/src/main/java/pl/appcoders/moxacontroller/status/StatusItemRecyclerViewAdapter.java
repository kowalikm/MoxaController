package pl.appcoders.moxacontroller.status;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StatusItemRecyclerViewAdapter extends RecyclerView.Adapter<StatusItemRecyclerViewAdapter.ViewHolder> {

    private final StatusContainer statusContainer;

    public StatusItemRecyclerViewAdapter(Context context) {
        statusContainer = new StatusContainer(context);

        statusContainer.setModelName("ioLogik E1214");
        statusContainer.setDeviceUptime("5d 12h 31m 17s");
        statusContainer.setFirmwareVersion("17.43.221b");
        statusContainer.setMacAddress("01:23:45:67:89:ab");
        statusContainer.setIpAddress("192.168.10.113");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final List<StatusItem> statusItemList = statusContainer.getStatusItemList();
        holder.mIdView.setText(statusItemList.get(position).getId());
        holder.mDataView.setText(statusItemList.get(position).getData());
    }

    @Override
    public int getItemCount() {
        return statusContainer.getStatusItemList().size();
    }

    public void refreshStatus() {
        statusContainer.setConnectionStatus(true);
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
