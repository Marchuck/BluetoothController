package pl.agh.kamil.bluetoothcontroller.dynamicSheets;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.agh.kamil.bluetoothcontroller.Core;
import pl.agh.kamil.bluetoothcontroller.R;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments.LaunchBluetoothFragment;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments.LeftItemsFragment;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments.controllerFragments.EditTextFragment;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments.controllerFragments.GateFragment;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments.controllerFragments.Lights1Fragment;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments.controllerFragments.LightsFragment;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments.controllerFragments.MediaFragment;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.utils.CustomDrawerListener;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.utils.ItemToControl;
import pl.lukmarr.blueduff.BlueDuff;
import pl.lukmarr.blueduff.BlueInterfaces;

public class DrawerActivity extends AppCompatActivity {
    public static final String TAG = DrawerActivity.class.getSimpleName();
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    public BlueDuff blueDuff;

    @Bind(R.id.progressBar)
    ProgressBar progressIndicator;

    private LeftItemsFragment leftItemsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        blueDuff = new BlueDuff();
        Core.instance.blueDuff = blueDuff;
        initAllFragments();
        setupDrawer();
        setTitle("Smart house");
    }

    private void initAllFragments() {
        //center fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, LaunchBluetoothFragment.newInstance())
                .commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rightContent, EditTextFragment.newInstance())
                .commitAllowingStateLoss();

    }

    private void setupDrawer() {
        drawerLayout.setDrawerElevation(10f);
        drawerLayout.setDrawerListener(new CustomDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                if (drawerView.getId() == R.id.leftContent) {

                } else {

                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (drawerView.getId() == R.id.leftContent) {

                } else {

                }
            }
        });
    }

    public void connectToDevice(BluetoothDevice device) {
        progressIndicator.setVisibility(View.VISIBLE);
        blueDuff.connectToDevice(device, new BlueInterfaces.OnConnectedCallback() {
            @Override
            public void onConnected() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressIndicator.setVisibility(View.GONE);
                        setupLeftDrawer();
                        drawerLayout.openDrawer(Gravity.LEFT);
                    }
                });
                blueDuff.writeData(new byte[]{'?'});
            }
        }, new BlueInterfaces.DataReceivedCallback() {
            @Override
            public void onDataReceived(byte[] bytes) {
                leftItemsFragment.bindWithData(bytes);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (blueDuff != null) {
            blueDuff.closeStreams(new BlueInterfaces.OnSocketKilledCallback() {
                @Override
                public void onSocketKilled() {
                    Toast.makeText(DrawerActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                }
            });
        }
        super.onBackPressed();
    }

    private void setupLeftDrawer() {
        if (leftItemsFragment == null) {
            leftItemsFragment = LeftItemsFragment.newInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.leftContent, leftItemsFragment)
                .commitAllowingStateLoss();
    }

    public void switchToFragment(ItemToControl itemToControl) {
        Log.d(TAG, "switchToFragment: ");
        android.support.v4.app.Fragment fr;
        String name = itemToControl.name;
        if (name.equals("kuchnia")) {
            fr = LightsFragment.newInstance();
        } else if (name.equals("dupa")) {
            fr = Lights1Fragment.newInstance();
        } else if (name.equals("media")) {
            fr = MediaFragment.newInstance();
        } else if (name.equals("water")) {
            fr = GateFragment.newInstance();
        } else {
            fr = Lights1Fragment.newInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fr).commitAllowingStateLoss();
        drawerLayout.closeDrawer(Gravity.LEFT);
    }
}
