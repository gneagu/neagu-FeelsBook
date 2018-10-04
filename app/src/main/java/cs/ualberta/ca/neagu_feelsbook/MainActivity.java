/**
 * MainActivity class runs the main application activity.
 *
 * @author: Gregory Neagu
 * @since: 1.0
 *
 */
package cs.ualberta.ca.neagu_feelsbook;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Class that creates the main user interface.
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "cs.ualberta.ca.neagu_feelsbook";
    private static final String FILENAME = "file.sav";
    private EditText counterText;
    private ListView emotionListView;


    ArrayList<CurrentMood> feelsList;
    ArrayAdapter<CurrentMood> adapter;


    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int gregIsAnnoyed = 0;

        // Declaring every item of activity_main.xml so I can interact with them.
        Button sadButton = (Button) findViewById(R.id.sadButton);
        Button happyButton = (Button) findViewById(R.id.happyButton);
        Button fearButton = (Button) findViewById(R.id.fearButton);
        Button surpriseButton = (Button) findViewById(R.id.surpriseButton);
        Button angerButton = (Button) findViewById(R.id.angryButton);
        Button loveButton = (Button) findViewById(R.id.loveButton);
        final TextView counterText = (TextView) findViewById(R.id.count);
        emotionListView = (ListView) findViewById(R.id.emotionListView);


        if (gregIsAnnoyed == 1){
            feelsList.clear();
            saveInFile();
            adapter.notifyDataSetChanged();

        }





        Log.w("MAIN", "After On Create");
        /*
        Called when an item in the listView is clicked.
         */

        emotionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String s = emotionListView.getItemAtPosition(i).toString();

                CurrentMood x  = new CurrentMood();

                x = feelsList.get(i);

//                feelsList.remove(x);
//                adapter.notifyDataSetChanged();
//

//                Log.w("TRIAL THING", x.toString());

//                Log.w("MAIN", "After On Create");
//                Log.w("Main", "" + i);

                Intent intent = new Intent(MainActivity.this, MoodSelected.class);

//                intent.putExtra("DERP", "" + i);

//                Intent child = new Intent(getPackageName());
                startActivityForResult(intent, 12);

//                Date date = intent.get


//                intent.getExtras();


//                String y = intent.getStringExtra("DELETE");
//
//                Log.w("TRAIL HERE", y);
            }
        });



        // When the sad Button is clicked, tis listener adds a sadMood() object to the feelList,
        // calls the saveInFile function to save the change to the file, and notifies the adapter that
        // the list has changed.
        sadButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                CurrentMood feel = new SadMood();

                feelsList.add(feel);

                saveInFile();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadFromFile();

        adapter = new ArrayAdapter<CurrentMood>(this,
                R.layout.list_item, feelsList);

        emotionListView.setAdapter(adapter);
    }



    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); //library to save objects
            Type listType = new TypeToken<ArrayList<CurrentMood>>(){}.getType();

            feelsList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            feelsList = new ArrayList<CurrentMood>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(feelsList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
