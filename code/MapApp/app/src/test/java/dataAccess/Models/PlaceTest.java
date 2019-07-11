package dataAccess.Models;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlaceTest {

    @Test
    public void equals() {
        Place p1=new Place(0,0,"","","");
        Place p2=new Place(0,0,"hey","you ","");
        assertEquals(true, p1.equals(p2));
    }
}