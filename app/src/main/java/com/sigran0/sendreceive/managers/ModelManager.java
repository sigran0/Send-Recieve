package com.sigran0.sendreceive.managers;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Setter;

public class ModelManager {

    @Data
    public static class UserData {
        String uid;
        String email;
        String phoneNumber;
        String username;
        String imageUrl;
        int type;
        String birthDate;
    }

    public enum ItemState {
        NO_ONE_ACCEPT,
        DELIVERING,
        COMPLETE
    }

    @Data
    public static class ItemData {
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
    public static class ItemDataList {
        int size = 0;
        List<ItemData> itemDataList = new ArrayList<>();
    }

    @Data
    public static class CategoryList {
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
