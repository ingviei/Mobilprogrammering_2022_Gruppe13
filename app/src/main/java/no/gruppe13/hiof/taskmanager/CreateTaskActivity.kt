package no.gruppe13.hiof.taskmanager


import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import no.gruppe13.hiof.taskmanager.adapter.CreateTaskCategorySpinnerAdapter
import no.gruppe13.hiof.taskmanager.data.Task
import no.gruppe13.hiof.taskmanager.data.TaskDatabase
import no.gruppe13.hiof.taskmanager.data.category.Category
import no.gruppe13.hiof.taskmanager.databinding.ActivityCreateTaskBinding
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModel

class CreateTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ToDo: Sjekk om dette er det beste stedet å deklarere viewmodel
        val viewModel =
            TaskManagerViewModel(
                (this.application as TaskManagerApplication).database.taskDao(),
                (this.application as TaskManagerApplication).database.categoryDao()
            )

        val chooseCategorySpinner: Spinner = binding.pickCategorySpinner
        val spinnerAdapter = CreateTaskCategorySpinnerAdapter(this, R.layout.simple_spinner_item, ArrayList<Category>())
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        chooseCategorySpinner.adapter = spinnerAdapter

        GlobalScope.launch(Dispatchers.IO) {
            lifecycle.coroutineScope.launch {
                viewModel.allCategories().collect() {
                    spinnerAdapter.clear()
                    spinnerAdapter.addAll(it)

                }
            }
        }

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
            val intent = Intent(context, TodayFragment::class.java)
            //TODO: intent i linjen over: Byttet ut TodoItems klassen med TodayFragment, siden TodoItems ikke finnes lenger.Må endres så intentet sendes til fragmentet istedet

            context.startActivity(intent)

            val selectedCategory: Category = binding.pickCategorySpinner.selectedItem as Category
            val task = Task(
                binding.taskInput.text.toString(),
                //ToDo: Fix inconsistent data types PK Category vs FK Task->Category
                selectedCategory.id.toLong(),
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

