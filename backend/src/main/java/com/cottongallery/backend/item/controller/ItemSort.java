package com.cottongallery.backend.item.controller;

public enum ItemSort {
    CREATED_DATE("createdDate"),
    LIKE_COUNT("likeCount");

    private final String fieldName;

    ItemSort(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
