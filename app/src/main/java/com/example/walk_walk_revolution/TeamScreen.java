package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class TeamScreen extends AppCompatActivity {
    private LinearLayout layoutMember1;
    private LinearLayout layoutMember2;
    private LinearLayout layoutMember3;
    private LinearLayout layoutMember4;
    private LinearLayout layoutMember5;
    private LinearLayout layoutMember6;

    private TextView initialView1;
    private TextView initialView2;
    private TextView initialView3;
    private TextView initialView4;
    private TextView initialView5;
    private TextView initialView6;

    private TextView nameView1;
    private TextView nameView2;
    private TextView nameView3;
    private TextView nameView4;
    private TextView nameView5;
    private TextView nameView6;

    private String member1Name;
    private String member2Name;
    private String member3Name;
    private String member4Name;
    private String member5Name;
    private String member6Name;

    private Button acceptButton;
    private Button declineButton;

    private LinearLayout invitation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_screen);

        invitation = findViewById(R.id.invitation);
        invitation.setVisibility(View.GONE); //when the app is first loaded, no invitation should appear.

        layoutMember1 = findViewById(R.id.linearLayout1);
        layoutMember2 = findViewById(R.id.linearLayout2);
        layoutMember3 = findViewById(R.id.linearLayout3);
        layoutMember4 = findViewById(R.id.linearLayout4);
        layoutMember5 = findViewById(R.id.linearLayout5);
        layoutMember6 = findViewById(R.id.linearLayout6);

        initialView1 = findViewById(R.id.initials_1);
        initialView2 = findViewById(R.id.initials_2);
        initialView3 = findViewById(R.id.initials_3);
        initialView4 = findViewById(R.id.initials_4);
        initialView5 = findViewById(R.id.initials_5);
        initialView6 = findViewById(R.id.initials_6);

        nameView1 = findViewById(R.id.team_member_1);
        nameView2 = findViewById(R.id.team_member_2);
        nameView3 = findViewById(R.id.team_member_3);
        nameView4 = findViewById(R.id.team_member_4);
        nameView5 = findViewById(R.id.team_member_5);
        nameView6 = findViewById(R.id.team_member_6);

        acceptButton = findViewById(R.id.accept_button);
        declineButton = findViewById(R.id.decline_button);

        acceptButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                acceptInvite();
            }
        });
        declineButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                declineInvite();
            }
        });

        loadTeamMembers();
        loadInvitation();
    }

    //this method is called in order to display an invitation on the screen
    public void loadInvitation() {
        String name = getInvitation();
        TextView inviter = findViewById(R.id.name_of_inviter);
        if(name.length() == 0)
        {
            invitation.setVisibility(View.GONE);
            return;
        }
        inviter.setText(name);

        invitation.setVisibility(View.VISIBLE);
    }

    //this method is called in order to display the current team's members on the screen.
    public void loadTeamMembers() {
        getTeamMemberNames();
        LinearLayout[] layouts = new LinearLayout[] {layoutMember1, layoutMember2, layoutMember3, layoutMember4, layoutMember5, layoutMember6};
        String[] names = new String[] {member1Name, member2Name, member3Name, member4Name, member5Name, member6Name};
        TextView[] initialViews = new TextView[] {initialView1, initialView2, initialView3, initialView4, initialView5, initialView6};
        TextView[] nameViews = new TextView[] {nameView1, nameView2, nameView3, nameView4, nameView5, nameView6};

        for(int i = 0; i < 6; i++)
        {
            if(names[i].length() > 0)
            {
                String initials = getTeamMemberInitials(names[i]);
                initialViews[i].setText(initials);
                nameViews[i].setText("Name: " + names[i]);
                layouts[i].setVisibility(View.VISIBLE);
            }
            else
            {
                initialViews[i].setText("");
                nameViews[i].setText("Name: ");
                layouts[i].setVisibility(View.GONE);
            }
        }

        TextView member1 = findViewById(R.id.team_member_1);
        TextView member2 = findViewById(R.id.team_member_2);
        TextView member3 = findViewById(R.id.team_member_3);
        TextView member4 = findViewById(R.id.team_member_4);
        TextView member5 = findViewById(R.id.team_member_5);
        TextView member6 = findViewById(R.id.team_member_6);


    }

    //getter method for the names of the current team members
    public void getTeamMemberNames()
    {
        member1Name = "Ariana G.";
        member2Name = "Ellen D.";
        member3Name = "Richard N.";
        member4Name = "Sarah S.";
        member5Name = "";
        member6Name = "";
    }

    //getter method for the name of the sender of an invitation.
    public String getInvitation()
    {
        String inviter = "Bob";

        return inviter;
    }

    //getter method to return the initials of the name that is passed in.
    public static String getTeamMemberInitials(String name)
    {
        String initials = "";
        name = name.trim();
        if(name.length() == 0)
        {
            return initials;
        }
        initials = initials + name.charAt(0);
        for(int i = 0; i < name.length(); i++){
            if(name.charAt(i) == ' ' && name.charAt(i + 1) != ' '){
                initials = initials + name.charAt(i + 1);
            }
        }

        return initials;
    }

    //called when you accept an invite and reloads the current team members on the screen.
    private void acceptInvite()
    {
        invitation.setVisibility(View.GONE);
        loadTeamMembers();
    }

    //called when you decline an invite (doesn't need to reload the current team members on the screen).
    private void declineInvite()
    {
        invitation.setVisibility(View.GONE);
        //loadTeamMembers();
    }
}
