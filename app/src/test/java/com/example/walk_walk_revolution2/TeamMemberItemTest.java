package com.example.walk_walk_revolution2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeamMemberItemTest {

    TeamMemberItem team;
    @Before
    public void setup() {
        team = new TeamMemberItem("Loc Vu", "LV", 555);
    }

    @Test
    public void getterTests(){
        assertEquals(team.getColor(), 555);
        assertEquals(team.getInitials(), "LV");
        assertEquals(team.getName(), "Loc Vu");

    }

}