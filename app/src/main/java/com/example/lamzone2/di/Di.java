package com.example.lamzone2.di;

import com.example.lamzone2.service.DummyReunionApiService;
import com.example.lamzone2.service.ReunionApiService;

public class Di {

    private static final ReunionApiService service = new DummyReunionApiService();


    public static ReunionApiService getService(){return service;}
}
