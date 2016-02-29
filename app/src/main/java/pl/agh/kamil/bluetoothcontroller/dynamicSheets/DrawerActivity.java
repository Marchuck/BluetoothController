package pl.agh.kamil.bluetoothcontroller.dynamicSheets;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.agh.kamil.bluetoothcontroller.R;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments.LaunchBluetoothFragment;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments.LeftItemsFragment;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.utils.CustomDrawerListener;
import pl.lukmarr.blueduff.BlueDuff;
import pl.lukmarr.blueduff.BlueInterfaces;

public class DrawerActivity extends AppCompatActivity {

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    public BlueDuff blueDuff;
    View progressIndicator;

    private LeftItemsFragment leftItemsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        blueDuff = new BlueDuff();
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
                .replace(R.id.rightContent, LaunchBluetoothFragment.newInstance())
                .commitAllowingStateLoss();

    }

    private void setupDrawer() {
        drawerLayout.setDrawerElevation(10f);
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
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
        blueDuff.connectToDevice(device, new BlueInterfaces.OnConnectedCallback() {
            @Override
            public void onConnected() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
                        setupLeftDrawer();
                        drawerLayout.openDrawer(Gravity.LEFT);
                        progressIndicator.setVisibility(View.GONE);
                    }
                });
                blueDuff.writeData("ADAŚ WAŁEK".getBytes());
            }
        }, new BlueInterfaces.DataReceivedCallback() {
            @Override
            public void onDataReceived(byte[] bytes) {
                leftItemsFragment.bindWithData(bytes);
            }
        });

    }

    private void setupLeftDrawer() {
        if (leftItemsFragment == null) {
            leftItemsFragment = LeftItemsFragment.newInstance();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.leftContent, leftItemsFragment)
                .commitAllowingStateLoss();
    }
}
