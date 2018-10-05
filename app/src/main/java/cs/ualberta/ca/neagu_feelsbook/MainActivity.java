/**
 * MainActivity class runs the main application activity.
 *
 * @author: Gregory Neagu
 * @since: 1.0
 *
 */
package cs.ualberta.ca.neagu_feelsbook;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Class that creates the main user interface.
 */
public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private EditText counterText;
    private ListView emotionListView;
    private ViewFlipper viewFlipper;
    private EditText enterMessage;
    private TextView displayDate;

    static Integer days;
    static Integer years;
    static Integer monthofYear;
    static Integer signalDateChange = 0;
    static Integer selectedEmotion= 0;
    static Integer hour;
    static Integer minute;
    static String moodType;
    static String message;

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
        emotionListView = (ListView) findViewById(R.id.emotionListView);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        Button timeButton = (Button) findViewById(R.id.timeButton);
        Button dayButton = (Button) findViewById(R.id.dayButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        displayDate = (TextView) findViewById(R.id.dateView);
        enterMessage = (EditText) findViewById(R.id.messageBox);

        emotionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedEmotion = i;
                selectedEmotion(i);

            }
        });

        /**
         * When day button is pushed.
         */
        dayButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }

        });

        /**
         * When save button is pushed.
         */
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (signalDateChange == 0) {

                    Date newDate = DateModified();
                    Log.w("AT THE SAVE", "" + newDate.toString());

                }

                viewFlipper.showPrevious();
            }
        });

        /**
         * When day button is pushed.
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                feelsList.remove(feelsList.get(selectedEmotion));
                saveInFile();
                adapter.notifyDataSetChanged();

                viewFlipper.showPrevious();
            }
        });

        /**
         * When time button is pushed.
         */
        timeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        // When the sad Button is clicked, this listener adds a sadMood() object to the feelList,
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

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
            hour = hourOfDay;
            minute = minuteOfDay;
            signalDateChange = 1;
        }
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
//            Log.w("AT THE PICKER", "" + year + month + day);

            monthofYear = month;

            years = year;
            days = day;
            signalDateChange = 1;

        }

    }

        public static Date DateModified() {
            // In the event this is called, it means that the date has been modified.
//            Integer a = 2018;
//            Integer b = 8;
//            Integer c = 11;
//            Integer d = 10;
//            Integer e = 10;


            Calendar cal = Calendar.getInstance();
            Date date1 = new Date();
            Date date = new Date();

            cal.set(Calendar.YEAR, years);
            cal.set(Calendar.MONTH, monthofYear);
            cal.set(Calendar.DATE, days);
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            signalDateChange = 1;

//            cal.set(Calendar.YEAR, a);
//            cal.set(Calendar.MONTH, b);
//            cal.set(Calendar.DATE, c);
//            cal.set(Calendar.HOUR_OF_DAY, d);
//            cal.set(Calendar.MINUTE, e);

            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            date = cal.getTime();

            Log.w("At save", "" + date.toString());
            Log.w("AFTER", "" + date1.toString());

            return date;
        }


        public void selectedEmotion(Integer selectedEmotion) {
            viewFlipper.showNext();
            String dateToShow;
            dateToShow = "" + feelsList.get(selectedEmotion).getMoodDate();
            String messageToShow = feelsList.get(selectedEmotion).getMessage();

            enterMessage.setText(messageToShow);
            displayDate.setText(dateToShow);

        }

    @Override
    public void onBackPressed()
    {


        viewFlipper.showPrevious();

    }
}
