package android.aspect.mybeerdatabase.activities;

import android.aspect.mybeerdatabase.R;
import android.aspect.mybeerdatabase.activities.dialogs.AddBeerDialog;
import android.aspect.mybeerdatabase.database.Beer;
import android.aspect.mybeerdatabase.database.BeerDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * Created by Aspect on 4/11/2016.
 */
public class DescriptionActivity extends AppCompatActivity {
    private BeerDatabase database;
    private TableLayout beerTable;
    private static boolean dark = true;
    private Beer beer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        beer = (Beer)(getIntent().getSerializableExtra("beer"));

        ((TextView)findViewById(R.id.description_name)).setText(beer.Name);
        ((TextView)findViewById(R.id.description_description)).setText(beer.Description);
        ((TextView)findViewById(R.id.description_alcohol)).setText(beer.Percentage + "%");
        ((TextView)findViewById(R.id.description_date)).setText(beer.getFormattedDate());
        ((TextView)findViewById(R.id.description_degree)).setText(beer.Degree + "°");
        ((ImageView)findViewById(R.id.description_beerimage)).setImageBitmap(BitmapFactory.decodeFile(beer.ImagePath));
    }
}