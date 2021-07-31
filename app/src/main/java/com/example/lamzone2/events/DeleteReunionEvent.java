package com.example.lamzone2.events;

import com.example.lamzone2.model.Reunion;

public class DeleteReunionEvent {

    public Reunion reunion;

    public DeleteReunionEvent(Reunion reunion){this.reunion=reunion;}
}
