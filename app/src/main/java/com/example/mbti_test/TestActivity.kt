package com.example.mbti_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    val questionResults = QuestionResults()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapte(this)
        viewPager.isUserInputEnabled = false
    }

    fun moveNextPage() {
        if(viewPager.currentItem == 3) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("results", ArrayList(questionResults.results))
            startActivity(intent)

        } else {
            val nextItem = viewPager.currentItem +1
            if (nextItem < viewPager.adapter?.itemCount ?:0) {
                viewPager.setCurrentItem(nextItem, true)
            }
        }
    }
}

class QuestionResults {
    val results = mutableListOf<Int>()

    fun addResponses(response: List<Int>){
        val mostFrequent = response.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        mostFrequent?.let { results.add(it) }
    }
}