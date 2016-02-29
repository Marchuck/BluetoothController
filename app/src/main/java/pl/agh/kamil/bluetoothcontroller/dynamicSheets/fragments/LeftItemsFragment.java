package pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;

import pl.agh.kamil.bluetoothcontroller.R;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.DrawerActivity;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.utils.ItemToControl;

public class LeftItemsFragment extends Fragment {

    public DrawerActivity asDrawerActivity(){
        return ((DrawerActivity)getActivity());
    }

    public void bindWithData(final byte[] data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (controllerAdapter != null) {
                    controllerAdapter.bindWithData(data);
                }
            }
        });
    }

    ControllerAdapter controllerAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LeftItemsFragment() {
    }

    public static LeftItemsFragment newInstance() {
        LeftItemsFragment fragment = new LeftItemsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            controllerAdapter = new ControllerAdapter(Collections.<ItemToControl>emptyList(),
                    new ControllerAdapter.SendValueCallback() {
                        @Override
                        public void sendState(ItemToControl itemToControl) {
                            //Toast.makeText(LeftItemsFragment.this.getActivity(), "clicked " + item, Toast.LENGTH_SHORT).show();
                            asDrawerActivity().switchToFragment(itemToControl);
                        }
                    });
            recyclerView.setAdapter(controllerAdapter);
        }
        return view;
    }
}
