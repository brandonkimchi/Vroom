package com.example.vroomandroidapplicationv4.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchCalenderViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SearchCalenderViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is search calender fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}