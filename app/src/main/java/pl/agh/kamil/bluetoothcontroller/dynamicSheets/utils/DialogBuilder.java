package pl.agh.kamil.bluetoothcontroller.dynamicSheets.utils;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.agh.kamil.bluetoothcontroller.R;

/**
 * Created by Łukasz Marczak
 *
 * @since 29.02.16
 */
public class DialogBuilder {

    public interface SelectItemCallback {
        void onClicked(BluetoothDevice device);
    }

    BTAdapter adapter;
    BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();

    public DialogBuilder(@NonNull Activity activity, @NonNull final SelectItemCallback callback) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.bonded_devices_layout);
        dialog.setTitle("Choose device");
        if (!bt.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, 0x01);
        }

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new BTAdapter(activity, new SelectItemCallback() {
            @Override
            public void onClicked(BluetoothDevice device) {
                callback.onClicked(device);
                dialog.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);

        //setup swipe to refresh
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)
                dialog.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new android.os.Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refresh();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 200);
            }
        });
        //setup swipeable colors
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_blue_light, android.R.color.holo_orange_light);

        dialog.show();
        new android.os.Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter.dataSet.size() == 0) {
//                    showNoItemsMessage(true);
                    Snackbar.make(swipeRefreshLayout, "Swipe vertically to refresh list",
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        }, 500);
    }

    public class BTAdapter extends RecyclerView.Adapter<VH> {

        List<BluetoothDevice> dataSet = new ArrayList<>();
        public SelectItemCallback callback;
        Context ctx;

        public BTAdapter(Context ctx, SelectItemCallback callback) {
            this.callback = callback;
            this.ctx = ctx;
            refresh();
        }

        public void refresh() {
            dataSet.clear();
            dataSet.addAll(bt.getBondedDevices());
            notifyDataSetChanged();
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bonded_devices_item, null, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(final VH holder, final int position) {
            holder.textView.setText(dataSet.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.textView.setTextColor(ctx.getResources().getColor(R.color.colorAccent));
                    callback.onClicked(dataSet.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }

    public static class VH extends RecyclerView.ViewHolder {

        public TextView textView;

        public VH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.bonded_device_name);
        }
    }
}
