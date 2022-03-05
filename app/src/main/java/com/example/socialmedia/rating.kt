package com.example.socialmedia

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast

class rating : AppCompatActivity() {
    lateinit var ratingBar: RatingBar
    lateinit var button: Button
    lateinit var js: JobScheduler
    lateinit var jobInfo: JobInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)
        ratingBar = findViewById(R.id.rtbar)
        button = findViewById(R.id.btn)

        button.setOnClickListener{
            val ratingg = ratingBar.rating
            js = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            val cm = ComponentName(this, notification::class.java)
            val b = JobInfo.Builder(2, cm)
            b.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            b.setRequiresCharging(false)
            b.setRequiresDeviceIdle(false)
            jobInfo = b.build()
            js!!.schedule(jobInfo)
            Toast.makeText(applicationContext,"""$ratingg""".trimIndent()+" stars", Toast.LENGTH_SHORT).show()
        }
    }
}