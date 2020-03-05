package com.example.walk_walk_revolution2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class TeamScreen extends AppCompatActivity {

    private Button acceptButton;
    private Button declineButton;
    private LinearLayout invitation;
    private static int[] colorList = {0xff66ff66, 0xff88ddbb, 0xffff6666, 0xff6666ff, 0xffffff66,
                                      0xffff4dd2, 0xffcc6600, 0xff00ffff, 0xffffcccc, 0xffff9900};
    ArrayList<TeamMemberItem> teamMemberItems;

    public static final String FIREBASE_SERVICE_KEY = "FIREBASE_SERVICE_KEY";

    String firebaseServiceKey;
    private FirebaseService firebaseService;
    private DocumentSnapshot documentSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_screen);

        firebaseServiceKey = getIntent().getStringExtra(FIREBASE_SERVICE_KEY);
        firebaseService = FirebaseServiceFactory.create(firebaseServiceKey, this);

        firebaseService.setup(getEmail());

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
        System.out.println("GetTeamMemberNames");
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


        // Lookup the recyclerview in activity layout
        RecyclerView rvTeamMemberItems = (RecyclerView) findViewById(R.id.team_recycler_view);
        rvTeamMemberItems.removeAllViews();
        // Initialize contacts
        teamMemberItems = getTeamMemberItems();
        // Create adapter passing in the sample user data
        TeamMemberItemAdapter adapter = new TeamMemberItemAdapter(teamMemberItems);
        // Attach the adapter to the recyclerview to populate items
        rvTeamMemberItems.setAdapter(adapter);
        // Set layout manager to position the items
        rvTeamMemberItems.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
    }

    //getter method for the names of the current team members
    //post-condition: if name is empty, then nothing should appear on the screen.
    public static ArrayList<String> getTeamMemberNames()
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
        teamNames.add("Rob Boss");
        teamNames.add("Kyle Boss");
        teamNames.add("Bob Ross");
        teamNames.add("Easter Dude");
        teamNames.add("Carl");
        teamNames.add("Carrol");
        teamNames.add("Kimothy");
        teamNames.add("Nelson");

        return teamNames;
    }

    //getter method for the name of the sender of an invitation.
    public String getInvitation()
    {
        documentSnapshot = firebaseService.getInvitation();

        return documentSnapshot.getString("FIRST_NAME") + " " + documentSnapshot.getString("LAST_NAME");
    }

    //getter method to return the initials of the name that is passed in.
    public String getTeamMemberInitials(String name)
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

    public ArrayList<TeamMemberItem> getTeamMemberItems(){
        ArrayList<String> names = getTeamMemberNames();
        ArrayList<TeamMemberItem> teamMemberItems = new ArrayList<TeamMemberItem>();
        for(int i = 0; i < names.size(); i++){
            String initials = getTeamMemberInitials(names.get(i));
            int color = getBackgroundColor(i);
            TeamMemberItem item = new TeamMemberItem(names.get(i), initials, color);
            teamMemberItems.add(item);
        }
        return teamMemberItems;

    }
    public static int getBackgroundColor(int offset){

        offset = offset % colorList.length;
        return colorList[offset];

    }

    //called when you accept an invite and reloads the current team members on the screen.
    private void acceptInvite()
    {

        /*
        TODO:
          1. Get inviter email, access their document and append our email to their teammates list, set their onTeam to true
          2. Add the inviter email to our list of teammates
          3. Change our onTeam to true
          4. Delete invitation collection

         */
        invitation.setVisibility(View.GONE);
        loadTeamMembers();
    }

    //called when you decline an invite (doesn't need to reload the current team members on the screen).
    private void declineInvite()
    {
        invitation.setVisibility(View.GONE);
        //loadTeamMembers();
    }

    public String getEmail(){

        SharedPreferences spfs = getSharedPreferences("user_email", MODE_PRIVATE);
        return spfs.getString("userEmail", null);
    }
}
