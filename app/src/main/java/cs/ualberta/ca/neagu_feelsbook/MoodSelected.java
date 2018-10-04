package cs.ualberta.ca.neagu_feelsbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MoodSelected extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_selected);

        Button timeButton = (Button) findViewById(R.id.timeButton);
        Button dayButton = (Button) findViewById(R.id.dayButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);



        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        intent.putExtra("DERP", "RESULT");
        setResult(RESULT_OK);

//        finish();
    }
}
