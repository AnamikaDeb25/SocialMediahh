package com.example.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog

class profile : AppCompatActivity() {
    lateinit var name : TextView
    lateinit var pgsBar : ProgressBar
    lateinit var alertDialog: AlertDialog
    private var a = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        name = findViewById(R.id.n)
        pgsBar = findViewById(R.id.pBar)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle("Profile")
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this,home::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.edtname){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Edit name")
            val input = EditText(this)
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
            input.layoutParams = lp
            builder.setView(input)
            builder.setPositiveButton("OK"){ dialogInterface, i ->

                alertDialog.dismiss()
                pgsBar.visibility = View.VISIBLE
                a = pgsBar.progress
                Thread {
                    while (a < 100) {
                        a += 5
                        Handler(Looper.getMainLooper()).post({
                            pgsBar.progress = a
                            if (a == 100) {
                                pgsBar.visibility = View.GONE
                                name.setText(input.text.toString())
                            }
                        })

                        try {
                            Thread.sleep(100)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }

                }.start()

            }
            builder.setNeutralButton("Cancle"){dialogInterface, i ->
                alertDialog.dismiss()
                val viewGroup: ViewGroup?= findViewById(R.id.toast_layout)
                val inflater = layoutInflater
                val layout: View = inflater.inflate(R.layout.toast_layout,viewGroup)
                // findViewById(R.id.custom_toast_layout1) as ViewGroup)
                val textview = layout.findViewById(R.id.txtvw) as TextView
                textview.text = "Name is not changed..!!"
                val toast = Toast(applicationContext)
                toast.setGravity(Gravity.BOTTOM, 0, 100)
                toast.duration = Toast.LENGTH_LONG
                toast.setView(layout)
                toast.show()
            }
            alertDialog= builder.create()
            alertDialog.show()
            return true
        }
        return super.onOptionsItemSelected(item)

    }
}





