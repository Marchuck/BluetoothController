package pl.agh.kamil.bluetoothcontroller;

import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.bluetooth.BluetoothDevice;
/**
 * Created by Kamil on 2016-01-06.
 */
public class SelectDevice extends Activity {

    public static String DEVICE_ADDRESS = "device_address";
    private ArrayAdapter<String> mDevices;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_device);
        Core.setCurrentContext(this);

        setResult(Activity.RESULT_CANCELED);

        mDevices = new ArrayAdapter<String>(this, R.layout.device_style);
        ListView pairedListView = (ListView) findViewById(R.id.lvDevices);
        pairedListView.setAdapter(mDevices);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        for(BluetoothDevice device : pairedDevices){
            mDevices.add(device.getName() + "\n" + device.getAddress());
        }
    }

    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            Intent intent = new Intent();
            intent.putExtra(DEVICE_ADDRESS, address);

            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };

}