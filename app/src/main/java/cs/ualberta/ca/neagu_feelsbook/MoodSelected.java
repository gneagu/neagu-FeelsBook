package cs.ualberta.ca.neagu_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MoodSelected extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_selected);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
    }
}
