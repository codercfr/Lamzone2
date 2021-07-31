package com.example.lamzone2;

import com.example.lamzone2.di.Di;
import com.example.lamzone2.model.Reunion;
import com.example.lamzone2.reunion_liste.ContactReunionAdpater;
import com.example.lamzone2.service.DummyReunionGenerator;
import com.example.lamzone2.service.ReunionApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Objects;
import java.util.logging.Filter;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private ReunionApiService service ;


    @Before
    public void setup(){service= Di.getService();}

    @Test
    public void  getReunionWithSuccess(){
        List<Reunion> reunions= service.getReunion();
        List<Reunion>expected_reunions = DummyReunionGenerator.DUMMY_REUNIONS;
        assertThat(reunions, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expected_reunions.toArray())));

    }
    @Test
     public void deleteNeighbourReunion(){
        Reunion reunionToDelete = service.getReunion().get(0);
        service.deleteReunion(reunionToDelete);
        assertFalse(service.getReunion().contains(reunionToDelete));
    }


    //test add
    @Test
    public void addNeighbourReunion(){
        List<Reunion> reunions= service.getReunion();
        Reunion reunion = new Reunion("10","salle B","Bowser","catherine@lamzone2");
        reunions.add(reunion);
        assertTrue(service.getReunion().contains(reunion));
    }

    //test filtre
    @Test
    public void TestFilter(){
        List<Reunion> reunions= service.getReunion();
        List<Reunion> filterList;
        String reunionSelected= service.getReunion().get(0).getSujet();
        ContactReunionAdpater adpater = new ContactReunionAdpater(reunions);
        filterList=adpater.filterList(reunionSelected,reunions);
        assertTrue(reunionSelected.contains(filterList.get(0).getSujet()));
        assertEquals(filterList.size(),1);
        //assertTrue(reunionSelected.contains());

    }

}