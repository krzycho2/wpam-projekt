package pw.elka.mobiasystent.modelcontroller

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthController {
    private var ownerActivity: Activity;
    private var auth: FirebaseAuth
    private var user: FirebaseUser?

    constructor(ownerActivity: Activity) {
        this.ownerActivity = ownerActivity;
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser;
    }

    /**
     * Logowanie do firebase(standardowe poświadczenia)
     */
    fun signin(email: String, password: String): Boolean {
        var status = false
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(ownerActivity) { task ->
                if (task.isSuccessful) {
                    user = auth.currentUser
                    status = true
                } else {
                    status = false
                }
            }
        return status;
    }

    /**
     * Rejestracja do firebase z wykorzystaniem maila i hasła
     */
    fun signUp(email: String, password: String): Boolean {
        var status = false
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(ownerActivity) { task ->
                if (task.isSuccessful) {
                    user = auth.currentUser
                    status = true
                } else {
                    status = false
                }
            }
        return status;
    }
}