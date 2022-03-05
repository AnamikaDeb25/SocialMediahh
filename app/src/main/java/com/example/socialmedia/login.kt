package com.example.socialmedia

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AlertDialog

class login : AppCompatActivity() {
    lateinit var et1 : EditText
    lateinit var et2 : EditText
    lateinit var go : ImageView
    lateinit var forget : TextView
    lateinit var signin : TextView
    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        et1 = findViewById(R.id.editText1)
        et2 = findViewById(R.id.editText2)
        go = findViewById(R.id.go)
        forget = findViewById(R.id.textView2)
        signin = findViewById(R.id.sg)
        val email = "ana@gmail.com"
        val pass = "ana123"

        go.setOnClickListener {
            val etemail = et1.getText().toString()
            val etpass = et2.getText().toString()
            if (etemail.isEmpty()){
                et1.setError("Please enter email")
                et1.requestFocus() }
            else if (etpass.isEmpty()){
                et2.setError("Please enter password")
                et2.requestFocus() }
            else if (etemail != email || etpass !=pass){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Incorrect details")
                builder.setMessage("Try again")
                builder.setIcon(R.drawable.ic_baseline_error_outline_24)
                builder.setPositiveButton("Ok"){ dialogInterface, which ->
                    et1.setText("")
                    et2.setText("")
                }
                builder.setNeutralButton("Cancle"){ dialogInterface, which -> alertDialog.dismiss() }
                builder.setCancelable(false)
                alertDialog = builder.create()
                alertDialog.show()
                val button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                button.setTextColor(Color.RED)
            }
            else if (etemail == email && etpass == pass){
                Toast.makeText(this,"Login successfully",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,home::class.java))
            }
        }
        }
}