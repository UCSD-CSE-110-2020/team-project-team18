package com.example.walk_walk_revolution2;

import com.example.walk_walk_revolution2.TeamScreen;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InitialsTest {
    @Test
    public void testInitials()
    {
        String name1 = "Ariana Grande";
        String name2 = "Richard P. Nixon";
        String name3 = "John";
        String name4 = "";
        String name5 = "     John   Doe     ";

        String initial1 = TeamScreen.getTeamMemberInitials(name1);
        String initial2 = TeamScreen.getTeamMemberInitials(name2);
        String initial3 = TeamScreen.getTeamMemberInitials(name3);
        String initial4 = TeamScreen.getTeamMemberInitials(name4);
        String initial5 = TeamScreen.getTeamMemberInitials(name5);

        assertEquals(initial1, "AG");
        assertEquals(initial2, "RPN");
        assertEquals(initial3, "J");
        assertEquals(initial4, "");
        assertEquals(initial5, "JD");
    }
}
