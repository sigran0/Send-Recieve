package com.sigran0.sendreceive.managers;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DatabaseManager {


    private static final String TAG = "DatabaseManager";

    private static DatabaseManager instance;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private UserManager userManager;

    private DatabaseManager(){

        database = FirebaseDatabase.getInstance();
        userManager = UserManager.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public static DatabaseManager getInstance(){

        if(instance == null)
            instance = new DatabaseManager();

        return instance;
    }

    public void uploadImage(String key, Uri imageUri, final SaveListener listener) {
        UploadTask uploadTask = storage
                                    .getReference("itemImage")
                                    .child(key)
                                    .putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                listener.fail(e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                listener.success();
            }
        });
    }

    public void saveUserData(final ModelManager.UserData data, Uri imageUri, final SaveListener saveListener) {
        final DatabaseReference ref = database
                                        .getReference("userData")
                                        .child(data.getUid())
                                        .push();
        final String key = ref.getKey();

        uploadImage(key, imageUri, new SaveListener() {
            @Override
            public void success() {
                data.setImageUrl(key);
                ref.setValue(data);
                saveListener.success();
            }

            @Override
            public void fail(String message) {
                fail(message);
            }
        });
    }

    public void saveItemData(final ModelManager.ItemData data, Uri imageUri, final SaveListener saveListener) {
        final DatabaseReference ref = database.getReference("itemData").push();
        final String key = ref.getKey();

        uploadImage(key, imageUri, new SaveListener() {
            @Override
            public void success() {
                data.setImageUrl(key);
                ref.setValue(data);
                saveListener.success();
            }

            @Override
            public void fail(String message) {
                fail(message);
            }
        });
    }

    public void getUserData(final DataReceiveListener<ModelManager.UserData> listener) {

        final String uid = userManager.getUID();

        database.getReference("userData")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ModelManager.UserData result = dataSnapshot
                                                        .child(uid)
                                                        .getValue(ModelManager.UserData.class);
                        listener.onReceive(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onError(databaseError.getMessage());
                        throw databaseError.toException();
                    }
                });
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
                        listener.onError(databaseError.getMessage());
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

    public interface SaveListener {
        public void success();
        public void fail(String message);
    }

    public interface DataReceiveListener<T>{
        public void onReceive(T data);
        public void onError(String message);
    }
}
