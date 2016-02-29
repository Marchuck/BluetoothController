package pl.agh.kamil.bluetoothcontroller;

import android.app.Application;

import pl.lukmarr.blueduff.BlueDuff;

/**
 * Created by Kamil on 2016-01-06.
 */
public class Core extends Application {
    private char lastLight = 'b';
    private char lastGate = 'f';
    private char lastBlinds = 'h';
    private char lastSound = 'd';
    public static Core instance;
    public BlueDuff blueDuff;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public char getLastLight() {
        return lastLight;
    }

    public void setLastLight(char lastLight) {
        Core.instance.lastLight = lastLight;
    }

    public char getLastGate() {
        return lastGate;
    }

    public void setLastGate(char lastGate) {
        Core.instance.lastGate = lastGate;
    }

    public char getLastBlinds() {
        return lastBlinds;
    }

    public void setLastBlinds(char lastBlinds) {
        Core.instance.lastBlinds = lastBlinds;
    }

    public char getLastSound() {
        return lastSound;
    }

    public void setLastSound(char lastSound) {
        Core.instance.lastSound = lastSound;
    }
}