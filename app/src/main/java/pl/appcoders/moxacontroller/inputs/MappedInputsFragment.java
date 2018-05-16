package pl.appcoders.moxacontroller.inputs;

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

public class MappedInputsFragment extends Fragment implements OnRefreshActionListener {

    private OnListFragmentInteractionListener listener;
    private MappedInputsViewModel mappedInputsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Mapped inputs");
        mappedInputsViewModel = ViewModelProviders.of(this)
                .get(MappedInputsViewModel.class);
        registerRestActionListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mappedinput_list, container, false);

        Context context = view.getContext();

        FloatingActionButton floatingActionButton = view.findViewById(R.id.mapInputFab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MapInputActivity.class));
            }
        });

        final RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mappedInputsViewModel.getMappedInputItemList().observe(this, new Observer<List<MappedInputItem>>() {
            @Override
            public void onChanged(@Nullable List<MappedInputItem> mappedInputItems) {
                recyclerView.setAdapter(new MappedInputsRecyclerViewAdapter(mappedInputItems, listener));
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
        mappedInputsViewModel.unregisterOnRestActionListener();
        super.onStop();
    }

    @Override
    public void refreshAction() {
        mappedInputsViewModel.refreshRestData();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(MappedInputItem item);
    }

    private void registerRestActionListener() {
        Activity activity = getActivity();
        if(activity instanceof OnRestActionListener) {
            mappedInputsViewModel.registerOnRestActionListener((OnRestActionListener)getActivity());
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnRestActionListener!");
        }
    }
}
