package com.example.walk_walk_revolution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewRouteDetail extends AppCompatActivity {

    private TextView txtName;
    private TextView txtStartPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_route_detail);

        txtName =  (TextView) findViewById(R.id.txtName);
        txtStartPoint = (TextView) findViewById(R.id.txtStartPoint);

        Button viewDetail = (Button) findViewById(R.id.view_route_detail);

        viewDetail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                launchRouteDetail();
            }
        });
    }

    public void launchRouteDetail() {
        String walkname = "Walk1";
        String startingPoint = "UCSD";
        Intent intent = new Intent(this, Route.class);
        intent.putExtra("name", walkname);
        intent.putExtra("startPoint", startingPoint);
        startActivity(intent);
    }
}