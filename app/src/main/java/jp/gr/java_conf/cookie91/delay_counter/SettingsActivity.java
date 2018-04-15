package jp.gr.java_conf.cookie91.delay_counter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    TextView delayLimitView;
    TextView cuttingLimitView;
    TextView absenceLimitView;
    TextView versionView;

    LinearLayout dSet;
    LinearLayout cSet;
    LinearLayout aSet;
    LinearLayout resetSettings;
    LinearLayout resetData;
    LinearLayout counter;
    LinearLayout about;

    String item;
    String situation;

    int count = 0;
    int delayCount = 0;
    int cuttingCount = 0;
    int absenceCount = 0;
    int leaveCount = 0;
    int delayLimit = 0;
    int cuttingLimit = 0;
    int absenceLimit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        delayLimitView = (TextView) findViewById(R.id.delayLimitView);
        cuttingLimitView = (TextView) findViewById(R.id.cuttingLimitView);
        absenceLimitView = (TextView) findViewById(R.id.absenceLimitView);
        versionView = (TextView) findViewById(R.id.version);
        String versionName = BuildConfig.VERSION_NAME;
        versionView.setText("遅刻カウンタ v" + versionName);
        loadSettings(getApplicationContext());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dSet:
                item = "遅刻";
                showDialog();
                break;
            case R.id.cSet:
                item = "欠課";
                showDialog();
                break;
            case R.id.aSet:
                item = "欠席";
                showDialog();
                break;
            case R.id.resetSettings:
                situation = "目標値の設定をリセット";
                aDialog();
                break;
            case R.id.resetData:
                situation = "出席状況のデータをリセット";
                aDialog();
                break;
            case R.id.counter:
                count ++;
                if (count >= 10){
                    Intent i = new Intent(SettingsActivity.this, EEActivity.class);
                    startActivity(i);
                    count = 0;
                    break;
                }
                break;
            case R.id.about:
                Intent i = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(i);
                break;
        }
    }

    public void showDialog() {

                LayoutInflater inflater = SettingsActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.setting_dialog, null, false);

                final NumberPicker np = (NumberPicker) view.findViewById(R.id.numberPicker);
                np.setMaxValue(40);
                np.setMinValue(0);

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("目標値を設定")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (item.equals("遅刻 ･ 早退"))
                            delayLimit = np.getValue();
                        if (item.equals("欠課"))
                            cuttingLimit = np.getValue();
                        if (item.equals("欠席"))
                            absenceLimit = np.getValue();

                        changeandsave();
                    }
                })
                .setNeutralButton("リセット", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (item.equals("遅刻 ･ 早退"))
                            delayLimit = 20;
                        if (item.equals("欠課"))
                            cuttingLimit = 40;
                        if (item.equals("欠席"))
                            absenceLimit = 40;

                        changeandsave();
                    }
                })
                .setNegativeButton("Cancel", null)
                .setView(view)
                .show();
    }

    private void aDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage( situation + "します。\nよろしくて？")
                .setCancelable(false)
                .setPositiveButton("登録", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (situation.equals("目標値の設定をリセット")) {
                            delayLimit = 20;
                            cuttingLimit = 40;
                            absenceLimit = 40;
                        } else if (situation.equals("出席状況のデータをリセット")) {
                            delayCount = 0;
                            cuttingCount = 0;
                            absenceCount = 0;
                            leaveCount = 0;
                        }

                        changeandsave();
                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });

        builder.show();
    }

    private void changeandsave() {
        delayLimitView.setText(String.valueOf(delayLimit));
        cuttingLimitView.setText(String.valueOf(cuttingLimit));
        absenceLimitView.setText(String.valueOf(absenceLimit));

        saveSettings(getApplicationContext(), delayCount, cuttingCount, absenceCount, leaveCount, delayLimit, cuttingLimit, absenceLimit);
    }

    public void loadSettings(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        delayCount = sp.getInt("DELAY", 0);
        cuttingCount = sp.getInt("CUTTING", 0);
        absenceCount = sp.getInt("ABSENCE", 0);
        leaveCount = sp.getInt("LEAVE", 0);
        delayLimit = sp.getInt("DLIMIT", 20);
        cuttingLimit = sp.getInt("CLIMIT", 40);
        absenceLimit = sp.getInt("ALIMIT", 40);
    }

    private void saveSettings(Context context, int delayCount, int cuttingCount, int absenceCount, int leaveCount, int delayLimit, int cuttingLimit, int absenceLimit) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt("DELAY", delayCount)
                .putInt("CUTTING", cuttingCount)
                .putInt("ABSENCE", absenceCount)
                .putInt("LEAVE", leaveCount)
                .putInt("DLIMIT", delayLimit)
                .putInt("CLIMIT", cuttingLimit)
                .putInt("ALIMIT", absenceLimit);

        editor.commit();

        Toast.makeText(SettingsActivity.this, "設定を保存しましたっ！", Toast.LENGTH_LONG).show();
    }



}
