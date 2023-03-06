package com.example.cse110_team2.UnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import com.example.cse110_team2.SharedCompassAPI;
import com.example.cse110_team2.User;
import org.junit.Test;

import java.util.UUID;

public class APITest {

    @Test
    public void testGetUser() {
        SharedCompassAPI api = SharedCompassAPI.provide();

        User fetchedUser =  api.getUser("point-nemo");

        assertNotNull(fetchedUser);
        assertEquals(fetchedUser.name, "Point Nemo");
        assertEquals(fetchedUser.latitude, -48.876667, 0.1);
        assertEquals(fetchedUser.longitude, -123.393333, 0.1);

    }

    @Test
    public void testPushUser(){
        SharedCompassAPI api = SharedCompassAPI.provide();
        String publicID = UUID.randomUUID().toString();
        String privateID = UUID.randomUUID().toString();
        User newUser = User.createUser("test-user",publicID, privateID);

        api.putUser(newUser);
        User fetchedUser = api.getUser(publicID);

        assertEquals(newUser.name, fetchedUser.name);
        assertEquals(newUser.uid, fetchedUser.uid);
        assertEquals(newUser.longitude, fetchedUser.longitude, 0.1);
        assertEquals(newUser.latitude, fetchedUser.latitude, 0.1);

        api.deleteUser(newUser);

    }

}
