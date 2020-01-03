package pw.elka.mobiasystent.modelcontroller

import android.app.Activity
import android.content.res.Resources
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient


import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import pw.elka.mobiasystent.R

import pw.elka.mobiasystent.model.User

class AuthController {
    private var ownerActivity: Activity;
    private var auth: FirebaseAuth
    private var user: FirebaseUser?
    private var flag = false;
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions


    constructor(ownerActivity: Activity) {
        this.ownerActivity = ownerActivity;
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser;

        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Resources.getSystem().getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(ownerActivity, mGoogleSignInOptions)

    }

    /**
     * Logowanie do firebase(standardowe poświadczenia)
     */
    fun signIn(email: String, password: String, callback: (result: Boolean) -> Unit): Unit {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(ownerActivity) { task ->
                if (task.isSuccessful) {
                    user = auth.currentUser;
                    callback(true);
                } else {
                    callback(false);
                }
            }
    }

    /**
     * Rejestracja do firebase z wykorzystaniem maila i hasła
     */
    fun signUp(
        email: String,
        password: String,
        newUser: User,
        callback: (result: Boolean) -> Unit
    ): Boolean {
        var status = false
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(ownerActivity) { task ->
                if (task.isSuccessful) {
                    user = auth.currentUser

                    callback(true);
                } else {
                    callback(false);
                }
            }
        return status;
    }
}