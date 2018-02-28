package pl.appcoders.moxacontroller.inputs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.appcoders.moxacontroller.inputs.MappedInputFragment.OnListFragmentInteractionListener;

public class MappedInputRecyclerViewAdapter extends RecyclerView.Adapter<MappedInputRecyclerViewAdapter.ViewHolder> {
    private final OnListFragmentInteractionListener listener;
    private List<MappedInputItem> mappedInputItemList;

    public MappedInputRecyclerViewAdapter(List<MappedInputItem> mappedInputItemList, OnListFragmentInteractionListener listener) {
        this.mappedInputItemList = mappedInputItemList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(mappedInputItemList.get(position).getMappedName());
        holder.mContentView.setText(mappedInputItemList.get(position).getCounterValue());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mappedInputItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public MappedInputItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(android.R.id.text1);
            mContentView = (TextView) view.findViewById(android.R.id.text2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}