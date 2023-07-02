package com.example.loancollector.utils;

import androidx.annotation.NonNull;

import com.example.loancollector.model.Loan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonHelper {

    public static String toJsonString(@NonNull Gson gson, Loan loan) {
        return gson.toJson(loan);
    }

    public static Loan fromJsonString(@NonNull Gson gson, String jsonString) {
        return gson.fromJson(jsonString, new TypeToken<Loan>() {}.getType());
    }
}
