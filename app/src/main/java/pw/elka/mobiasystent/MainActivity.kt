package pw.elka.mobiasystent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import pw.elka.mobiasystent.modelcontroller.AuthController
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {
    private val TAG = "MyActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth: AuthController = AuthController(this)
        auth.signIn("janekjak@gmail.com","dupa123",{result -> Log.v(TAG, "Czy udalo sie zalogować: " + result); }) 
    }
}
