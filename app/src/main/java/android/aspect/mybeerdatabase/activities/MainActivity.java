package android.aspect.mybeerdatabase.activities;

import android.app.AlertDialog;
import android.aspect.mybeerdatabase.activities.dialogs.EditBeerDialog;
import android.aspect.mybeerdatabase.R;
import android.aspect.mybeerdatabase.database.Beer;
import android.aspect.mybeerdatabase.database.BeerDatabase;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private BeerDatabase database;
    private TableLayout beerTable;
    private static boolean dark = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = new BeerDatabase(getFilesDir().getAbsolutePath());

        ((Button)findViewById(R.id.button_addNewBeer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                EditBeerDialog editBeerDialog = new EditBeerDialog();
                editBeerDialog.setDatabase(database);
                editBeerDialog.show(fm, "fragment_edit_name");
            }
        });

        database.load();
        recreateBeerList();
    }

    public void recreateBeerList(){
        ((TableLayout) findViewById(R.id.beer_table)).removeAllViews();
        for(Beer beer : database) {
            addBeerToTable(beer);
        }
    }

    private void addBeerToTable(final Beer beer){
        View child;
        if(!dark)
            child = getLayoutInflater().inflate(R.layout.beer_table_item_light, null);
        else
            child = getLayoutInflater().inflate(R.layout.beer_table_item_dark, null);
        dark = !dark;

        ((TextView)child.findViewById(R.id.tableitem_name)).setText(beer.Name);
        ((TextView)child.findViewById(R.id.tableitem_degree)).setText(String.valueOf(beer.Degree) + "°");
        ((TextView)child.findViewById(R.id.tableitem_date)).setText(beer.getFormattedDate());

        if(beer.ImagePath == null) {
            int imageId = R.drawable.beer_icon;
            switch (beer.Type) {
                case Pale:
                    imageId = R.drawable.beer_icon;
                    break;
                case Dark:
                    imageId = R.drawable.darkbeer_icon;
                    break;
                case Wheat:
                    imageId = R.drawable.wheatbeer_icon;
                    break;
            }
            ((ImageView) child.findViewById(R.id.beerIcon)).setImageResource(imageId);
        }
        else{
            ((ImageView) child.findViewById(R.id.beerIcon)).setImageBitmap(BitmapFactory.decodeFile(beer.ImagePath));
        }

        final MainActivity thisActivity = this;
        ((ImageButton)child.findViewById(R.id.tableitem_removeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity);
                builder.setMessage("Do you want to remove " + beer.Name + "?");
                builder.setPositiveButton("Yes", new YesNoDialogListener(beer));
                builder.setNegativeButton("No", new YesNoDialogListener(beer));
                builder.show();
            }
        });

        ((ImageButton)child.findViewById(R.id.tableitem_editButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                EditBeerDialog editBeerDialog = new EditBeerDialog();
                editBeerDialog.setDatabase(database);
                editBeerDialog.setBeer(beer);
                editBeerDialog.show(fm, "fragment_edit_name");
            }
        });

        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, DescriptionActivity.class);
                myIntent.putExtra("beer", beer);
                MainActivity.this.startActivity(myIntent);
            }
        });

        ((TableLayout) findViewById(R.id.beer_table)).addView(child);
    }

    private class YesNoDialogListener implements DialogInterface.OnClickListener{
        private final Beer selectedBeer;

        public YesNoDialogListener(Beer selectedBeer){
            this.selectedBeer = selectedBeer;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    database.remove(selectedBeer);
                    recreateBeerList();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
