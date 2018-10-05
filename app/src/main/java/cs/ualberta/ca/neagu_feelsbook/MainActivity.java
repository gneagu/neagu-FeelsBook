/**
 * MainActivity class runs the main application activity.
 *
 * @author: Gregory Neagu
 * @since: 1.0
 *
 * Copyright 2018 Gregory Neagu

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package cs.ualberta.ca.neagu_feelsbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that creates the main user interface.
 */
public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "fileasds.sav";
    private EditText counterText;
    private ListView emotionListView;
    private ViewFlipper viewFlipper;
    private EditText enterMessage;
    private static TextView displayDate;
    private static TextView counterScreen;

    static Integer days;
    static Integer years;
    static Integer monthofYear;
    static Integer signalDateChange = 0;
    static Integer signalTimeChange = 0;
    static Integer selectedEmotion= 0;
    static Integer atHome = 0;
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
        counterScreen = (TextView) findViewById(R.id.Counter);

        // These are the buttons from the second "view" that are also in this layout.
        Button timeButton = (Button) findViewById(R.id.timeButton);
        Button dayButton = (Button) findViewById(R.id.dayButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        displayDate = (TextView) findViewById(R.id.dateView);
        enterMessage = (EditText) findViewById(R.id.messageBox);

        /**
         * Shows a simple alert message when the message is longer then the limit.
         *
         * @author: MysticMagic
         */
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Your message is larger than the allowed character limit!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // Specifies the action when an entry in the listView is clicked.
        emotionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedEmotion = i;
                selectedEmotion(i);
                atHome = 1;

            }
        });

        /**
         * When day button is pushed this triggers the date picker to appear and allows the user
         * to select a date.
         */
        dayButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }

        });

        /**
         * When save button is pushed, if the date has been modified, then it is updated, and the
         * object is set with the new date. The emotion object also has its message updated.
         */
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                /* If date has changed either through the date picker or time picker, the new
                 date is created, and then updated on the object.
                 */

                if (enterMessage.getText().length() <= 100) {
                    if (signalDateChange == 1 || signalTimeChange == 1)  {
                        Date newDate = DateModified(feelsList.get(selectedEmotion).getMoodDate());

                        feelsList.get(selectedEmotion).setMoodDate(newDate);
                        signalTimeChange = 0;
                        signalDateChange = 0;
                    }

                    // The emotions message is updated.
                    feelsList.get(selectedEmotion).setMessage("" + enterMessage.getText());
                    atHome = 0;
                    adapter.notifyDataSetChanged();
                    viewFlipper.showPrevious();
                } else {
                    alertDialog.show();
                }

                Collections.sort(feelsList);
                simpleCounter(feelsList);
                adapter.notifyDataSetChanged();
                }
        });

        /**
         * When delete button is pushed the emotion is deleted from the emotion list.
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                feelsList.remove(feelsList.get(selectedEmotion));
                saveInFile();
                simpleCounter(feelsList);
                adapter.notifyDataSetChanged();
                atHome = 0;

                viewFlipper.showPrevious();
            }
        });

        /**
         * When time button is pushed, the time picker is triggered and appears.
         */
        timeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        /**
         * When the sad Button is clicked, this listener adds a sadMood() object to the feelList,
         * calls the saveInFile function to save the change to the file, and notifies the adapter that
         * the list has changed.
         */
        sadButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                CurrentMood feel = new SadMood();

                feelsList.add(feel);

                saveInFile();
                adapter.notifyDataSetChanged();
                simpleCounter(feelsList);
            }
        });

        /**
         * When the happy Button is clicked, this listener adds a sadMood() object to the feelList,
         * calls the saveInFile function to save the change to the file, and notifies the adapter that
         * the list has changed.
         */
        happyButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                CurrentMood feel = new JoyMood();

                feelsList.add(feel);

                saveInFile();
                adapter.notifyDataSetChanged();
                simpleCounter(feelsList);
            }
        });

        /**
         * When the fear Button is clicked, this listener adds a sadMood() object to the feelList,
         * calls the saveInFile function to save the change to the file, and notifies the adapter that
         * the list has changed.
         */
        fearButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                CurrentMood feel = new FearMood();

                feelsList.add(feel);

                saveInFile();
                adapter.notifyDataSetChanged();
                simpleCounter(feelsList);
            }
        });

        /**
         * When the love Button is clicked, this listener adds a sadMood() object to the feelList,
         * calls the saveInFile function to save the change to the file, and notifies the adapter that
         * the list has changed.
         */
        loveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                CurrentMood feel = new LoveMood();

                feelsList.add(feel);

                saveInFile();
                adapter.notifyDataSetChanged();
                simpleCounter(feelsList);
            }
        });

        /**
         * When the surprise Button is clicked, this listener adds a sadMood() object to the feelList,
         * calls the saveInFile function to save the change to the file, and notifies the adapter that
         * the list has changed.
         */
        surpriseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                CurrentMood feel = new SurpriseMood();

                feelsList.add(feel);

                saveInFile();
                adapter.notifyDataSetChanged();
                simpleCounter(feelsList);
            }
        });

        /**
         * When the angry Button is clicked, this listener adds a sadMood() object to the feelList,
         * calls the saveInFile function to save the change to the file, and notifies the adapter that
         * the list has changed.
         */
        angerButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                CurrentMood feel = new AngryMood();

                feelsList.add(feel);

                saveInFile();
                adapter.notifyDataSetChanged();
                simpleCounter(feelsList);
            }
        });
    }


    /**
     * This function is run when the program is created. It calls the loadFromFile function to
     * read the collection of moods from the save file, and then binds them to an adapter.
     */
    @Override
    protected void onStart() {
        super.onStart();

        loadFromFile();
        adapter = new ArrayAdapter<CurrentMood>(this,
                R.layout.list_item, feelsList);
        emotionListView.setAdapter(adapter);
    }

    /**
     * @Author: Rosevear (Cmput 301 TA)
     *
     * This functions loads a collection of CurrentMood objects from a save file.
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson(); //library to save objects
            Type listType = new TypeToken<ArrayList<CurrentMood>>(){}.getType();

            feelsList = gson.fromJson(in, listType);

            Collections.sort(feelsList);
            simpleCounter(feelsList);

        } catch (FileNotFoundException e) {
            feelsList = new ArrayList<CurrentMood>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @Author: Rosevear (Cmput 301 TA)
     *
     * This function saves the collection of CurrentMood objects to a save file.
     */
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

    /**
     * @author: GOOGLE
     *
     * This code is taken from the url in the README.md.
     * This code is covered under the Apache 2.0 license.
     *
     * This function specifies the behaviour of the time picker. On create, it modifies
     * variables (hour, and minute).
     */
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

        // Once the time is specified, the variables mentioned above are changed.
        public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
            hour = hourOfDay;
            minute = minuteOfDay;

            signalTimeChange = 1;
            Date throwAway = DateModified(new Date());
        }
    }

    /**
     * @author: GOOGLE
     *
     * This code is taken from the url in the README.md.
     * This code is covered under the Apache 2.0 license.
     *
     * This function specifies the behaviour of the date picker. On create, it modifies
     * variables (year, month, and day).
     */
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

        // Once the date is selected, the variables mentioned above are changed.
        public void onDateSet(DatePicker view, int year, int month, int day) {
            monthofYear = month;
            years = year;
            days = day;

            signalDateChange = 1;
            Date throwAway = DateModified(new Date());

        }

    }

    /**
     * This function takes a date parameter and then checks to see which set of date variables have
     * been changed above. The first set (year, month, and day) are modified by the date selector,
     * and the second (hour, minute) are modified by the time picker. The date is modified with the
     * modified variables, and is then returned.
     *
     * @param date
     * @return date
     */
    public static  Date DateModified(Date date) {

        // In the event this is called, it means that the date has been modified.
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        // These variables are modified by the date picker.
        if (signalDateChange == 1) {
            cal.set(Calendar.YEAR, years);
            cal.set(Calendar.MONTH, monthofYear);
            cal.set(Calendar.DATE, days);
        }

        // These variables are modified by the time picker.
        if (signalTimeChange == 1) {
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
        }

        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        date = cal.getTime();

        // Code from Mincong Huang
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String text = "" + sdf.format(date);

        displayDate.setText(text);

        return date;
    }

    /**
     * This function takes the position of the emotion in the list of emotions, and then takes
     * the date from it, and displays it in the textView "editDate", and displays the message
     * in the editText "enterMessage".
     *
     * @param selectedEmotion
     */
    public void selectedEmotion(Integer selectedEmotion) {
        viewFlipper.showNext(); //Flips the view to the other window.

        String messageToShow = feelsList.get(selectedEmotion).getMessage(); //Get the message

        // Code from Mincong Huang
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String text = sdf.format(feelsList.get(selectedEmotion).getMoodDate());

        enterMessage.setText(messageToShow);
        displayDate.setText(text);
    }


    /**
     * This function modifies the back button behaviour. Since the views are shown on the same
     * interface, pressing the back button would exit out of the program, so behaviour needs to
     * be modified to change views instead.
     *
     * Atrribution: ekawas
     */
    @Override
    public void onBackPressed() {
        if (atHome != 0) {
            atHome = 0;
            viewFlipper.showPrevious();
        }
    }

    /**
     * This function receives the list of emotions, extracts the name, and then adds that name
     * to another list which uses the name as a keep to keep track of occurrences of that list in
     * the emotionList
     *
     * @attribution: techiedelight.com
     * @param listOfEmotions
     */
    public void simpleCounter(ArrayList<CurrentMood> listOfEmotions) {

        String toDisplay = new String();
        Map<String, Integer> countedEmotions = new HashMap<>();

        //Iterates through list of emotions, extracts name, and increments ocurences in countedEmotions
        for (CurrentMood emotion : listOfEmotions) {
            String mood = emotion.getMood();

            //If value does not exist for key, creates value, otherwise increments it.
            Integer count = countedEmotions.get(mood);
            if (count == null) {
                count = 0;
            }
            countedEmotions.put(mood, count + 1);
        }

        //Iterates through countedEmotions and adds counts to a string.
        for (Map.Entry<String, Integer> entry : countedEmotions.entrySet()) {
            toDisplay += entry.getKey() + ": " + entry.getValue() + " ";
        }

        //Updates textView on screen with the counters.
        counterScreen.setText(toDisplay);
    }
}
