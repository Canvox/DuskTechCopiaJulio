package net.dusktech.com.dusktechcopiajulio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StartMainActivity extends AppCompatActivity {

    String tag = "Lifecycle";

    private ArrayList scores;

    private Button playButton;
    private SignInButton mGoogleButton;
    private Button mLogoutButton;

    private TextView mScoreView, mTempScoreView;

    private int testScore, totalScore, lessonNumberMain;

    private int realScore;

    public int contadorScore;

    private static final int RC_SIGN_IN = 1;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;

    private Button buttonSave;
    //private DatabaseReference mConditionRef = mDatabase.child("Score");


    private static final String TAG = "START_MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_main);

        Log.d(tag, "In the onCreate() event");

        playButton = (Button) findViewById(R.id.play);

        mScoreView = (TextView)findViewById(R.id.score);

        mTempScoreView = (TextView)findViewById(R.id.tempScoreView);

        buttonSave = (Button) findViewById(R.id.saveButton);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        Intent intent = getIntent();

        //contadorScore = intent.getIntExtra("contadorScore",0);

        //getScoreFirebase();
        //Aqui puede estar el problema porque lo podria meter en el metodo de la base de datos
        testScore = intent.getIntExtra("passedTotalScoreUnlockable",0);
        contadorScore = intent.getIntExtra("passedTotalScoreUnlockable",0);
        //totalScore = testScore + getScoreFirebase();
        System.out.println("RealScore---------------" + String.valueOf(realScore));
        System.out.println("TestScore***************" + String.valueOf(testScore));
        System.out.println("TotalScore---------------" + String.valueOf(totalScore));

        lessonNumberMain = intent.getIntExtra("passedLessonNumberUnlockable", 0);

        mGoogleButton = (SignInButton) findViewById(R.id.googleButton);
        mLogoutButton = (Button) findViewById(R.id.logoutButton);



        //Arreglar este metodo

        //SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        //contadorScore = sharedPreferences.getInt("contador",0);

        System.out.println("---------------Contador:" + String.valueOf(contadorScore));

        //contadorScore = ((AndroidApplication) getApplicationContext()).contador;

        System.out.println("++++++++++++++Contador:" + String.valueOf(contadorScore));

        getScoreFirebase();


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(StartMainActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Github test

        mGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*startActivity(new Intent(getApplicationContext(), ImageActivity.class));*/
                Intent intent = new Intent(StartMainActivity.this, UnlockableActivity.class);
                intent.putExtra("passedHighscoreMain", testScore);
                //saveUserInformation();
                startActivity(intent);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveUserInformation();
                //contadorScore = ((AndroidApplication) getApplicationContext()).contador;
                //saveInfo(contadorScore);

                //Intent intent = new Intent(StartMainActivity.this, AndroidApplication.class);
                //intent.putExtra("contadorScore", contadorScore);
                //updateTempScore(testScore);

            }
        });
    }

    public void onStart(){
        super.onStart();
        Log.d(tag, "In the onStart() event");
    }

    public void onRestart(){
        super.onRestart();
        Log.d(tag, "In the onRestart() event");
    }

    public void onResume(){
        super.onResume();
        Log.d(tag, "In the onResume() event");
    }

    public void onPause(){
        super.onPause();
        Log.d(tag, "In the onPause() event");
    }public void onStop(){
        super.onStop();
        Log.d(tag, "In the onStop() event");
    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(tag, "In the onDestroy() event");
    }




    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {

        //Reinicia el Score y TempScore para el Usuario
        testScore = 0;
        updateScore(testScore);
        updateTempScore(testScore);



        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(StartMainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    private void updateScore(int value) {
        mScoreView.setText("" + value);
    }

    private void updateTempScore(int value){
        mTempScoreView.setText("" + value);
    }

    private void saveUserInformation() {
        //Getting values from database

        //String score = Integer.toString(testScore);

        ArrayList scores = new ArrayList();
        scores.add(Integer.toString(totalScore));
        scores.add("Score: " + Integer.toString(testScore));

        ArrayList informacion = new ArrayList();
        informacion.add("# leccion: " + Integer.toString(lessonNumberMain));


        //Creating a UserInformation object
        //Constructor tiene dos Arraylist
        UserInformation userInformation = new UserInformation(scores, informacion);

        //getting the current logged in user
        FirebaseUser user = mAuth.getCurrentUser();

        //saving data to firebase database
        /*
        * first we are creating a new child in firebase with the
        * unique id of logged in user
        * and then for that user under the unique id we are saving data
        * for saving data we are using setvalue method this method takes a normal java object
        * */
        mDatabase.child(user.getUid()).setValue(userInformation);

        //displaying a success toast
        Toast.makeText(this, "Information Saved...", Toast.LENGTH_LONG).show();
    }

    public void getScoreFirebase(){


            mDatabase = FirebaseDatabase.getInstance().getReference();

            mAuth = FirebaseAuth.getInstance();

            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        // User is signed in
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    } else {
                        // User is signed out
                        Log.d(TAG, "onAuthStateChanged:signed_out");
                    }
                    // ...
                }
            };

            FirebaseUser user = mAuth.getCurrentUser();

            mDatabase.child(user.getUid()).child("scores").child("0").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //System.out.println("-----------------" + snapshot.getValue());
                    realScore = Integer.valueOf(snapshot.getValue().toString());
                    System.out.println("RealScore***************" + String.valueOf(realScore));
                    totalScore = testScore + realScore;
                    updateScore(totalScore);

                    ArrayList scores = new ArrayList();

                    scores.add(Integer.toString(totalScore));
                    scores.add("Score: " + Integer.toString(contadorScore));

                    ArrayList informacion = new ArrayList();
                    informacion.add("# leccion: " + Integer.toString(lessonNumberMain));


                    UserInformation userInformation = new UserInformation(scores, informacion);
                    FirebaseUser user = mAuth.getCurrentUser();


                    mDatabase.child(user.getUid()).setValue(userInformation);


                    testScore = 0;
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });


        }

        public void saveInfo(int x){
            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("contador",x);
            editor.apply();
        }

}


