package pl.appcoders.moxacontroller.status;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StatusItemRecyclerViewAdapter extends RecyclerView.Adapter<StatusItemRecyclerViewAdapter.ViewHolder> {

    private final StatusContainer statusContainer;

    public StatusItemRecyclerViewAdapter(StatusContainer statusContainer) {
        this.statusContainer = statusContainer;
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
