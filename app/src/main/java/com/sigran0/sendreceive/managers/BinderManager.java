package com.sigran0.sendreceive.managers;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import java.util.HashMap;

public class BinderManager {

    private static final String TAG = "BinderManager";

    static private BinderManager instance;
    private HashMap<String, SparseArray<BinderInterface>> mBindList = new HashMap<>();

    private BinderManager(){

    }

    public static BinderManager getInstance(){

        if(instance == null)
            instance = new BinderManager();

        return instance;
    }

    @NonNull
    public void bind(String key, BinderInterface _interface){

        if(key == null || _interface == null)
            throw new NullPointerException("Argument can not be null object");

        if(!mBindList.containsKey(key)){
            SparseArray<BinderInterface> aList = new SparseArray<>();
            mBindList.put(key, aList);
        }

        SparseArray<BinderInterface> aList = mBindList.get(key);
        aList.put(_interface.hashCode(), _interface);
    }

    @NonNull
    public <T> void startUpdate(@NonNull String key, T data){

        if(!mBindList.containsKey(key)){
            Log.d(TAG, "startUpdate: Binder has no key");
            return;
        }

        SparseArray<BinderInterface> aList = mBindList.get(key);

        if(aList == null)
            throw new NullPointerException("This key has no instance.");

        if(aList.size() <= 0) {
            Log.d(TAG, "startUpdate: Binder has  no item to update");
            return;
        }

        for(int c = 0; c < aList.size(); c++){

            int arrayKey = aList.keyAt(c);

            BinderInterface<T> binderInterface = aList.get(arrayKey);

            if(binderInterface != null)
                binderInterface.update(data);
        }
    }

    public void unbind(@NonNull String key){

        SparseArray temp = mBindList.get(key);

        if(temp != null)
            temp.clear();
    }

    public void removeAll(){
        mBindList = new HashMap<>();
    }

    public interface BinderInterface<T>{
        void update(T data);
    }
}
