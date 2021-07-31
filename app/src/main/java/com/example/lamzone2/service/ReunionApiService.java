package com.example.lamzone2.service;

import com.example.lamzone2.model.Reunion;

import java.util.ArrayList;
import java.util.List;

public interface ReunionApiService {

    void creatRunion(Reunion reunion);

    void deleteReunion(Reunion reunion);

    List<Reunion>getReunion();



}
