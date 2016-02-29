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
public class Lights1Fragment extends Fragment {
    //	private static TextView status;

    public Lights1Fragment() {
    }

    public static Lights1Fragment newInstance(){
        return new Lights1Fragment();
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.activity_lights, container, false);

        ImageView imview = (ImageView) v .findViewById(R.id.status_image);
         char ch = Core.instance.getLastLight();
         if (ch == 'g')
             imview.setImageResource(R.drawable.onn);
         else if (ch == 'h')
             imview.setImageResource(R.drawable.off);
         Button btn = (Button) v.findViewById(R.id.light_button_on);
         btn.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 if (Core.instance.blueDuff != null)
                     Core.instance.blueDuff.writeData(new byte[]{'g'});
             }
         });
         Button btn2 = (Button) v.findViewById(R.id.light_button_off);
         btn2.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 if (Core.instance.blueDuff != null)
                     Core.instance.blueDuff.writeData(new byte[]{'h'});
             }
         });
        return v ;
    }
}