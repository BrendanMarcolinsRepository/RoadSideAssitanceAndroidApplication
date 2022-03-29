package com.example.roadsideassistapplication

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthSession
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify

class Login : AppCompatActivity() {


    private var logoImageView : ImageView? = null;
    private var emailEditText : EditText? = null;
    private var passwordEditText : EditText? = null;
    private var loginButton : Button? = null;
    private var emailString : String? = null;
    private var passwordString : String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Add this line, to include the Auth plugin.
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        Amplify.configure(applicationContext)


        try {
            Amplify.configure(applicationContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }

        logoImageView = findViewById(R.id.loginPageImageView);
        emailEditText = findViewById(R.id.loginEmailEditext);
        passwordEditText = findViewById(R.id.loginPasswordEditext);
        loginButton = findViewById(R.id.loginButton);

        loginButton?.setOnClickListener(loginAccount())


        Amplify.Auth.fetchAuthSession(
            { Log.i("AmplifyQuickstart", "Auth session = $it") },
            { error -> Log.e("AmplifyQuickstart", "Failed to fetch auth session", error) }
        )



    }

    private fun loginAccount() = View.OnClickListener{

        loginMethod();
    }

    private fun loginMethod() {

        emailString = emailEditText?.text.toString();
        passwordString = passwordEditText?.text.toString();


        if(!TextUtils.isEmpty(emailEditText?.text.toString())) {
            emailString = emailEditText?.text.toString();
        } else { return; }

        if(!TextUtils.isEmpty(passwordEditText?.text.toString())) {
            passwordString = passwordEditText?.text.toString();
        } else { return; }


        Amplify.Auth.signIn(emailString, passwordString,
            { result ->
                if (result.isSignInComplete) {
                    Log.i("AuthQuickstart", "Sign in succeeded")
                } else {
                    Log.i("AuthQuickstart", "Sign in not complete")
                }
            },
            { Log.e("AuthQuickstart", "Failed to sign in", it) }
        )



    }




}
