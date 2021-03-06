package de.jutzig.jabylon.common.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

public class AttachablePreferences implements Preferences, Serializable{


    private static final long serialVersionUID = 1L;
    private Map<String, Preferences> children;
    private Map<String, Object> values = new HashMap<String, Object>();
    @Override
    public String absolutePath() {
        return "";
    }

    @Override
    public String[] childrenNames() throws BackingStoreException {
        if(children==null)
            return new String[]{};
        Set<String> keys = children.keySet();
        return keys.toArray(new String[keys.size()]);
    }

    @Override
    public void clear() throws BackingStoreException {
        values.clear();

    }

    @Override
    public void flush() throws BackingStoreException {
        // TODO Auto-generated method stub

    }

    @Override
    public String get(String arg0, String arg1) {
        if(values!=null && values.containsKey(arg1))
            return (String) values.get(arg0);
        return arg1;
    }

    @Override
    public boolean getBoolean(String arg0, boolean arg1) {
        if(values!=null && values.containsKey(arg1))
            return (Boolean) values.get(arg0);
        return arg1;
    }

    @Override
    public byte[] getByteArray(String arg0, byte[] arg1) {
        if(values!=null && values.containsKey(arg1))
            return (byte[]) values.get(arg0);
        return arg1;
    }

    @Override
    public double getDouble(String arg0, double arg1) {
        if(values!=null && values.containsKey(arg1))
            return (Double) values.get(arg0);
        return arg1;
    }

    @Override
    public float getFloat(String arg0, float arg1) {
        if(values!=null && values.containsKey(arg1))
            return (Float) values.get(arg0);
        return arg1;
    }

    @Override
    public int getInt(String arg0, int arg1) {
        if(values!=null && values.containsKey(arg1))
            return (Integer) values.get(arg0);
        return arg1;
    }

    @Override
    public long getLong(String arg0, long arg1) {
        if(values!=null && values.containsKey(arg1))
            return (Long) values.get(arg0);
        return arg1;
    }

    @Override
    public String[] keys() throws BackingStoreException {
        if(values==null)
            return new String[]{};
        Set<String> keys = values.keySet();
        return keys.toArray(new String[keys.size()]);
    }

    @Override
    public String name() {
        return "attachable";
    }

    @Override
    public Preferences node(String arg0) {
        Map<String, Preferences> children = getOrCreateChildren();
        AttachablePreferences child = new AttachablePreferences();
        children.put(arg0, child);
        return child;
    }

    private Map<String, Preferences> getOrCreateChildren() {
        if(children==null)
            children = new HashMap<String, Preferences>();
        return children;
    }

    @Override
    public boolean nodeExists(String arg0) throws BackingStoreException {
        return getOrCreateChildren().containsKey(arg0);
    }

    @Override
    public Preferences parent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void put(String arg0, String arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putBoolean(String arg0, boolean arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putByteArray(String arg0, byte[] arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putDouble(String arg0, double arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putFloat(String arg0, float arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putInt(String arg0, int arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void putLong(String arg0, long arg1) {
        values.put(arg0, arg1);

    }

    @Override
    public void remove(String arg0) {
        values.remove(arg0);

    }

    @Override
    public void removeNode() throws BackingStoreException {
        // TODO Auto-generated method stub

    }

    @Override
    public void sync() throws BackingStoreException {
        // TODO Auto-generated method stub

    }



}
