package com.sigran0.sendreceive.managers;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.sigran0.sendreceive.interfaces.DataListner;

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

    public void uploadImage(String key, Uri imageUri, final DataListner.DataSendListener listener) {
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


    public void downloadImage(String key, final DataListner.DataReceiveListener<Uri> listener) {
        storage.getReference("itemImage")
                .child(key)
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.success(uri);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        listener.fail(e.getMessage());
                    }
                });
    }

    public void saveUserData(final ModelManager.UserData data, Uri imageUri, final DataListner.DataSendListener dataSendListener) {
        final DatabaseReference ref = database
                                        .getReference("userData")
                                        .child(data.getUid());

        final String key = ref.getKey();

        uploadImage(key, imageUri, new DataListner.DataSendListener() {
            @Override
            public void success() {
                data.setImageUrl(key);
                ref.setValue(data);
                dataSendListener.success();
            }

            @Override
            public void fail(String message) {
                fail(message);
            }
        });
    }

    public void deleteItemData(String itemId){
        database.getReference("itemData")
                .child(itemId).removeValue();
    }

    public void saveItemData(final ModelManager.ItemData data, Uri imageUri, final DataListner.DataSendListener dataSendListener) {
        final DatabaseReference ref = database.getReference("itemData").push();
        final String key = ref.getKey();

        uploadImage(key, imageUri, new DataListner.DataSendListener() {
            @Override
            public void success() {
                data.setImageUrl(key);
                ref.setValue(data);
                dataSendListener.success();
            }

            @Override
            public void fail(String message) {
                fail(message);
            }
        });
    }

    public void getUserData(final DataListner.DataReceiveListener<ModelManager.UserData> listener) {

        final String uid = userManager.getUID();

        database.getReference("userData")
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ModelManager.UserData result = dataSnapshot
                                                        .getValue(ModelManager.UserData.class);
                        listener.success(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.fail(databaseError.getMessage());
                        throw databaseError.toException();
                    }
                });
    }

    public void getNotProceedItemListData(final DataListner.DataReceiveListener<ModelManager.ItemDataList> listener) {

        database.getReference("itemData")
                .orderByChild("processState")
                .equalTo(0)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ModelManager.ItemDataList result = new ModelManager.ItemDataList();

                        for(DataSnapshot item : dataSnapshot.getChildren()) {
                            ModelManager.ItemData temp = item.getValue(ModelManager.ItemData.class);
                            result.getItemDataList().add(temp);
                        }

                        result.setSize(result.getItemDataList().size());
                        listener.success(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.fail(databaseError.getMessage());
                        throw databaseError.toException();
                    }
                });
    }

    public void getMySendListData(final DataListner.DataReceiveListener<ModelManager.ItemDataList> listener) {

        final String uid = userManager.getUID();

        database.getReference("itemData")
                .orderByChild("customerUid")
                .equalTo(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ModelManager.ItemDataList result = new ModelManager.ItemDataList();

                        for(DataSnapshot item : dataSnapshot.getChildren()) {
                            ModelManager.ItemData temp = item.getValue(ModelManager.ItemData.class);
                            result.getItemDataList().add(temp);
                        }

                        result.setSize(result.getItemDataList().size());
                        listener.success(result);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.fail(databaseError.getMessage());
                        throw databaseError.toException();
                    }
                });

    }
}
