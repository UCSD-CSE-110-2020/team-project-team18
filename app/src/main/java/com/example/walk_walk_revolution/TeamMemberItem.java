package com.example.walk_walk_revolution;

public class TeamMemberItem {
    private String name;
    private String initials;
    private int color;

    public TeamMemberItem(String name, String initials, int color) {
        this.name = name;
        this.initials = initials;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getInitials() {
        return initials;
    }

    public int getColor() {
        return color;
    }

}
