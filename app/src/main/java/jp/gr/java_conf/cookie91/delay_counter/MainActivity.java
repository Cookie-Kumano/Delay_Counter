package jp.gr.java_conf.cookie91.delay_counter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.media.RatingCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    Button delay;
    Button cutting;
    Button absence;
    Button details;
    Button share;

    TextView delayView;
    TextView cuttingView;
    TextView absenceView;
    TextView leaveView;

    int delayCount = 0;
    int cuttingCount = 0;
    int absenceCount = 0;
    int leaveCount = 0;
    int delayLimit = 20;
    int cuttingLimit = 40;
    int absenceLimit = 40;

    String situation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        details = (Button) findViewById(R.id.details);
        share = (Button) findViewById(R.id.share);

        delayView = (TextView) findViewById(R.id.delayView);
        cuttingView = (TextView) findViewById(R.id.cuttingView);
        absenceView = (TextView) findViewById(R.id.absenceView);
        leaveView = (TextView) findViewById(R.id.leaveView);

        loadSettings(getApplicationContext());

    }
    protected void onResume() {
        super.onResume();
        loadSettings(getApplicationContext());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                final String[] items = {"遅刻", "欠課", "欠席", "早退"};
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("今日はどうなさいましたの？")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0)
                                    situation = "遅刻";
                                if (which == 1)
                                    situation = "欠課";
                                if (which == 2)
                                    situation = "欠席";
                                if (which == 3)
                                    situation = "早退";
                                aDialog();
                            }
                        })
                        .show();
                break;

            case R.id.share:
                int dlCount = delayCount + leaveCount;
                String sharedText = "私の遅刻･早退回数は"+ dlCount + "回/" + delayLimit + "回\n" +
                        "私の欠課回数は" + cuttingCount + "回/" + cuttingLimit + "回\n" +
                        "私の欠席回数は" + absenceCount + "回/" + absenceLimit + "回\n" +
                        "です。 \n \n この出席状況は くっきー が開発したカウントアプリの算出結果に基づいています。\n"+
                        " #遅刻カウンタ";

                // builderの生成　ShareCompat.IntentBuilder.from(Context context);
                ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(MainActivity.this);

                // アプリ一覧が表示されるDialogのタイトルの設定
                builder.setChooserTitle("出席状況を晒すアプリを選択");

                builder.setText(sharedText);

                builder.setType("text/plain");

                // Shareアプリ一覧のDialogの表示
                builder.startChooser();
                break;

            case R.id.settings:
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
                break;

            case R.id.details:

                View view = MainActivity.this.getLayoutInflater().inflate(R.layout.details_dialog, null);
                AlertDialog.Builder alr = new AlertDialog.Builder(MainActivity.this);

                TextView delayView_D = (TextView) view.findViewById(R.id.delayView_D);
                delayView_D.setText(String.valueOf(delayCount + leaveCount));

                TextView delayLimitView = (TextView) view.findViewById(R.id.delayLimitView);
                delayLimitView.setText(String.valueOf(delayLimit));

                TextView cuttingView_D = (TextView) view.findViewById(R.id.cuttingView_D);
                cuttingView_D.setText(String.valueOf(cuttingCount));

                TextView cuttingLimitView = (TextView) view.findViewById(R.id.cuttingLimitView);
                cuttingLimitView.setText(String.valueOf(cuttingLimit));

                TextView absenceView_D = (TextView) view.findViewById(R.id.absenceView_D);
                absenceView_D.setText(String.valueOf(absenceCount));

                TextView absenceLimitView = (TextView) view.findViewById(R.id.absenceLimitView);
                absenceLimitView.setText(String.valueOf(absenceLimit));

                TextView absenceSView = (TextView) view.findViewById(R.id.absenceSView);
                absenceSView.setText(String.valueOf(absenceCount));

                String del = String.valueOf(delayCount);
                String lea = String.valueOf(leaveCount);
                String cut = String.valueOf(cuttingCount);
                BigDecimal bd = new BigDecimal(del);
                BigDecimal bd1 = new BigDecimal(cut);
                BigDecimal bd2 = new BigDecimal(lea);
                BigDecimal bd3 = new BigDecimal("0.5");
                BigDecimal cus = bd1.add((bd.add(bd2)).multiply(bd3));
                BigDecimal result = cus.setScale(0, BigDecimal.ROUND_HALF_UP);

                TextView cuttingSView = (TextView) view.findViewById(R.id.cuttingSView);
                cuttingSView.setText(String.valueOf(result));

                alr.setView(view)
                        .show();

                break;
        }
    }

    private void changeText() {
        delayView.setText(String.valueOf(delayCount));
        cuttingView.setText(String.valueOf(cuttingCount));
        absenceView.setText(String.valueOf(absenceCount));
        leaveView.setText(String.valueOf(leaveCount));
        //TODO: 遅刻・欠課・欠席・早退回数により上部メッセージ変更を実装。
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

        Toast.makeText(MainActivity.this, "出席状況を適用しましたっ！\n" + "遅刻: " + delayCount + "\n欠課: " + cuttingCount + "\n欠席: " + absenceCount + "\n早退: " + leaveCount, Toast.LENGTH_LONG).show();
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

        changeText();

        Toast.makeText(MainActivity.this, "出席状況をロードしましたっ！\n" + "遅刻: " + delayCount + "\n欠課: " + cuttingCount + "\n欠席: " + absenceCount + "\n早退: " + leaveCount, Toast.LENGTH_SHORT).show();
    }

    private void aDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("「" + situation + "」 の内容で登録します。\nよろしくて？")
                .setCancelable(false)
                .setPositiveButton("登録", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (situation.equals("遅刻")) {
                            delayCount++;
                        } else if (situation.equals("欠課")) {
                            cuttingCount++;
                        } else if (situation.equals("欠席")) {
                            absenceCount++;
                        } else if (situation.equals("早退")) {
                            leaveCount++;
                        }

                        changeText();
                        saveSettings(getApplicationContext(), delayCount, cuttingCount, absenceCount, leaveCount, delayLimit, cuttingLimit, absenceLimit);
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
}
