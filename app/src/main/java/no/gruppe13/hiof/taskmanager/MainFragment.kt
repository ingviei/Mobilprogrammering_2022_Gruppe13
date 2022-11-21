package no.gruppe13.hiof.taskmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todayButton = view.findViewById<Button>(R.id.btnToday)
        val thisWeekButton = view.findViewById<Button>(R.id.btnThisWeek)

        todayButton.setOnClickListener{
            val navController = it.findNavController()
            navController.navigate(R.id.action_mainFragment2_to_todayFragment2)
        }
        thisWeekButton.setOnClickListener{
            val navController = it.findNavController()
            navController.navigate(R.id.action_mainFragment2_to_thisWeekFragment2)
        }

    }
}