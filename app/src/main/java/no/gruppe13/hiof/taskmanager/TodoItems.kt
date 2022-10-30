package no.gruppe13.hiof.taskmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import no.gruppe13.hiof.taskmanager.databinding.TodoItemsBinding

class TodoItems : AppCompatActivity() {

    private lateinit var binding: TodoItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.todo_items)

        binding = TodoItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addTaskButton.setOnClickListener {
            val context = binding.addTaskButton.context
            val intent = Intent(context, CreateTaskActivity::class.java)
            context.startActivity(intent)
        }


    }

}