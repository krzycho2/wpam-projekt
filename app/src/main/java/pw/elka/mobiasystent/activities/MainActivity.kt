package pw.elka.mobiasystent.activities

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
}
