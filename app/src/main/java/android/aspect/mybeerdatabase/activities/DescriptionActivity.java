package android.aspect.mybeerdatabase.activities;

import android.aspect.mybeerdatabase.R;
import android.aspect.mybeerdatabase.activities.dialogs.AddBeerDialog;
import android.aspect.mybeerdatabase.database.BeerDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;

/**
 * Created by Aspect on 4/11/2016.
 */
public class DescriptionActivity extends AppCompatActivity {
    private BeerDatabase database;
    private TableLayout beerTable;
    private static boolean dark = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}