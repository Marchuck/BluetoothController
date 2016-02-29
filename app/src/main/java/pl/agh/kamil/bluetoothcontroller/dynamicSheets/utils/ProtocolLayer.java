package pl.agh.kamil.bluetoothcontroller.dynamicSheets.utils;

import android.util.Log;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak
 *
 * @since 29.02.16
 */
public class ProtocolLayer {

    public static final String TAG = ProtocolLayer.class.getSimpleName();

    public static List<ItemToControl> createNewControllers(byte[] bytes) {
        Log.d(TAG, "createNewController: ");
        List<ItemToControl> list = new ArrayList<>();

        String str = new String(bytes, Charset.forName("UTF-8"));

        String[] splitted1 = str.split(";");
        for (String s1 : splitted1) {
            String s2[] = s1.split(",");
            String nazwa = s2[0];
            String triggerOn = s2[1];
            String triggerOff = s2[2];
            String com = s2[3];
            String opis = s2[4];
            ItemToControl item = new ItemToControl(triggerOn, triggerOff, nazwa, opis, com);
            list.add(item);
        }
        return list;
    }
}
