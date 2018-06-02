package com.sigran0.sendreceive.managers;

import com.sigran0.sendreceive.interfaces.DataListner;

public class StaticDataManager {

    static private StaticDataManager instance;

    ModelManager.UserData userData;
    private DatabaseManager databaseManager;
    private boolean isInitialized = false;
    private String[] categoryList = {
            "일반", "식품", "냉동품", "깨지기 쉬운것", "전자제품", "취급주의", "생물"
    };
    private String[] sizeList = {
            "아주 작음", "작음", "보통", "큼", "아주 큼"
    };

    private static Double startMoney = 2000.0;

    private static Double[] categoryMpList = {
            1.0, 1.0, 1.8, 1.1, 1.1, 1.5, 1.1
    };

    private static Double[] sizeMpList = {
            1.0, 1.0, 1.1, 1.5, 5.0
    };

    public static int estimateFee(int category, int size) {
        double result = Math.round(startMoney * categoryMpList[category] * sizeMpList[size]);
        return (int) ((result / 100.0) * 100);
    }

    private StaticDataManager() {
        databaseManager = DatabaseManager.getInstance();
    }

    public static StaticDataManager getInstance() {
        if(instance == null)
            instance = new StaticDataManager();
        return instance;
    }

    public boolean isInitialized(){
        return isInitialized;
    }

    public void initialize(final DataListner.DataInitializeListener listener) {
        if(!isInitialized)
            databaseManager.getUserData(
                    new DataListner.DataReceiveListener<ModelManager.UserData>() {
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

    public String[] getCategoryList(){
        return categoryList;
    }

    public String[] getSizeList(){
        return sizeList;
    }
}
