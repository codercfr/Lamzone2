package com.example.lamzone2.service;

import com.example.lamzone2.model.Reunion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyReunionGenerator {

    public  static List<Reunion> DUMMY_REUNIONS = Arrays.asList(
            new Reunion("14", "Reunion A", "Peach", "maxime@lamzone.com"),
        new Reunion("16", "Reunion B", "Mario","paul@lamzone.com"),
        new Reunion("19","Reunion C","Luigi","amandine@lamzone.com"));


    static List<Reunion>generateReunions(){return new ArrayList<>(DUMMY_REUNIONS);}
}
