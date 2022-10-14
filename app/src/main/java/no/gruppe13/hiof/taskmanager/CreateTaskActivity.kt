package no.gruppe13.hiof.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import no.gruppe13.hiof.taskmanager.databinding.ActivityCreateTaskBinding
import no.gruppe13.hiof.taskmanager.databinding.ActivityMainBinding

class CreateTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .setFragmentResultListener(DatePickerFragment.REQUEST_KEY, this) { requestKey, bundle ->
                // We use a String here, but any type that can be put in a Bundle is supported
                val result = bundle.getString(DatePickerFragment.FORMATTED_DATE_KEY)
                binding.dateInput.setText(result)
                // Do something with the result
            }
    }

    fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }
}