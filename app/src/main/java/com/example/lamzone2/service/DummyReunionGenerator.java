package com.example.lamzone2.service;

import com.example.lamzone2.model.Reunion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyReunionGenerator {

    public  static List<Reunion> DUMMY_REUNIONS = Arrays.asList(
            new Reunion("14h00", "Reunion A", "Peach", "maxime@lamzone.com",0xFFA3CED2, "10/8/21"),
        new Reunion("16h00", "Reunion B", "Mario","paul@lamzone.com",0xFFA3CED2, "10/9/21"),
        new Reunion("19h00","Reunion C","Luigi","amandine@lamzone.com",0xFFA3CED2, "10/10/21"));


    static List<Reunion>generateReunions(){return new ArrayList<>(DUMMY_REUNIONS);}
}
