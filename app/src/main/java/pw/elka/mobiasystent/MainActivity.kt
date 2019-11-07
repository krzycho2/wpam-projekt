package pw.elka.mobiasystent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pw.elka.mobiasystent.modelcontroller.AuthController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth: AuthController = AuthController(this)
    }
}
