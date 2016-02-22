package pl.agh.kamil.bluetoothcontroller;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
/**
 * Created by Kamil on 2016-01-06.
 */
public class GateActivity extends Activity {
    private Main.ConnectedThread mConnectedThread = null;
    //	private static TextView status;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gate_activity);
        mConnectedThread = Core.getCommunication();
        Core.setCurrentContext(this);
        char ch = Core.getLastBlinds();
        ImageView view = (ImageView)findViewById(R.id.status_image);
        if (ch == 'e')
            view.setImageResource(R.drawable.onn);
        else if(ch=='f')
            view.setImageResource(R.drawable.off);


        Button btn = (Button) findViewById(R.id.light_button_on);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mConnectedThread != null)
                    mConnectedThread.write('e');
            }
        });
        Button btn2 = (Button) findViewById(R.id.light_button_off);
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mConnectedThread != null)
                    mConnectedThread.write('f');
            }
        });

    }

}