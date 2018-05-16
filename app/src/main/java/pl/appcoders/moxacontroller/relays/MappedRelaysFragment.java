package pl.appcoders.moxacontroller.relays;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.main.OnRefreshActionListener;
import pl.appcoders.moxacontroller.main.OnRestActionListener;

/**
 * Created by mkowalik on 16.05.18.
 */

public class MappedRelaysFragment extends Fragment implements OnRefreshActionListener {
    private OnListFragmentInteractionListener listener;
    private MappedRelaysViewModel mappedRelaysViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Mapped relays");
        mappedRelaysViewModel = ViewModelProviders.of(this)
                .get(MappedRelaysViewModel.class);
        registerRestActionListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mappedrelay_list, container, false);

        Context context = view.getContext();

        FloatingActionButton floatingActionButton = view.findViewById(R.id.mapRelayFab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MapRelayActivity.class));
            }
        });

        final RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mappedRelaysViewModel.getMappedRelayItemList().observe(this, new Observer<List<MappedRelayItem>>() {
            @Override
            public void onChanged(@Nullable List<MappedRelayItem> mappedRelayItems) {
                recyclerView.setAdapter(new MappedRelaysRecyclerViewAdapter(mappedRelayItems, listener));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnListFragmentInteractionListener) {
            listener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onStop() {
        mappedRelaysViewModel.unregisterOnRestActionListener();
        super.onStop();
    }

    @Override
    public void refreshAction() {
        mappedRelaysViewModel.refreshRestData();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(MappedRelayItem item);
    }

    private void registerRestActionListener() {
        Activity activity = getActivity();
        if(activity instanceof OnRestActionListener) {
            mappedRelaysViewModel.registerOnRestActionListener((OnRestActionListener)getActivity());
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnRestActionListener!");
        }
    }
}
