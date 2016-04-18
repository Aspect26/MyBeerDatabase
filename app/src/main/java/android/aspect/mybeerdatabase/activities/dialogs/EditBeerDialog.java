package android.aspect.mybeerdatabase.activities.dialogs;

/**
 * Created by Aspect on 3/27/2016.
 */
import android.app.Activity;
import android.aspect.mybeerdatabase.R;
import android.aspect.mybeerdatabase.activities.ListActivity;
import android.aspect.mybeerdatabase.database.Beer;
import android.aspect.mybeerdatabase.database.BeerDatabase;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
// ...

public class EditBeerDialog extends DialogFragment{

    private EditText nameText;
    private EditText descriptionText;
    private ImageView image;
    private DatePicker dateAddedPicker;
    private NumberPicker degreeSpinner;
    private NumberPicker percentageSpinner;
    private RadioGroup typeGetter;
    private Button editButton;
    private String imagePath;
    private BeerDatabase database;
    private Beer beer;

    private static String[] percentages;

    static final int PICK_IMAGE = 6435;

    public EditBeerDialog() {
        int count = 100;
        int beginning = 25;
        percentages = new String[count];

        for(int i=beginning; i<count+beginning; i++)
            percentages[i-beginning] = String.valueOf( i / 10.0);
    }

    public void setDatabase(BeerDatabase database){
        this.database = database;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle instance) {
        View view = inflater.inflate(R.layout.dialog_edit_beer, container);
        getDialog().setTitle("Add Beer");

        nameText = (EditText) view.findViewById(R.id.beer_name);
        descriptionText = (EditText) view.findViewById(R.id.beer_description);
        image = (ImageView) view.findViewById(R.id.beer_image);
        dateAddedPicker = (DatePicker) view.findViewById(R.id.beer_date);
        degreeSpinner = (NumberPicker) view.findViewById(R.id.beer_degree);
        percentageSpinner = (NumberPicker) view.findViewById(R.id.beer_percentage);
        typeGetter = (RadioGroup) view.findViewById(R.id.beer_type);
        editButton = (Button) view.findViewById(R.id.button_addBeer);

        if(beer != null)
            editButton.setText("Edit");

        degreeSpinner.setMaxValue(100);
        degreeSpinner.setValue(10);
        degreeSpinner.setWrapSelectorWheel(false);
        degreeSpinner.setMinValue(0);

        percentageSpinner.setMinValue(0);
        percentageSpinner.setMaxValue(percentages.length - 1);
        percentageSpinner.setWrapSelectorWheel(false);
        percentageSpinner.setDisplayedValues(percentages);
        percentageSpinner.setValue(22);

        //set initial values
        if(beer != null)
            setInitialValues();

        final EditBeerDialog thisDialog = this;

        view.findViewById(R.id.beer_image).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        view.findViewById(R.id.button_addBeer).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String description = descriptionText.getText().toString();
                Date dateAdded = getDateFromDatePicker(dateAddedPicker);
                int degree = degreeSpinner.getValue();
                double percentage = Double.parseDouble(percentages[percentageSpinner.getValue()]);

                // TODO: this shit
                Beer.BeerType type;
                int id = typeGetter.getCheckedRadioButtonId();
                RadioButton selected = (RadioButton)(typeGetter.findViewById(id));
                if(selected.getText().equals("Pale"))
                    type = Beer.BeerType.Pale;
                else if(selected.getText().equals("Dark"))
                    type = Beer.BeerType.Dark;
                else
                    type = Beer.BeerType.Wheat;

                // Add new beer or edit passed beer
                if(beer == null){
                    database.add(new Beer(name, description, dateAdded, degree, percentage, type, 0.5, 0, imagePath));
                }
                else{
                    database.changeBeer(beer, new Beer(name, description, dateAdded, degree, percentage, type, 0.5, 0, imagePath));
                }

                ((ListActivity) getActivity()).recreateBeerList();
                thisDialog.dismiss();
            }
        });

        view.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisDialog.dismiss();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = this.getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                this.imagePath = picturePath;
                this.image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private void setInitialValues(){
        this.nameText.setText(beer.Name);
        this.descriptionText.setText(beer.Description);
        this.degreeSpinner.setValue(beer.Degree);
        this.percentageSpinner.setValue(Arrays.asList(percentages).indexOf(beer.Percentage));
        this.image.setImageBitmap(BitmapFactory.decodeFile(beer.ImagePath));

        Calendar cal = Calendar.getInstance();
        cal.setTime(beer.DateAdded);
        this.dateAddedPicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }
}