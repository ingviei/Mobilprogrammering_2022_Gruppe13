package no.gruppe13.hiof.taskmanager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels

import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import no.gruppe13.hiof.taskmanager.databinding.ActivityMainBinding
import no.gruppe13.hiof.taskmanager.ui.home.HomeFragment
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModel
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Fragments on main-site
        val categoryFragment = CategoryFragment()
        val todayFragment = TodayFragment()

        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fl_fragment, categoryFragment)
            commit()
        }

        val todayButton = findViewById<Button>(R.id.btnToday)
        todayButton.setOnClickListener{
            supportFragmentManager.beginTransaction().apply{
                replace(R.id.fl_fragment, todayFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.floatingActionButtonAddCategoryButton.setOnClickListener {
            val context = binding.floatingActionButtonAddCategoryButton.context
            val intent = Intent(context, CreateCategoryActivity::class.java)
            context.startActivity(intent)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_calendar
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        // TodayButton on main site - to be changed to fragment
       /* val todayButton = findViewById<Button>(R.id.btnToday)
        todayButton.setOnClickListener{
            Intent(this,TodoItems::class.java).also{
                startActivity(it)
            }
        }*/



    }
}