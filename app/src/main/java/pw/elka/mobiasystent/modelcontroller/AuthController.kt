package pw.elka.mobiasystent.modelcontroller

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthController {
    private var ownerActivity: Activity;
    private var auth: FirebaseAuth
    private var user: FirebaseUser?
    private var flag = false;

    constructor(ownerActivity: Activity) {
        this.ownerActivity = ownerActivity;
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser;

    }

    /**
     * Logowanie do firebase(standardowe poświadczenia)
     */
    fun signIn(email: String, password: String, callback: (result:Boolean)-> Unit): Unit {
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
    fun signUp(email: String, password: String, callback: (result:Boolean)-> Unit): Boolean {
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