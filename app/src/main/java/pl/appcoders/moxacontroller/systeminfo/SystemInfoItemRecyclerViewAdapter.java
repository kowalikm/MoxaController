package pl.appcoders.moxacontroller.systeminfo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class SystemInfoItemRecyclerViewAdapter extends RecyclerView.Adapter<SystemInfoItemRecyclerViewAdapter.ViewHolder> {

    private final SystemInfoContainer systemInfoContainer;

    SystemInfoItemRecyclerViewAdapter(SystemInfoContainer systemInfoContainer) {
        this.systemInfoContainer = systemInfoContainer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final List<SystemInfoItem> systemInfoItemList = systemInfoContainer.getSystemInfoItemList();
        holder.mIdView.setText(systemInfoItemList.get(position).getId());
        holder.mDataView.setText(systemInfoItemList.get(position).getData());
    }

    @Override
    public int getItemCount() {
        return systemInfoContainer.getSystemInfoItemList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mIdView;
        final TextView mDataView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(android.R.id.text1);
            mDataView = view.findViewById(android.R.id.text2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDataView.getText() + "'";
        }
    }
}
