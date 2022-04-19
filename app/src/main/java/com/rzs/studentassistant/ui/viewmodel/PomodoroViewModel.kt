package com.rzs.studentassistant.ui.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*

@HiltViewModel
class PomodoroViewModel : ViewModel() {

    var counting: MutableLiveData<Boolean> = MutableLiveData()
    private var _remainingSeconds: MutableLiveData<Int> = MutableLiveData()


    lateinit var timer: CountDownTimer

    fun seconds(): LiveData<Int>{
        return _remainingSeconds
    }

    fun stopTimer(){
        timer.cancel()
    }

    fun startCountDown(milis:Long){

        timer = object : CountDownTimer(milis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val remains = millisUntilFinished / 1000
                _remainingSeconds.value = remains.toInt()
            }

            override fun onFinish() {
                counting.postValue(false)
            }
        }.start()


    }


}

