package cs.ualberta.ca.neagu_feelsbook;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.view.View;

import java.util.Date;
import java.util.Calendar;
import java.util.Locale;


public class MoodSelected extends AppCompatActivity {

    static Integer days;
    static Integer years;
    static Integer monthofYear;
    static Integer signalDateChange = 0;
    static Integer signalMessageChange = 0;
    static Integer hour;
    static Integer minute;
    static String moodType;
    static String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_selected);

        Button timeButton = (Button) findViewById(R.id.timeButton);
        Button dayButton = (Button) findViewById(R.id.dayButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);



        // Get the Intent that started this activity and extract the string
        final Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        final Integer positon = intent.getIntExtra("POSITION", 0);


        intent.putExtra("DERP", "" + positon);

//        CurrentMood derp = getIntent().getSerializableExtra("MyClass");


//        intneg

//        setResult(RESULT_OK);

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
//                Date newDate = new Date();

                if (signalDateChange == 1) {

                    Date newDate = DateModified();

//                    Intent returnIntent = new Intent();
//                    returnIntent.putExtra("result",result);
//                    setResult(Activity.RESULT_OK,returnIntent);
//                    finish();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("result", 1);
//                    resultIntent.putExtra("data", 1);
                    setResult(Activity.RESULT_OK, resultIntent);
                }

                finish();
            }
        });

        /**
         * When day button is pushed.
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

//                intent.putExtra("RETURN VALUE", "DELETE");

                Intent resultIntent = new Intent();
                resultIntent.putExtra("DELETE_POSITION", positon);
                setResult(2, resultIntent);


                finish();
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
            monthofYear = month;
            years = year;
            days = day;
            signalDateChange = 1;

        }
    }

    public static Date DateModified() {
        // In the event this is called, it means that the date has been modified.


        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

//        cal.set(Calendar.YEAR, years);
//        cal.set(Calendar.MONTH, monthofYear);
//        cal.set(Calendar.DATE, days);
//        cal.set(Calendar.HOUR_OF_DAY, hour);
//        cal.set(Calendar.MINUTE, minute);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);
        date = cal.getTime();

        return date;
    }
}
