package pl.agh.kamil.bluetoothcontroller;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by Kamil on 2016-01-06.
 */
public class Core extends Application {
    private static Main.ConnectedThread communicationThread = null;
    private static Context currentContext = null;
    private static char lastLight = 'b';
    private static char lastGate = 'f';
    private static char lastBlinds = 'h';
    private static char lastSound = 'd';


    public static Context getCurrentContext() {
        return currentContext;
    }

    public static void setCurrentContext(Context currentContext) {
        Core.currentContext = currentContext;
    }

    public static Main.ConnectedThread getCommunication() {
        Log.d("CORE", "get connected thread");
        return communicationThread;
    }

    public static void setCommunication(Main.ConnectedThread c) {
        communicationThread = c;
    }
    public static char getLastLight() {
        return lastLight;
    }

    public static void setLastLight(char lastLight) {
        Core.lastLight = lastLight;
    }

    public static char getLastGate() {
        return lastGate;
    }

    public static void setLastGate(char lastGate) {
        Core.lastGate = lastGate;
    }

    public static char getLastBlinds() {
        return lastBlinds;
    }

    public static void setLastBlinds(char lastBlinds) {
        Core.lastBlinds = lastBlinds;
    }

    public static char getLastSound() {
        return lastSound;
    }

    public static void setLastSound(char lastSound) {
        Core.lastSound = lastSound;
    }
}