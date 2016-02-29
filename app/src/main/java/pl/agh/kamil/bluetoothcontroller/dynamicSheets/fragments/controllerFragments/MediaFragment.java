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
public class MediaFragment extends Fragment {
    //	private static TextView status;

    public MediaFragment() {
    }

    public static MediaFragment newInstance(){
        return new MediaFragment();
    }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.media_activity, container, false);

        ImageView imview = (ImageView) v .findViewById(R.id.status_image);
         char ch = Core.instance.getLastSound();
         if (ch == 'c')
             imview.setImageResource(R.drawable.onn);
         else if (ch == 'd')
             imview.setImageResource(R.drawable.off);
         Button btn = (Button) v.findViewById(R.id.light_button_on);
         btn.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 if (Core.instance.blueDuff != null)
                     Core.instance.blueDuff.writeData(new byte[]{'c'});
             }
         });
         Button btn2 = (Button) v.findViewById(R.id.light_button_off);
         btn2.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 if (Core.instance.blueDuff != null)
                     Core.instance.blueDuff.writeData(new byte[]{'d'});
             }
         });
        return v ;
    }
}