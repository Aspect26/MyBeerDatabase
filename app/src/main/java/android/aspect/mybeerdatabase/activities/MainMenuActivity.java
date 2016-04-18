package android.aspect.mybeerdatabase.activities;

import android.aspect.mybeerdatabase.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Aspect on 4/18/2016.
 */
public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        ((Button)findViewById(R.id.mainmenubutton_beers)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainMenuActivity.this, ListActivity.class);
                MainMenuActivity.this.startActivity(myIntent);
            }
        });

        ((Button)findViewById(R.id.mainmenubutton_about)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainMenuActivity.this, AboutActivity.class);
                MainMenuActivity.this.startActivity(myIntent);
            }
        });

        ((Button)findViewById(R.id.mainmenubutton_changelog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainMenuActivity.this, ChangelogActivity.class);
                MainMenuActivity.this.startActivity(myIntent);
            }
        });

        ((Button)findViewById(R.id.mainmenubutton_exit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
