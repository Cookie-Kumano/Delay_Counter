package jp.gr.java_conf.cookie91.delay_counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class EEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ee);
        Toast.makeText(this, "Designed by Cookie", Toast.LENGTH_LONG).show();
    }
}
