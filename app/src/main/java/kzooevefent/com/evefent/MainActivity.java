package kzooevefent.com.evefent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String id = intent.getStringExtra("Eid");
        String name = intent.getStringExtra("eName");
        //System.out.println("Recieved: Eid: " + id + " eName: " + name);
        //String desc = intent.getStringExtra("desc");
        setContentView(R.layout.activity_main);
        TextView text = (TextView) findViewById(R.id.text);
        text.setText("This is a generic main activity for event " + name + ".");

    }

}
