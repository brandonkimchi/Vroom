package com.example.vroomandroidapplicationv4.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchProfileViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SearchProfileViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is search profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}