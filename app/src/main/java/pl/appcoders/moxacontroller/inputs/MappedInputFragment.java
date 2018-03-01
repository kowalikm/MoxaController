package pl.appcoders.moxacontroller.inputs;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.appcoders.moxacontroller.R;
import pl.appcoders.moxacontroller.main.OnRefreshActionListener;

public class MappedInputFragment extends Fragment implements OnRefreshActionListener {

    private OnListFragmentInteractionListener listener;
    private MappedInputController mappedInputController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Mapped inputs");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mappedinput_list, container, false);

        mappedInputController = new MappedInputController(view.getContext());

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MappedInputRecyclerViewAdapter(mappedInputController.getMappedInputItems(), listener));
        }

        mappedInputController.refreshMappedInputs();

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

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(MappedInputItem item);
    }
}
