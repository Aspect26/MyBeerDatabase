package android.aspect.mybeerdatabase.database;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Aspect on 3/27/2016.
 */

public class Beer implements Serializable{
    public enum BeerType{
        Pale,
        Dark,
        Wheat,
    }

    public String Name;
    public String Description;
    public String ImagePath;
    public Date DateAdded;
    public BeerType Type;
    public int Degree;
    public int Rating;
    public double Percentage;
    public double BottleSize;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/dd/MM");

    public Beer(){
        this("Unknown");
    }

    public Beer(String name){
        this(name, "No description.", Calendar.getInstance().getTime(), -1, -1, BeerType.Pale);
    }

    public Beer(String name, String description, Date dateAdded, int degree, double percentage, BeerType type){
        this(name, description, dateAdded, degree, percentage, type, 0.5, 3, "");
    }

    public Beer(String name, String description, Date dateAdded, int degree, double percentage,
                BeerType type, double bottleSize, int rating, String imagePath){
        this.Name = name;
        this.Description = description;
        this.DateAdded = dateAdded;
        this.Degree = degree;
        this.Percentage = percentage;
        this.Type = type;
        this.BottleSize = bottleSize;
        this.Rating = rating;
        this.ImagePath = imagePath;
    }

    public String getSerialized(){
        JSONObject json = new JSONObject();

        try {
            json.put("Name", Name);
            json.put("Description", Description);
            json.put("Date", dateFormat.format(DateAdded));
            json.put("Degree", Degree);
            json.put("Percentage", Percentage);
            json.put("Type", Type);
            json.put("Size", BottleSize);
            json.put("Rating", Rating);
            json.put("Image", ImagePath);
        } catch(JSONException e){
            Log.e("SAVE", "Could not serialize beer: " + this);
        }

        return json.toString();
    }

    public static Beer createFromJSON(JSONObject json){
        Beer beer = new Beer();
        try {
            beer.Name = json.getString("Name");
            beer.Description = json.getString("Description");
            beer.DateAdded = dateFormat.parse(json.getString("Date"));
            beer.Degree = json.getInt("Degree");
            beer.Percentage = json.getDouble("Percentage");
            beer.Type = BeerType.valueOf(json.getString("Type"));
            beer.BottleSize = json.getDouble("Size");
            beer.Rating = json.getInt("Rating");
            beer.ImagePath = json.getString("Image");
        } catch(JSONException e){
            Log.e("LOAD", "Could not load beer from json.");
            return beer.Name.equals("")? null : beer;
        } catch (ParseException e){
            Log.e("LOAD", "Could not parse beer date from json.");
            return beer.Name.equals("")? null : beer;
        }

        return beer;
    }

    public String getFormattedDate(){
        return dateFormat.format(DateAdded);
    }

    @Override
    public String toString(){
        return "[Beer] " + Name + " (" + Degree + "°)";
    }
}
