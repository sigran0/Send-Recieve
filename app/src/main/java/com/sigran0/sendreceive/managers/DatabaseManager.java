package com.sigran0.sendreceive.managers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseManager {


    private static final String TAG = "DatabaseManager";

    private static DatabaseManager instance;
    private FirebaseDatabase database;

    private DatabaseManager(){

        database = FirebaseDatabase.getInstance();
        Log.d(TAG, String.format("database : %s", database.toString()));
    }

    public static DatabaseManager getInstance(){

        if(instance == null)
            instance = new DatabaseManager();

        return instance;
    }

    public void getCategoryList(final DataReceiveListener<ModelManager.CategoryList> listener){

        database.getReference("categoryData")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ModelManager.CategoryList result = dataSnapshot.getValue(ModelManager.CategoryList.class);
                        listener.onReceive(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onError("good");
                    }
                });
    }

//    public void getCategoryList(final DataReceiveListener<List<ModelManager.Category>> listener){
//
//        database.getReference("category").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                List<ModelManager.Category> result = new ArrayList<>();
//
//                for(DataSnapshot category : dataSnapshot.getChildren()){
//                    List<ModelManager.SubCategory> subResult = new ArrayList<>();
//
//                    for(DataSnapshot subCategory : category.getChildren()){
//                        if(!subCategory.getKey().equals("title")) {
//                            ModelManager.SubCategory sub = subCategory.getValue(ModelManager.SubCategory.class);
//                            subResult.add(sub);
//                        }
//                    }
//
//                    ModelManager.TranslateData categoryTitle = category.child("title").getValue(ModelManager.TranslateData.class);
//
//                    ModelManager.Category resultCategory = new ModelManager.Category();
//
//                    resultCategory.setCategories(subResult);
//                    resultCategory.setTitle(categoryTitle);
//
//                    result.add(resultCategory);
//                }
//
//                listener.onReceive(result);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                listener.onError("good");
//            }
//        });
//    }

    public interface DataReceiveListener<T>{
        public void onReceive(T data);
        public void onError(String message);
    }
}
