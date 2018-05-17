package com.sigran0.sendreceive.managers;

import java.util.List;

import lombok.Data;

public class ModelManager {

    @Data
    public static class UserData {
        String uid;
        String email;
        String phoneNumber;
        String username;
        boolean isWorker;
        String profileImageUrl;
        int birthDateTimestamp;
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
