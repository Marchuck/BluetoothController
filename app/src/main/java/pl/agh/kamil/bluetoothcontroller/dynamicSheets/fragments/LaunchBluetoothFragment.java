package pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments;


import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.agh.kamil.bluetoothcontroller.R;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.DrawerActivity;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.utils.DialogBuilder;

/**
 * A fragment with a Google +1 button.
 * Use the {@link LaunchBluetoothFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaunchBluetoothFragment extends Fragment {

    private DrawerActivity getDrawerActivity() {
        return ((DrawerActivity) getActivity());
    }

    private boolean notConnected = true;


    @OnClick(R.id.fab)
    public void onFabClick() {
        if (notConnected) {
//            notConnected = false;
            new DialogBuilder(getDrawerActivity(), new DialogBuilder.SelectItemCallback() {
                @Override
                public void onClicked(BluetoothDevice device) {
                     getDrawerActivity().connectToDevice(device);
                }
            });
        } else {
            if (getView() != null)
                Snackbar.make(getView(), "Cannot connect now", Snackbar.LENGTH_SHORT).show();
        }
    }

    public LaunchBluetoothFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LaunchBluetoothFragment newInstance() {
        LaunchBluetoothFragment fragment = new LaunchBluetoothFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lauch_bluetooth, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


}
