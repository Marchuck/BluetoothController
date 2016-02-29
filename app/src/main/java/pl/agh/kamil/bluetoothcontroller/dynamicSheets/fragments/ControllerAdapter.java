package pl.agh.kamil.bluetoothcontroller.dynamicSheets.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.agh.kamil.bluetoothcontroller.R;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.utils.ItemToControl;
import pl.agh.kamil.bluetoothcontroller.dynamicSheets.utils.ProtocolLayer;

public class ControllerAdapter extends RecyclerView.Adapter<ControllerAdapter.ViewHolder> {

    public interface SendValueCallback {
        void sendState(ItemToControl itemToControl);
    }

    SendValueCallback sendValueCallback;
    List<ItemToControl> dataSet = new ArrayList<>();

    public ControllerAdapter(List<ItemToControl> items, @NonNull SendValueCallback sendValueCallback) {
        dataSet.addAll(items);
        this.sendValueCallback = sendValueCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.controlled_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ItemToControl item = dataSet.get(position);

        holder.name.setText(dataSet.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendValueCallback.sendState(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void bindWithData(byte[] data) {

        List<ItemToControl> itemsToControl = ProtocolLayer.createNewControllers(data);
        dataSet.addAll(itemsToControl);
        notifyItemRangeChanged(0, getItemCount());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
