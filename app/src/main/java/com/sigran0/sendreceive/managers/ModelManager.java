package com.sigran0.sendreceive.managers;

import com.sigran0.sendreceive.recycler.holder.ItemListHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Setter;

public class ModelManager {

    public static enum ITEM_STATE {
        NOT_PROCESS,
        PROCESSING,
        DELIVERY_COMPLETE,
        COMPLETE
    }

    @Data
    public static class TempData implements Serializable{
        ItemListHolder.TYPE type;
        ItemDataList data;
    }

    @Data
    public static class UserData implements Serializable{
        String uid;
        String email;
        String phoneNumber;
        String username;
        String imageUrl;
        int type;
        int money;
        String birthDate;
    }

    public enum ItemState implements Serializable{
        NO_ONE_ACCEPT,
        DELIVERING,
        COMPLETE
    }

    @Data
    public static class ItemData implements Serializable{
        String itemName;
        String customerUid;
        String customerName;
        String delivererUid;
        String delivererName;
        String imageUrl;
        String startPos;
        String endPos;
        long timestamp;
        int price;
        int estimatePrice;
        int category;
        int size;
        int processState;
    }

    @Data
    public static class ItemDataList implements Serializable {
        int size = 0;
        List<ItemData> itemDataList = new ArrayList<>();
    }

    @Data
    public static class CategoryList implements Serializable{
        List<Category> categoryList;
    }

    @Data
    public static class Category {
        String name;
        List<SubCategory> subCategoryList;
        TranslateData title;
    }

    @Data
    public static class SubCategory {
        String name;
        TranslateData title;
    }

    @Data
    public static class TranslateData {
        String kr;
        String us;
    }
}
