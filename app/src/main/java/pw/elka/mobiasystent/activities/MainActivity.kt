package pw.elka.mobiasystent.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import pw.elka.mobiasystent.modelcontroller.AuthController
import pw.elka.mobiasystent.R


class MainActivity : AppCompatActivity() {
    private val TAG = "MyActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth: AuthController = AuthController(this)
        auth.signIn("janekjak@gmail.com","dupa123",{result -> Log.v(TAG, "Czy udalo sie zalogować: " + result); }) 
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
}
