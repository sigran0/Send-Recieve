package com.sigran0.sendreceive.managers;

import com.sigran0.sendreceive.interfaces.DataListner;

public class StaticDataManager {

    static private StaticDataManager instance;

    ModelManager.UserData userData;
    private DatabaseManager databaseManager;
    private boolean isInitialized = false;

    private StaticDataManager() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static StaticDataManager getInstance() {
        if(instance == null)
            instance = new StaticDataManager();
        return instance;
    }

    public void initialize(final DataListner.DataInitializeListener listener) {
        if(!isInitialized)
            databaseManager.getUserData(new DataListner.DataReceiveListener<ModelManager.UserData>() {
                @Override
                public void success(ModelManager.UserData data) {
                    isInitialized = true;
                    userData = data;
                    listener.success();
                }

                @Override
                public void fail(String message) {
                    listener.fail(message);
                }
            });
    }

    public ModelManager.UserData getUserData(){
        if(!isInitialized)
            throw new IllegalAccessError("Before call getUserData, you must call initialize()");
        if(userData == null)
            throw new NullPointerException("userData object is null");
        return userData;
    }
}
