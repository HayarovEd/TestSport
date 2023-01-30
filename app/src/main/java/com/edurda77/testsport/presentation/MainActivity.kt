package com.edurda77.testsport.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edurda77.testsport.databinding.ActivityMainBinding
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }
}