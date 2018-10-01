package cs.ualberta.ca.neagu_feelsbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private EditText counterText;
    private ListView emotionListRepresentation;


    ArrayList<CurrentMood> feelsList;
    ArrayAdapter<CurrentMood> adapter;


    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declaring every item of activity_main.xml so I can interact with them.
        Button sadButton = (Button) findViewById(R.id.sadButton);
        Button happyButton = (Button) findViewById(R.id.happyButton);
        Button fearButton = (Button) findViewById(R.id.fearButton);
        Button surpriseButton = (Button) findViewById(R.id.surpriseButton);
        Button angerButton = (Button) findViewById(R.id.angryButton);
        Button loveButton = (Button) findViewById(R.id.loveButton);
        final TextView counterText = (TextView) findViewById(R.id.count);
        emotionListRepresentation = (ListView) findViewById(R.id.emotionListView);

        sadButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                CurrentMood feel = new SadMood();

//                Log.w("MAIN", "DDEEEEDEED");
//                counterText.append(feel.toString());


                feelsList.add(feel);

//                saveInFile();
//                adapter.notifyDataSetChanged();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        loadFromFile();

        adapter = new ArrayAdapter<CurrentMood>(this,
                R.layout.activity_main, feelsList);

//        emotionListRepresentation = (ListView) findViewById(R.id.emotionListView);

        emotionListRepresentation = (ListView) findViewById(R.id.emotionListView);

        emotionListRepresentation.setAdapter(adapter);


//        adapter = new ArrayAdapter<CurrentMood>(this,
//                R.layout.list_item, feelsList);


//        emotionListRepresentation.setAdapter(adapter);



    }

    private void loadFromFile() {
//    ArrayList<String> tweets = new ArrayList<String>();
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
