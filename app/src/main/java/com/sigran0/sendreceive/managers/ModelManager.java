package com.sigran0.sendreceive.managers;

import java.util.List;

import lombok.Data;
import lombok.Setter;

public class ModelManager {

    @Data
    @Setter
    public static class UserData {
        String uid;
        String email;
        String phoneNumber;
        String username;
        String imageUrl;
        int type;
        String birthDate;
    }

    @Data
    public static class ItemData {
        String customerUid;
        String senderUid;
        String name;
        String imageUrl;
        String startPos;
        String endPos;
        int price;
        int estimatePrice;
        int category;
        int size;
        int processState;
    }

    @Data
    @Setter
    public static class ItemDataList {
        List<ItemData> itemDataList;
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
