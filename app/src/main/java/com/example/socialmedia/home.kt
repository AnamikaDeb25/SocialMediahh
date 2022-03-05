package com.example.socialmedia

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView

class home : AppCompatActivity() {
    lateinit var mp: MediaPlayer
    lateinit var popupMenu : TextView
    lateinit var play : ImageView
    lateinit var alertDialog: AlertDialog
    lateinit var tvLabel: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        popupMenu = findViewById(R.id.view)

        var seekbarr: SeekBar = findViewById(R.id.seekBar)
        play=findViewById(R.id.play)
        var playing:Boolean= false
        mp=MediaPlayer.create(this, R.raw.samplesong)
        seekbarr.max=mp.duration
        seekbarr.progress=0


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
       // tvLabel = findViewById(R.id.tvLabel)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    startActivity(Intent(this,home::class.java))
                    true
                }
                R.id.menu_profile -> {
                    startActivity(Intent(this,profile::class.java))
                    true
                }
                else -> false
            }
        }




        //playing music
        play.setOnClickListener {
            play.setImageResource(R.drawable.ic_baseline_pause_24)
            if (!playing) {
                playing= true
                mp.start()
                Thread(Runnable {
                    var currentPosition = mp.currentPosition
                    val total = mp.duration
                    while (mp.isPlaying && currentPosition < total) {
                        currentPosition = try {
                            Thread.sleep(1000)
                            mp.currentPosition
                        } catch (e: InterruptedException) {
                            return@Runnable
                        } catch (e: Exception) {
                            return@Runnable
                        }
                        seekbarr.setProgress(currentPosition)
                    }
                    play.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                }).start()
            }
            else{mp.pause()
                playing=false
                play.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }

        }
        seekbarr.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if(mp.isPlaying){
                    mp.seekTo(seekbarr.progress) }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        //popup menu
        popupMenu.setOnClickListener {
            val popup = PopupMenu(this, popupMenu)
            popup.menuInflater.inflate(R.menu.popupmenu,popup.menu)
            popup.setOnMenuItemClickListener { item ->
                val id = item.itemId
                if (id == R.id.SetReminder){ startActivity(Intent(this,SetReminder::class.java)) }
                if (id == R.id.Profile){ startActivity(Intent(this,profile::class.java)) }
                if (id == R.id.rate){ startActivity(Intent(this,rating::class.java)) }
                if (id == R.id.Logout){
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Are you sure you want to logout?")
                    builder.setIcon(R.drawable.ic_baseline_error_outline_24)
                    builder.setPositiveButton("Yes"){ dialogInterface, which ->
                        Toast.makeText(applicationContext,"Logged out",Toast.LENGTH_SHORT).show()

                    }
                    builder.setNeutralButton("Cancle"){ dialogInterface, which -> alertDialog.dismiss() }
                    builder.setCancelable(false)
                    alertDialog = builder.create()
                    alertDialog.show()
                }
                true
            }
            popup.show()
        }
    }

    override fun onStop() {
        super.onStop()
        mp.pause()
        play.setImageResource(R.drawable.ic_baseline_play_arrow_24)
    }
}