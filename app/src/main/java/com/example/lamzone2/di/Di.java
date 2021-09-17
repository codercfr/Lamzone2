package com.example.lamzone2.di;

import android.widget.Filter;
import android.widget.Filterable;

import com.example.lamzone2.model.Reunion;
import com.example.lamzone2.service.DummyReunionApiService;
import com.example.lamzone2.service.ReunionApiService;

import java.util.ArrayList;
import java.util.List;

public class Di implements Filterable {

    private static final ReunionApiService service = new DummyReunionApiService();
    public static ReunionApiService getService(){return service;}


    @Override
    public Filter getFilter() {
        return null;
    }
}
