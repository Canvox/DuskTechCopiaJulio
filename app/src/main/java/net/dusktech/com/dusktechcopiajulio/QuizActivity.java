package net.dusktech.com.dusktechcopiajulio;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private ArrayActivity mQuestionLibrary = new ArrayActivity();

    private TextView mScoreView, mQuestionView;

    private Button mButtonChoice1,mButtonChoice2, mButtonChoice3, mButtonChoice4;


    private String mAnswer;
    private int mScore = 0;

    private int testValue, testScore, tempScore, lessonNumberQuizUnlock;


    //private int mQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
        mButtonChoice4 = (Button)findViewById(R.id.choice4);

        Intent intent = getIntent();
        //Recibe numero de pregunta de ImageActivity
        testValue = intent.getIntExtra("passedQuestionImage",0);
        //Recibe el score de ImageActivity
        testScore = intent.getIntExtra("passedScoreImage",0);
        //Recibe el score temportal de ImageActivity
        tempScore = intent.getIntExtra("passedTempScoreImage", 0);

        lessonNumberQuizUnlock = intent.getIntExtra("passedLessonNumberUnlockableImage", 0);

        //Setea los views a [1] la primera vez
        updateScore(testScore);
        updateQuestion(testValue);


        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (mButtonChoice1.getText() == mAnswer) {
                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    testScore++;
                    tempScore++;
                    updateScore(testScore);

                } else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(check(testValue) == 0){
                    testValue = increment(testValue);
                    launchActivity();
                }
            }
        });

        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice2.getText() == mAnswer){
                    testScore++;
                    tempScore++;
                    updateScore(testScore);

                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(check(testValue) == 0){
                    testValue = increment(testValue);
                    launchActivity();
                }
            }
        });

        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice3.getText() == mAnswer){
                    testScore++;
                    tempScore++;
                    updateScore(testScore);
                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(check(testValue) == 0){
                    testValue = increment(testValue);
                    launchActivity();
                }
            }
        });

        mButtonChoice4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice4.getText() == mAnswer){
                    testScore++;
                    tempScore++;
                    updateScore(testScore);
                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                }
                if(check(testValue) == 0){
                    testValue = increment(testValue);
                    launchActivity();
                }
            }
        });
        //}

    }

    /*private void check(){
        if (mScore == (mQuestionLibrary.mQuestions.length-1)){
            win();
        }else if (mQuestionNumber == 7) {
            gameOver();
        }
    }*/


    private void updateQuestion(int value){
        mQuestionView.setText(mQuestionLibrary.getQuestion(value));
        System.out.println("++++++++++++++++++"+mQuestionLibrary.getQuestion(value));
        mButtonChoice1.setText(mQuestionLibrary.getChoice1(value));
        mButtonChoice2.setText(mQuestionLibrary.getChoice2(value));
        mButtonChoice3.setText(mQuestionLibrary.getChoice3(value));
        mButtonChoice4.setText(mQuestionLibrary.getChoice4(value));

        mAnswer = mQuestionLibrary.getCorrectAnswer(value);
        /*if (mQuestionNumber >= mQuestionLibrary.mQuestions.length-1){
            gameOver();
        }*/
    }

    private void updateScore(int value) {
        mScoreView.setText("" + value);
    }

    /*private void win(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder
                .setMessage("Victory! Your score is " + mScore + "points")
                .setCancelable(false)
                .setPositiveButton("New Game",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(),QuizActivity.class));
                                finish();
                            }
                        })
                .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }*/

    /*private void gameOver(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder
                .setMessage("Game Over! Your score is " + mScore + "points" + (mQuestionLibrary.mQuestions.length-1))
                .setCancelable(false)
                .setPositiveButton("New Game",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(),QuizActivity.class));
                                finish();
                            }
                        })
                .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }*/

    private void launchActivity() {
        Intent intent = new Intent(QuizActivity.this, ImageActivity.class);
        intent.putExtra("passedQuestionQuiz", testValue);
        intent.putExtra("passedScoreQuiz", testScore);
        intent.putExtra("passedTempScoreQuiz", tempScore);
        intent.putExtra("passedLessonNumberUnlockableQuiz", lessonNumberQuizUnlock);
        startActivity(intent);
    }

    private int increment(int value){
        value++;
        return value;
    }

    private int check(int value) {
        int resp = 0;
        if (value == (mQuestionLibrary.mQuestions.length)) {
            resp = 1;
            end();
        }
        return resp;
    }

    //Metodo para cuando se acaben preguntas
    private void end(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuizActivity.this);
        alertDialogBuilder
                .setMessage("You've reached questions. Sorry!")
                .setCancelable(false)
                .setPositiveButton("New Game",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(QuizActivity.this, StartMainActivity.class);
                                intent.putExtra("passedScoreImage", testScore);
                                startActivity(intent);
                                finish();
                                finish();
                            }
                        })
                .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(QuizActivity.this, StartMainActivity.class);
                                intent.putExtra("passedScoreImage", testScore);
                                startActivity(intent);
                                finish();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}


