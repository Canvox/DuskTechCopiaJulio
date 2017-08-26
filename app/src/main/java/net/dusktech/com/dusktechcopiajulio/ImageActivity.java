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

    //Reinicia el valor de TempScore
    private int mTempScore = 0;

    int mQuestionNumber = 0;

    private int lessonNumberUnlock, tempLessonNumberUnlock;

    private Animator mCurrentAnimator;

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
        //Recibe score de UnlockableActivity
        mScoreUnlockable = intent.getIntExtra("passedTotalScoreUnlockable",0);

        //Recibe numero de pregunta de QuizActivity
        mQuestionNumber = intent.getIntExtra("passedQuestionQuiz",0); //(..,0)setea 0 en caso de no encontrar valor

        //Recibe el score de QuizActivity
        mScore = intent.getIntExtra("passedScoreQuiz",0);

        //Recibe score temporal de QuizActivity
        mTempScore = intent.getIntExtra("passedTempScoreQuiz",0);

        //Recibe lessonNumber de UnlockableActivity
        lessonNumberUnlock = intent.getIntExtra("passedLessonNumberUnlockable", 0);

        tempLessonNumberUnlock = intent.getIntExtra("passedLessonNumberUnlockableQuiz", 0);

        if(mScoreUnlockable > mScore){
            mScore = mScoreUnlockable;
        }

        if(tempLessonNumberUnlock > lessonNumberUnlock){
            lessonNumberUnlock = tempLessonNumberUnlock;
        }

        //Setea los views a [0] y aumenta QuestionNumber para QuizActivity
        updateScore(mScore);
        updateQuestion();

        final View thumb1View = findViewById(R.id.imageButton);
        thumb1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumb1View, R.drawable.dusk);
            }
        });

        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);




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

                    mTempScore = mTempScore + 1;
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

                    mTempScore = mTempScore + 1;

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

                    mTempScore = mTempScore + 1;

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

                    mTempScore = mTempScore + 1;

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

    private void win(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ImageActivity.this);
        alertDialogBuilder
                .setMessage("Victory! Your score is " + mScore + "points")
                .setCancelable(false)
                .setPositiveButton("New Game",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(),ImageActivity.class));
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

    }

    private void gameOver(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ImageActivity.this);
        alertDialogBuilder
                .setMessage("Game Over! Your score is " + mScore + "points" + (mQuestionLibrary.mQuestions.length-1))
                .setCancelable(false)
                .setPositiveButton("New Game",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getApplicationContext(),ImageActivity.class));
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

    }

    private void zoomImageFromThumb(final View thumbView, int imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.imageView);
        expandedImageView.setImageResource(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.quizLinearLayout)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

    //TODO: Copiar Metodo en QuizActivity
    private void launchActivity() {
        Intent intent = new Intent(ImageActivity.this, QuizActivity.class);
        //Envia valor a QuizActivity
        intent.putExtra("passedQuestionImage", mQuestionNumber);
        intent.putExtra("passedScoreImage", mScore);
        //Pasa el tempScore a QuizActivity
        intent.putExtra("passedTempScoreImage", mTempScore);
        intent.putExtra("passedLessonNumberUnlockableImage", lessonNumberUnlock);
        startActivity(intent);
    }

    //Metodo para cuando se acaben preguntas
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
                                intent.putExtra("passedScoreImage", mScore);
                                intent.putExtra("passedContador", contador);
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
                                intent.putExtra("passedTempScoreImage", mTempScore);
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


