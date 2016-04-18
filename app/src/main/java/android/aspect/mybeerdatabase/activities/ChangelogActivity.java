package android.aspect.mybeerdatabase.activities;

import android.aspect.mybeerdatabase.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Aspect on 4/18/2016.
 */
public class ChangelogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changelog);

        ((Button)findViewById(R.id.changelog_button_mainmenu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
