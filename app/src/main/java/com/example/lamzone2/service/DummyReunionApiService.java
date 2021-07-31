package com.example.lamzone2.service;

import com.example.lamzone2.model.Reunion;
import java.util.List;

public class DummyReunionApiService implements ReunionApiService{

private static final List<Reunion> reunions=DummyReunionGenerator.generateReunions();
    @Override
    public void creatRunion(Reunion reunion) {
        reunions.add(reunion);
    }

    @Override
    public void deleteReunion(Reunion reunion) {
        reunions.remove(reunion);
    }

    @Override
    public List<Reunion> getReunion() {
        return reunions;
    }



}
