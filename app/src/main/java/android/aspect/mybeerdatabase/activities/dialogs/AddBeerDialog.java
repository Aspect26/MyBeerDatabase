package android.aspect.mybeerdatabase.activities.dialogs;

/**
 * Created by Aspect on 3/27/2016.
 */
import android.aspect.mybeerdatabase.R;
import android.aspect.mybeerdatabase.activities.MainActivity;
import android.aspect.mybeerdatabase.database.Beer;
import android.aspect.mybeerdatabase.database.BeerDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;
import java.util.Date;
// ...

public class AddBeerDialog extends DialogFragment{

    private EditText nameText;
    private EditText descriptionText;
    private DatePicker dateAddedPicker;
    private NumberPicker degreeSpinner;
    private NumberPicker percentageSpinner;
    private RadioGroup typeGetter;

    private BeerDatabase database;

    private static String[] percentages;

    public AddBeerDialog() {
        int count = 100;
        int beginning = 25;
        percentages = new String[count];

        for(int i=beginning; i<count+beginning; i++)
            percentages[i-beginning] = String.valueOf( i / 10.0);
    }

    public void setDatabase(BeerDatabase database){
        this.database = database;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle instance) {
        View view = inflater.inflate(R.layout.new_beer_dialog, container);
        getDialog().setTitle("Add Beer");

        nameText = (EditText) view.findViewById(R.id.beer_name);
        descriptionText = (EditText) view.findViewById(R.id.beer_description);
        dateAddedPicker = (DatePicker) view.findViewById(R.id.beer_date);
        degreeSpinner = (NumberPicker) view.findViewById(R.id.beer_degree);
        percentageSpinner = (NumberPicker) view.findViewById(R.id.beer_percentage);
        typeGetter = (RadioGroup) view.findViewById(R.id.beer_type);

        degreeSpinner.setMaxValue(100);
        degreeSpinner.setValue(10);
        degreeSpinner.setWrapSelectorWheel(false);
        degreeSpinner.setMinValue(0);

        percentageSpinner.setMinValue(0);
        percentageSpinner.setMaxValue(percentages.length - 1);
        percentageSpinner.setWrapSelectorWheel(false);
        percentageSpinner.setDisplayedValues(percentages);
        percentageSpinner.setValue(22);

        final AddBeerDialog thisDialog = this;

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

                Beer beer = new Beer(name, description, dateAdded, degree, percentage, type);
                database.add(beer);
                ((MainActivity) getActivity()).recreateBeerList();
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

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}