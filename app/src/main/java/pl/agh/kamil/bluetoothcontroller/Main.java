package pl.agh.kamil.bluetoothcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by Kamil on 2016-01-06.
 */

@SuppressLint("ClickableViewAccessibility")
public class Main extends Activity implements SensorEventListener {
    // private static String status_text = "__";

    // private boolean LIGHT = false;
    private static final int GET_DEVICE = 1;
    // status aplikacji
    private static final int DISCONNECTED = 1;
    private static final int CONNECTED = 2;
    private static final int CONNECTION_ERROR = 3;
    private static int STATE = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice;
    private BluetoothSocket mSocket;
    private static final UUID uuid = UUID
            .fromString("00001101-0000-1000-8000-00805f9b34fb");
    final Handler handler = new Handler();
    private ConnectedThread mConnectedThread = null;
    // sensory - nieu¿ywany
    SensorManager sensorManager = null;
    // komunikat stanu
    private TextView mTextState = null;

    Intent intent = null;
    private boolean ConnectionStarted = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Core.setCurrentContext(this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth jest niedostêpny!",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth jest wyłączony!", Toast.LENGTH_LONG)
                    .show();
            finish();
            return;
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (pairedDevices.size() == 0) {
            Toast.makeText(this, "Brak sparowanych urządzeñ!",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        intent = new Intent(this, SelectDevice.class);

        mTextState = (TextView) findViewById(R.id.status);
        setButtons();
    }

    private void setButtons() {

        final Button Start = (Button) findViewById(R.id.buttonStart);

        Start.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mConnectedThread == null) {
                    startActivityForResult(intent, GET_DEVICE);
                    Start.setText("Rozpocznij");
                } else {
                    Intent steruj = new Intent(getApplicationContext(),
                            ListMenu.class);
                    steruj.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    if (!ConnectionStarted) {
                        startActivity(steruj);
                        ConnectionStarted = true;
                    }
                }
            }
        });

    }

    public class ConnectedThread extends Thread {

        private InputStream mmInStream = null;
        private OutputStream mmOutStream = null;

        public ConnectedThread(BluetoothSocket socket) {
            try {
                mmInStream = socket.getInputStream();
                mmOutStream = socket.getOutputStream();
            } catch (IOException e) {
                handler.post(new Runnable() {
                    public void run() {
                        btDisconnect();
                        changeState(CONNECTION_ERROR);
                        mConnectedThread = null;
                    }
                });
            }
        }

        public void run() {

            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    if (bytes > 0) {
                        byte[] newbuffer = new byte[bytes];

                        for (int i = 0; i < bytes; i++)
                            newbuffer[i] = buffer[i];

                        final String data = new String(newbuffer, "US-ASCII");
                        handler.post(new Runnable() {

                            public void run() {
                                // przychodz¹ce dane lec¹ jako Toast na
                                // aktualnym Activity
                                // ((Activity)
                                // Core.getCurrentContext()).runOnUiThread(new
                                // Runnable() {
                                // public void run() {
                                // Toast.makeText(Core.getCurrentContext(),
                                // "Dane przychodz¹ce: "+data,
                                // Toast.LENGTH_LONG).show();
                                // }
                                // });
                                final TextView textView = (TextView) ((Activity) Core
                                        .getCurrentContext())
                                        .findViewById(R.id.status_text);
                                textView.setText(data);
                                final ImageView view = (ImageView) ((Activity) Core
                                        .getCurrentContext())
                                        .findViewById(R.id.status_image);
                                if (data.length() != 0) {
                                    if (data.equals("a")) {
                                        Core.setLastLight('a');
                                        view.setImageResource(R.drawable.onn);
                                    } else if (data.equals("b")) {
                                        Core.setLastLight('b');
                                        view.setImageResource(R.drawable.off);
                                    } else if (data.equals("c")) {
                                        Core.setLastSound('c');
                                        view.setImageResource(android.R.drawable.ic_delete);
                                    } else if (data.equals("d")) {
                                        Core.setLastSound('d');
                                        view.setImageResource(R.drawable.a_sound_down);
                                    } else if (data.equals("e")) {
                                        Core.setLastGate('e');
                                        view.setImageResource(R.drawable.a_sound_down);
                                    } else if (data.equals("f")) {
                                        Core.setLastGate('f');
                                        view.setImageResource(android.R.drawable.ic_delete);
                                    } else if (data.equals("g")) {
                                        Core.setLastBlinds('g');
                                        view.setImageResource(R.drawable.a_sound_down);
                                    } else if (data.equals("h")) {
                                        Core.setLastBlinds('h');
                                        view.setImageResource(android.R.drawable.ic_delete);
                                    }
                                }
                            }
                        });
                    }

                } catch (IOException e) {
                    Log.e("BT", "watcher", e);
                    break;
                }
            }
        }

        void write(int one) {
            if (STATE != CONNECTED)
                return;

            try {
                mmOutStream.write(one);
            } catch (IOException e) {
                handler.post(new Runnable() {
                    public void run() {
                        btDisconnect();
                        changeState(CONNECTION_ERROR);
                        mConnectedThread = null;
                    }
                });
            }
        }

        void write(String str) {
            if (STATE != CONNECTED)
                return;

            try {
                mmOutStream.write(str.getBytes());
            } catch (IOException e) {

                synchronized (Main.this) {
                    btDisconnect();
                    changeState(CONNECTION_ERROR);
                    mConnectedThread = null;
                }
            }

        }

        public void disconnect() {
            btDisconnect();
        }
    }

    public void onSensorChanged(SensorEvent event) {
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void btConnect() {
        if (mDevice == null)
            return;
        try {
            mSocket = mDevice.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect();

        } catch (IOException e) {
            Log.e("BT", "point1", e);

            btDisconnect();
            changeState(CONNECTION_ERROR);
            return;
        }

        mConnectedThread = new ConnectedThread(mSocket);
        mConnectedThread.start();

        changeState(CONNECTED);
    }

    private void btConnect(String address) {
        mDevice = mBluetoothAdapter.getRemoteDevice(address);
        btConnect();
    }

    private void btDisconnect() {
        if (mSocket == null)
            return;

        if (mConnectedThread != null) {
            mConnectedThread.stop();
            mConnectedThread = null;
        }

        try {
            mSocket.close();
        } catch (IOException e) {
            Log.e("BT", "point3", e);
        }

        mSocket = null;

        changeState(DISCONNECTED);
    }

    private void changeState(int iState) {

        STATE = iState;

        switch (iState) {
            case CONNECTED:
                mTextState.setTextColor(Color.BLUE);
                mTextState.setText("Połączony z " + mDevice.getName());
                Core.setCommunication(mConnectedThread);
                break;
            case DISCONNECTED:
                mTextState.setTextColor(Color.BLACK);
                mTextState.setText("Rozłączony");
                break;
            case CONNECTION_ERROR:
                mTextState.setTextColor(Color.RED);
                mTextState.setText("Błąd połączenia");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.findItem(R.id.connect).setVisible(STATE != CONNECTED);
        menu.findItem(R.id.disconnect).setVisible(STATE == CONNECTED);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect:
                Intent in = new Intent(this, SelectDevice.class);
                startActivityForResult(in, GET_DEVICE);
                return true;
            case R.id.disconnect:
                btDisconnect();
                mDevice = null;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case GET_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    btConnect(data.getStringExtra(SelectDevice.DEVICE_ADDRESS));
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // mTextState = (TextView) findViewById(R.id.status);
                    mTextState.setText("xDD");
                }
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        changeState(STATE);
    }
}