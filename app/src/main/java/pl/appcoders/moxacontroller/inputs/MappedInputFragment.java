package pl.appcoders.moxacontroller.inputs;

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

public class MappedInputFragment extends Fragment implements OnRefreshActionListener {

    private OnListFragmentInteractionListener listener;
    private MappedInputViewModel mappedInputViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Mapped inputs");
        mappedInputViewModel = ViewModelProviders.of(this)
                .get(MappedInputViewModel.class);
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
        mappedInputViewModel.getMappedInputItemList().observe(this, new Observer<List<MappedInputItem>>() {
            @Override
            public void onChanged(@Nullable List<MappedInputItem> mappedInputItems) {
                recyclerView.setAdapter(new MappedInputRecyclerViewAdapter(mappedInputItems, listener));
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
    public void refreshAction() {
        mappedInputViewModel.refreshRestData();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(MappedInputItem item);
    }
}
