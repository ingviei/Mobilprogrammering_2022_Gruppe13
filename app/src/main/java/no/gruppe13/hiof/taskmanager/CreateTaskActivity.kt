package no.gruppe13.hiof.taskmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import no.gruppe13.hiof.taskmanager.data.Task
import no.gruppe13.hiof.taskmanager.data.TaskDatabase
import no.gruppe13.hiof.taskmanager.databinding.ActivityCreateTaskBinding
import java.time.LocalDateTime

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
            val intent = Intent(context, TodoItems::class.java)
            context.startActivity(intent)

            val task = Task(
                binding.taskInput.text.toString(),
                1,
                binding.dateInput.text.toString(),
                binding.timeInput.text.toString(),
                binding.commentInput.text.toString(),
                false
            )

            val db = TaskDatabase.getDatabase(this)
            lifecycleScope.launch{
                db.taskDao().insertTask(task)
            }

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