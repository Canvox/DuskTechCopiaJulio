package net.dusktech.com.dusktechcopiajulio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class UnlockableActivity extends AppCompatActivity {

    private ImageButton lesson1Button, lesson2Button, lesson3Button, lesson4Button;

    private TextView mScoreView, mHighScoreView;

    private Button backButton;

    private int mscoreUnlockable, mscoreMain, mTempScoreUnlockable, mContadorUnlockable;

    private int lessonNumber, tempLessonNumber;

    //Creo que esto no se va a usar
    private int mScoreFirstLesson;
    private int mScoreSecondLesson;
    private int mScoreThirdLesson;
    private int mScoreFourthLesson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlockable);

        lesson1Button = (ImageButton) findViewById(R.id.lesson1);
        lesson2Button = (ImageButton) findViewById(R.id.lesson2);
        lesson3Button = (ImageButton) findViewById(R.id.lesson3);
        lesson4Button = (ImageButton) findViewById(R.id.lesson4);

        mScoreView = (TextView) findViewById(R.id.scoreView);
        mHighScoreView = (TextView) findViewById(R.id.highScoreView);

        backButton = (Button) findViewById(R.id.back);

        /* Ahora el score se va a guardar por leccion y se le envia un score total a StartMainActivity */
        //Intent intent = getIntent();
        //mscoreUnlockable = intent.getIntExtra("passedScoreMain",0);

        lesson1Button.setImageResource(R.drawable.unlockedlesson);

        Intent intent = getIntent();
        mscoreMain = intent.getIntExtra("passedScoreMain", 0);

        mscoreUnlockable = intent.getIntExtra("passedScoreImage",0);

        mTempScoreUnlockable = intent.getIntExtra("passedTempScoreImage", 0);

        lessonNumber = intent.getIntExtra("passedLessonNumberUnlockable", 0);

        mContadorUnlockable = intent.getIntExtra("passedContadorImage",0);


        if (mscoreMain > mscoreUnlockable){
            mHighScoreView.setText("Highscore: " + mscoreMain);
        }else{
            mHighScoreView.setText("Highscore: " + mscoreUnlockable);
        }


        mScoreView.setText("Score: " + mTempScoreUnlockable);

        /*Test para probar el cambio de imagenes (Si funciona)
        if (mscoreUnlockable >= 4){
            lesson1Button.setImageResource(R.drawable.unlockedlesson);
        } else {
            lesson1Button.setImageResource(R.drawable.locklesson);
        }*/

        lesson1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*startActivity(new Intent(getApplicationContext(), ImageActivity.class));*/
                //Solucion temporal (deberia ubicar el numero de leccion en algun lado donde sea mas generico)
                /*Intent intent = new Intent(UnlockableActivity.this, ImageActivity.class);
                if (mscoreMain > mscoreUnlockable){
                    intent.putExtra("passedTotalScoreUnlockable", mscoreMain);
                    startActivity(intent);
                }else{
                    intent.putExtra("passedTotalScoreUnlockable", mscoreUnlockable);
                    startActivity(intent);
                }
                intent.putExtra("passedTempScoreUnlockable", mTempScoreUnlockable);*/
                lessonNumber = 1;
                launchActivity();
            }
        });

        lesson2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*startActivity(new Intent(getApplicationContext(), ImageActivity.class));*/
                Intent intent = new Intent(UnlockableActivity.this, ImageActivity.class);
                intent.putExtra("passedSecondLesson", mScoreSecondLesson);
                startActivity(intent);
            }
        });

        lesson3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*startActivity(new Intent(getApplicationContext(), ImageActivity.class));*/
                Intent intent = new Intent(UnlockableActivity.this, ImageActivity.class);
                intent.putExtra("passedThirdLesson", mScoreThirdLesson);
                startActivity(intent);
            }
        });

        lesson4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*startActivity(new Intent(getApplicationContext(), ImageActivity.class));*/
                Intent intent = new Intent(UnlockableActivity.this, ImageActivity.class);
                intent.putExtra("passedFourthLesson", mScoreFourthLesson);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lessonNumber++;
                Intent intent = new Intent(UnlockableActivity.this, StartMainActivity.class);
                intent.putExtra("passedTotalScoreUnlockable", mscoreUnlockable);
                intent.putExtra("passedTempScoreUnlockable", mTempScoreUnlockable);
                intent.putExtra("passedLessonNumberUnlockable", lessonNumber);
                startActivity(intent);
            }
        });

    }

    public void launchActivity(){
        Intent intent = new Intent(UnlockableActivity.this, ImageActivity.class);
        if (mscoreMain > mscoreUnlockable){
            intent.putExtra("passedTotalScoreUnlockable", mscoreMain);
        }else{
            intent.putExtra("passedTotalScoreUnlockable", mscoreUnlockable);
        }
        intent.putExtra("passedTempScoreUnlockable", mTempScoreUnlockable);
        //Igual hay que enviarselo a Image porque la Activity se reinicia
        intent.putExtra("passedLessonNumberUnlockable", lessonNumber);
        intent.putExtra("passedContadorUnlockable", mContadorUnlockable);
        startActivity(intent);

    }
}
