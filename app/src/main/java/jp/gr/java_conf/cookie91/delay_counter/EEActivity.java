package jp.gr.java_conf.cookie91.delay_counter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;


public class EEActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ee);
        imageView = (ImageView) findViewById(R.id.imageView);
        /**
        Picasso.get()
                .load(R.drawable.kumano_run)
                .fit()
                .centerCrop()
                .into(imageView);
         **/
        imageView.setImageResource(R.drawable.kumano_run);
        String versionName = BuildConfig.VERSION_NAME;
        Toast.makeText(this,
                "遅刻カウンタ v"
                + versionName
                + "\nIllustration by Cookie", Toast.LENGTH_LONG).show();
    }
}
