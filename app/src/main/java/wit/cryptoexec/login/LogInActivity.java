package wit.cryptoexec.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import wit.cryptoexec.OpenOrders.OpenOrders;
import wit.cryptoexec.exchange.ExchangesActivity;
import wit.cryptoexec.R;
import wit.cryptoexec.main.MainActivity;

public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String TAG = "debug";
    private EditText email;
    private EditText password;
    private String emailText;
    private String passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.edit_text_email);
        password = findViewById(R.id.edit_text_password);

        Button signInButton = findViewById(R.id.button_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveEmailAndPassword()) {
                    signIn(emailText, passwordText);
                }
            }
        });

        Button signUpButton = findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveEmailAndPassword()) {
                    createAccount(emailText, passwordText);
                }
            }
        });

//        Button continueButton = findViewById(R.id.button_continue);
//        continueButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                goToMain();
//            }
//        });



    }

    @Override
    public void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update ui accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToMain();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void goToMain() {
     //   Testing sandbox, make sure to comment out actual one when working with "testing" activity
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    public boolean saveEmailAndPassword() {
        emailText = email.getText().toString();
        if(emailText.isEmpty()) {
            email.setFocusableInTouchMode(true);
            email.requestFocus();
            return false;
        }
        passwordText = password.getText().toString();
        if(passwordText.isEmpty()) {
            password.setFocusableInTouchMode(true);
            password.requestFocus();
            return false;
        }
        return true;
    }
}
