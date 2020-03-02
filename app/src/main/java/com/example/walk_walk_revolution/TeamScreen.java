package com.example.walk_walk_revolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

public class TeamScreen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter teamAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button acceptButton;
    private Button declineButton;

    private LinearLayout invitation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_screen);

        invitation = findViewById(R.id.invitation);
        invitation.setVisibility(View.GONE); //when the app is first loaded, no invitation should appear.

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_screen);
        recyclerView = (RecyclerView) findViewById(R.id.team_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<String> teamNames = getTeamMemberNames();
        // specify an adapter (see also next example)
        teamAdapter = new TeamItemAdapter(teamNames);
        recyclerView.setAdapter(teamAdapter);
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
        ArrayList<String> teamNames = getTeamMemberNames();

    }

    //getter method for the names of the current team members
    //post-condition: if name is empty, then nothing should appear on the screen.
    public ArrayList<String> getTeamMemberNames()
    {
        ArrayList<String> teamNames = new ArrayList<String>();
        teamNames.add("Ariana Grande");
        teamNames.add("Ellen Degeneres");
        teamNames.add("Richard Milhous Nixon");
        teamNames.add("Sarah Silverman");
        teamNames.add("Michael Gary Scott");
        teamNames.add("David Graham");
        teamNames.add("Eliot Lastname");
        teamNames.add("Ross Boss");
        teamNames.add("Kanye West");
        teamNames.add("Kanye East");
        teamNames.add("Oscar");
        teamNames.add("Larold");
        teamNames.add("Kimothy");
        teamNames.add("Timberly");
        return teamNames;
    }

    //getter method for the name of the sender of an invitation.
    public static String getInvitation()
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
