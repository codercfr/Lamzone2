package com.example.lamzone2.service;

import com.example.lamzone2.model.Reunion;

import java.util.ArrayList;
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
        return new ArrayList<>(reunions);
    }

    public  List<Reunion> getReunionFilter(String constraint){
        List<Reunion>filteredList =new ArrayList<>();
        if(constraint == null || constraint.length() == 0 ){
            filteredList.addAll(reunions);
        }else
        {
            for(Reunion items: reunions) {
                if (items.getSujet().toLowerCase().contains(constraint.toLowerCase()) || items.getDatetime().toLowerCase().contains(constraint.toLowerCase())) {
                    filteredList.add(items);
                }
            }
        }
        return filteredList;

    }



}
