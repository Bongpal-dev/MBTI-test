package com.example.mbti_test

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract.Root
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class ResultActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val results = intent.getIntegerArrayListExtra("results") ?: arrayListOf()

        val resultTypes = listOf(
            listOf("E","I"),
            listOf("N","S"),
            listOf("T","F"),
            listOf("J","P")
        )

        var resultString = ""
        for (i in results.indices) {
            resultString += resultTypes[i][results[i]-1]
        }

        val tv_resValue: TextView = findViewById(R.id.tv_resValue)
        tv_resValue.text = resultString

        val iv_resImg: ImageView = findViewById(R.id.iv_resImg)
        val imageResource = resources.getIdentifier("ic_${resultString.toLowerCase(Locale.ROOT)}","drawable", packageName)

        iv_resImg.setImageResource(imageResource)

        val btn_Retry: Button = findViewById(R.id.btn_resRetry)
        btn_Retry.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }


    }
}