package pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments.controllerFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import pl.agh.kamil.bluetoothcontroller.Core;
import pl.agh.kamil.bluetoothcontroller.R;

/**
 * Created by Kamil on 2016-01-06.
 */
public class EditTextFragment extends Fragment {
    //	private static TextView status;
    public static final String TAG = EditTextFragment.class.getSimpleName();

    public EditTextFragment() {
    }

    public static EditTextFragment newInstance() {
        return new EditTextFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.send_data_fragment, container, false);
        final EditText editText = (EditText) v.findViewById(R.id.editText);
        Button btn = (Button) v.findViewById(R.id.SEND);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) return;
                if (Core.instance.blueDuff == null) return;
                Log.d(TAG, "onClick: ");
                Core.instance.blueDuff.writeData(editText.getText().toString().getBytes());
            }
        });
        return v;
    }
}