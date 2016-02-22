package pl.agh.kamil.bluetoothcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * Created by Kamil on 2016-01-06.
 */
public class ListMenu extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Core.setCurrentContext(this);
        Core.setLastBlinds('h');
        setResult(Activity.RESULT_CANCELED);

        final ListView listview = (ListView) findViewById(R.id.listView1);
        final String[] values = new String[]{
                "Kuchnia",
                "Salon",
                "Łazienka",
                "Brama",
        };
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                R.layout.select_activity_item, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);

                view.animate().setDuration(250).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = null;
                                if (item.equals(values[0]))
                                    intent = new Intent(getBaseContext(),
                                            LightsActivity.class);
                                else if (item.equals(values[1]))
                                    intent = new Intent(getBaseContext(),
                                            Lights1Activity.class);
                                else if (item.equals(values[2]))
                                    intent = new Intent(getBaseContext(),
                                            MediaActivity.class);
                                else if (item.equals(values[3]))
                                    intent = new Intent(getBaseContext(),
                                            GateActivity.class);
                                else
                                    intent = null;

                                if (intent != null)
                                    startActivity(intent);
                                else {
                                    finish();
                                }
                                view.setAlpha(1);
                            }
                        });
            }
        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}