package no.gruppe13.hiof.taskmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
                val result = bundle.getString(DatePickerFragment.FORMATTED_DATE_KEY)
                binding.dateInput.setText(result)
            }

        supportFragmentManager
            .setFragmentResultListener(TimePickerFragment.REQUEST_KEY, this) { requestKey, bundle ->
                val result = bundle.getString(TimePickerFragment.FORMATTED_TIME_KEY)
                binding.timeInput.setText(result)
            }

        binding.createTaskButton.setOnClickListener {
            val context = binding.createTaskButton.context
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            val toast = Toast.makeText(this, "Gjøremål opprettet!", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun showTimePickerDialog(v: View) {
        val newFragment = TimePickerFragment()
        newFragment.show(supportFragmentManager, "timePicker")
    }
}