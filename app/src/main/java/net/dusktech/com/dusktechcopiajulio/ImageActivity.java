package net.dusktech.com.dusktechcopiajulio;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ImageActivity extends AppCompatActivity {


    Intent intent = getIntent();

    private ArrayActivity mQuestionLibrary = new ArrayActivity();

    private TextView mScoreView, mQuestionView;
    private ImageView mImageView;

    private ImageButton mImageButton;

    private Button mButtonChoice1, mButtonChoice2, mButtonChoice3, mButtonChoice4;

    private String mAnswer;

    private int mScore =  0;

    private int mScoreUnlockable;

    int mQuestionNumber = 0;

    private int lessonNumberUnlock, tempLessonNumberUnlock;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;

    boolean isImageFitToScreen;

    //Manejo del HighScore
    private int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mScoreView = (TextView)findViewById(R.id.questionNumberTextView);
        mQuestionView = (TextView)findViewById(R.id.questionTextView);

        mImageView = (ImageView)findViewById(R.id.imageView);

        mImageButton = (ImageButton)findViewById(R.id.imageButton) ;

        mButtonChoice1 = (Button)findViewById(R.id.button);
        mButtonChoice2 = (Button)findViewById(R.id.button2);
        mButtonChoice3 = (Button)findViewById(R.id.button3);
        mButtonChoice4 = (Button)findViewById(R.id.button4);

        Intent intent = getIntent();

        //Recibe las variables de las diferentes actividades
        mScoreUnlockable = intent.getIntExtra("passedScoreUnlockable",0); //Aka Highscore
        mQuestionNumber = intent.getIntExtra("passedQuestionQuiz",0);
        mScore = intent.getIntExtra("passedScoreQuiz",0);
        lessonNumberUnlock = intent.getIntExtra("passedLessonNumberUnlockable", 0);
        tempLessonNumberUnlock = intent.getIntExtra("passedLessonNumberUnlockableQuiz", 0);

        if(mScoreUnlockable > mScore){
            mScore = mScoreUnlockable;
        }

        if(tempLessonNumberUnlock > lessonNumberUnlock){
            lessonNumberUnlock = tempLessonNumberUnlock;
        }

        updateScore(mScore);
        updateQuestion(); //Setea los views a [0] y aumenta QuestionNumber para QuizActivity


        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                mImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isImageFitToScreen) {
                            isImageFitToScreen=false;
                            mImageButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            mImageButton.setAdjustViewBounds(true);
                        }else{
                            isImageFitToScreen=true;
                            mImageButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            mImageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                    }
                });

                if (mButtonChoice1.getText() == mAnswer){
                    mScore = mScore + 1;
                    updateScore(mScore);
                    Toast.makeText(ImageActivity.this, "Correct", Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(ImageActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(!check()){

                    launchActivity();
                }
            }
        });

        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice2.getText() == mAnswer){
                    mScore = mScore + 1;
                    updateScore(mScore);
                    Toast.makeText(ImageActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ImageActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(!check()){
                    launchActivity();
                }

            }
        });

        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice3.getText() == mAnswer){
                    mScore = mScore + 1;
                    updateScore(mScore);
                    Toast.makeText(ImageActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ImageActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(!check()){
                    launchActivity();
                }
            }
        });

        mButtonChoice4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice4.getText() == mAnswer){
                    mScore = mScore + 1;
                    updateScore(mScore);
                    Toast.makeText(ImageActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(ImageActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(!check()){
                    launchActivity();
                }
            }
        });
    }

    private boolean check() {
        boolean op = false;
        if (mQuestionNumber == (mQuestionLibrary.mQuestions.length)) {
            op = true;
            end();
        }
        return op;
    }

    void updateQuestion(){

        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
        mButtonChoice4.setText(mQuestionLibrary.getChoice4(mQuestionNumber));

        mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
        mQuestionNumber++;
        /*if (mQuestionNumber >= mQuestionLibrary.mQuestions.length-1){
            gameOver();
        }*/
    }

    void updateScore(int point) {
        mScoreView.setText("Score: " + point);
    }

    public void updateImage(){

        Drawable test;
        switch (mQuestionNumber-1){
            case 1:
                test = getResources().getDrawable(R.drawable.ico_android);
                mImageView.setImageDrawable(test);
                mImageButton.setImageDrawable(test);
                break;
            case 2:
                test = getResources().getDrawable(R.drawable.dusk);
                mImageView.setImageDrawable(test);
                mImageButton.setImageDrawable(test);
                break;
            case 3:
                test = getResources().getDrawable(R.drawable.ico_android);
                mImageView.setImageDrawable(test);
                mImageButton.setImageDrawable(test);
                break;
            case 4:
                test = getResources().getDrawable(R.drawable.dusk                                                                                                      );
                mImageView.setImageDrawable(test);
                mImageButton.setImageDrawable(test);
                break;
            case 5:
                test = getResources().getDrawable(R.drawable.ico_android);
                mImageView.setImageDrawable(test);
                mImageButton.setImageDrawable(test);
                break;
        }
    }

    private void launchActivity() {
        Intent intent = new Intent(ImageActivity.this, QuizActivity.class);
        intent.putExtra("passedQuestionImage", mQuestionNumber);
        intent.putExtra("passedScoreImage", mScore);
        intent.putExtra("passedLessonNumberUnlockableImage", lessonNumberUnlock);
        startActivity(intent);
    }
    private void end(){
        contador++;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ImageActivity.this);
        alertDialogBuilder
                .setMessage("You've reached " + mQuestionNumber + " questions. Sorry!")
                .setCancelable(false)
                .setPositiveButton("New Game",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ImageActivity.this, StartMainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ImageActivity.this, UnlockableActivity.class);
                                intent.putExtra("passedScoreImage", mScore);
                                intent.putExtra("passedLessonNumberUnlockable", lessonNumberUnlock);
                                intent.putExtra("passedContadorImage", contador);
                                startActivity(intent);
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private boolean contador(){
        boolean resp = false;
        if (contador == 1){
            resp = true;
        }
        return resp;
    }

}


