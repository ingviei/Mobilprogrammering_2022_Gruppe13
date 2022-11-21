package no.gruppe13.hiof.taskmanager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels

import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import no.gruppe13.hiof.taskmanager.databinding.ActivityMainBinding
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModel
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButtonAddCategoryButton.setOnClickListener {
            val context = binding.floatingActionButtonAddCategoryButton.context
            val intent = Intent(context, CreateCategoryActivity::class.java)
            context.startActivity(intent)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_calendar
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    }
