package pl.agh.kamil.bluetoothcontroller.dynamicSheets.utils;

/**
 * Created by Łukasz Marczak
 *
 * @since 29.02.16
 */

public class ItemToControl {
    public String id;//signal: 'a'
    public String triggerOff;//signal: 'b'
    public String name;
    public boolean currentState;//true=ON, false=OFF
    public String opis;
    public String com;

    public ItemToControl() {
    }

    public ItemToControl(String id, String triggerOff, String name , String opis, String com) {
        this.id = id;
        this.triggerOff = triggerOff;
        this.name = name;
        this.opis = opis;
        this.com = com;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getTriggerName() {
        return currentState ? "on" : "off";
    }
}