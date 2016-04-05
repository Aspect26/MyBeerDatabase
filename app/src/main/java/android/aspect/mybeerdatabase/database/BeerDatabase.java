package android.aspect.mybeerdatabase.database;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by Aspect on 3/27/2016.
 */
public class BeerDatabase implements Iterable<Beer>{
    private ArrayList<Beer> beers = new ArrayList<>();

    private static String DB_FILE_PATH;

    public BeerDatabase(String filesPath){
        DB_FILE_PATH = filesPath + "/beer_data.dat";
    }

    public void add(Beer beer){
        this.beers.add(beer);
        sort();
        save();
    }

    public int size(){
        return beers.size();
    }

    public void save(){
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(DB_FILE_PATH));
            for(Beer beer : this){
                writer.write(beer.getSerialized() + "\n");
            }
            writer.close();

        } catch (IOException e){
        }
    }

    public void load(){
        beers = new ArrayList<>();

        try {
            BufferedReader iReader = new BufferedReader(new InputStreamReader(new FileInputStream(DB_FILE_PATH)));
            String line;
            while( (line = iReader.readLine()) != null){
                try {
                    Beer beer = Beer.createFromJSON(new JSONObject(line));
                    if(beer != null)
                        beers.add(beer);
                }catch (JSONException e){
                    Log.e("LOAD", "Could not create JSON object when loading from database file.");
                }
            }

            iReader.close();

        } catch(FileNotFoundException e){
            try {
                (new File(DB_FILE_PATH)).createNewFile();
            } catch (IOException f){
                Log.e("load", "Could not create ddatabase file: " + f.getMessage());
            }
        } catch (IOException e){
            Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    public String[] getNames(){
        String[] arr = new String[this.size()];
        int i = 0;
        for(Beer beer : this)
            arr[i++] = beer.Name;

        return arr;
    }

    public Beer get(int index){
        return beers.get(index);
    }

    public boolean remove(Beer beer){
        boolean result = beers.remove(beer);
        if(result)
            save();

        return result;
    }

    private void sort(){
        Collections.sort(beers, new Comparator<Beer>() {
            @Override
            public int compare(Beer lhs, Beer rhs) {
                return lhs.Name.compareTo(rhs.Name);
            }
        });
    }

    @Override
    public Iterator<Beer> iterator() {
        return new Iterator<Beer>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < beers.size();
            }

            @Override
            public Beer next() {
                return beers.get(current++);
            }

            @Override
            public void remove() {

            }
        };
    }
}
