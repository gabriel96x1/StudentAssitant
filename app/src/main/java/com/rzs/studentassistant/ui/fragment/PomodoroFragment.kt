package com.rzs.studentassistant.ui.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.lifecycle.Observer
import com.rzs.studentassistant.databinding.PomodoroFragmentBinding
import com.rzs.studentassistant.ui.viewmodel.PomodoroViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PomodoroFragment : Fragment() {

    companion object {
        fun newInstance() = PomodoroFragment()
    }

    private lateinit var viewModel: PomodoroViewModel
    private lateinit var btnStartStop : ToggleButton
    private lateinit var secondsText: TextView
    private lateinit var binding: PomodoroFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PomodoroFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        secondsText = binding.pomodoroTime
        btnStartStop = binding.btnStartStop

        viewModel = ViewModelProvider(this)[PomodoroViewModel::class.java]

        btnStartStop.setOnCheckedChangeListener { _, isOn ->
            if (isOn){
                viewModel.startCountDown(30000)
            } else {
                viewModel.stopTimer()
            }

        }

        viewModel.seconds().observe(viewLifecycleOwner, Observer {
            secondsText.text = it.toString()
        }

        )

    }

}