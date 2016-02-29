package pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments.controllerFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import pl.agh.kamil.bluetoothcontroller.Core;
import pl.agh.kamil.bluetoothcontroller.R;

/**
 * Created by Kamil on 2016-01-06.
 */
public class GateFragment extends Fragment {
    //	private static TextView status;

    public GateFragment() {
    }

    public static GateFragment newInstance(){
        return new GateFragment();
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.gate_activity, container, false);

        ImageView imview = (ImageView) v .findViewById(R.id.status_image);
         char ch = Core.instance.getLastBlinds();
         if (ch == 'e')
             imview.setImageResource(R.drawable.onn);
         else if (ch == 'f')
             imview.setImageResource(R.drawable.off);
         Button btn = (Button) v.findViewById(R.id.light_button_on);
         btn.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 if (Core.instance.blueDuff != null)
                     Core.instance.blueDuff.writeData(new byte[]{'e'});
             }
         });
         Button btn2 = (Button) v.findViewById(R.id.light_button_off);
         btn2.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 if (Core.instance.blueDuff != null)
                     Core.instance.blueDuff.writeData(new byte[]{'f'});
             }
         });
        return v ;
    }
}