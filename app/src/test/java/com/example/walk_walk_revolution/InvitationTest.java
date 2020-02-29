package com.example.walk_walk_revolution;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InvitationTest {
    @Test
    public void testInvitation()
    {
        String inviter = "Bob";

        assertEquals(inviter, TeamScreen.getInvitation());
    }
}
