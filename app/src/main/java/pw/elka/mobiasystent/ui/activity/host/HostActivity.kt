package pw.elka.mobiasystent.ui.activity.host

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.material.bottomnavigation.BottomNavigationView

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import pw.elka.mobiasystent.model.UserModel
import pw.elka.mobiasystent.utils.FirestoreUtil
import pw.elka.mobiasystent.utils.MyApplication
import pw.elka.mobiasystent.utils.Prefs
import kotlinx.android.synthetic.main.activity_host.*
import pw.elka.mobiasystent.R
import pw.elka.mobiasystent.databinding.DrawerHeaderLayoutBinding
import pw.elka.mobiasystent.ui.fragment.AssignedPersonFragment
import pw.elka.mobiasystent.ui.fragment.CalendarFragment
import pw.elka.mobiasystent.ui.fragment.home.HomeFragment

class HostActivity : AppCompatActivity() {
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var navController: NavController
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navViewBinding: DrawerHeaderLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        val toolbar = customToolbar
        val bb = bottomBar

        setSupportActionBar(toolbar)

        if (mAuth.currentUser != null) {getUserData()}

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        drawerLayout = drawer_layout
        navViewBinding = DrawerHeaderLayoutBinding.inflate(layoutInflater, navView, true)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHost.navController
        val navInflater = navController.navInflater

        val graph = navInflater.inflate(R.navigation.main_graph)

        val navBar: BottomNavigationView = findViewById(R.id.bottomBar)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.homeFragment, R.id.calendarFragment, R.id.assignedPersonFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (
                destination.id == R.id.onBoarding ||
                destination.id == R.id.authFragment ||
                destination.id == R.id.loginFragment ||
                destination.id == R.id.signUpFragment
            ) {
                toolbar.visibility = View.GONE
                bb.visibility = View.GONE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            } else {
                bb.visibility = View.VISIBLE
                toolbar.visibility = View.VISIBLE
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
        if (!Prefs.getInstance(this)!!.hasCompletedWalkthrough!!) {
            if (mAuth.currentUser == null) {
                graph.startDestination = R.id.authFragment
            } else {
                getUserData()
                graph.startDestination = R.id.homeFragment
            }
        } else {
            graph.startDestination = R.id.onBoarding

        }
        navController.graph = graph

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener {
            it.isChecked
            drawerLayout.closeDrawers()
            when (it.itemId) {
                R.id.action_logout -> {
                    MyApplication.currentUser!!.active = false
                    FirestoreUtil.updateUser(MyApplication.currentUser!!) {
                        mAuth.signOut()
                    }
                    googleSignInClient.signOut()
                    MyApplication.currentUser = null
                    navController.navigate(R.id.action_logout)
                }
                R.id.action_settings -> {
                    navController.navigate(R.id.settingsFragment)
                    getSupportActionBar()!!.setTitle("Ustawienia")
                }
            }
            true
        }

    }

    private fun getUserData() {

        val ref = db.collection("users").document(mAuth.currentUser!!.email!!)

        ref.get().addOnSuccessListener {
            val userInfo = it.toObject(UserModel::class.java)
            navViewBinding.user = userInfo
            MyApplication.currentUser = userInfo
            Log.d("DUPA", "dodano usera");
            Log.d("DUPA", userInfo.toString());

            MyApplication.currentUser?.active = true
            FirestoreUtil.updateUser(MyApplication.currentUser!!) {
            }
        }.addOnFailureListener {
            val intent = Intent(this, MyApplication::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }



}