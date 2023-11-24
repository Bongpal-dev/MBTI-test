package com.example.mbti_test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class QuestionFragment : Fragment() {

    private var questionType: Int = 0

    //검사 유형 (외향형&내향형, 감각형&직관형....)
    private val questionTitle = listOf(
        R.string.question1_title,
        R.string.question2_title,
        R.string.question3_title,
        R.string.question4_title
    )

    //유형별 질문
    private val questionTexts = listOf(
        listOf(R.string.question1_1, R.string.question1_2, R.string.question1_3),
        listOf(R.string.question2_1, R.string.question2_2, R.string.question2_3),
        listOf(R.string.question3_1, R.string.question3_2, R.string.question3_3),
        listOf(R.string.question4_1, R.string.question4_2, R.string.question4_3)
    )

    //질문별 대답
    private val questionAnswer = listOf(
        listOf(
            listOf(R.string.question1_1_answer1, R.string.question1_1_answer2),
            listOf(R.string.question1_2_answer1, R.string.question1_2_answer2),
            listOf(R.string.question1_3_answer1, R.string.question1_3_answer2)
        ),
        listOf(
            listOf(R.string.question2_1_answer1, R.string.question2_1_answer2),
            listOf(R.string.question2_2_answer1, R.string.question2_2_answer2),
            listOf(R.string.question2_3_answer1, R.string.question2_3_answer2)
        ),
        listOf(
            listOf(R.string.question3_1_answer1, R.string.question3_1_answer2),
            listOf(R.string.question3_2_answer1, R.string.question3_2_answer2),
            listOf(R.string.question3_3_answer1, R.string.question3_3_answer2)
        ),
        listOf(
            listOf(R.string.question4_1_answer1, R.string.question4_1_answer2),
            listOf(R.string.question4_2_answer1, R.string.question4_2_answer2),
            listOf(R.string.question4_3_answer1, R.string.question4_3_answer2)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            questionType = it.getInt(ARG_QUESTION_TYPE)
        }
    }

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val title: TextView = view.findViewById(R.id.tv_quest_title)
        title.text = getString(questionTitle[questionType])

        val questTextView = listOf<TextView>(
            view.findViewById(R.id.tv_quest_1),
            view.findViewById(R.id.tv_quest_2),
            view.findViewById(R.id.tv_quest_3)
        )

        val answerRadioGroup = listOf<RadioGroup>(
            view.findViewById(R.id.rg_ans1),
            view.findViewById(R.id.rg_ans2),
            view.findViewById(R.id.rg_ans3)
        )

        for (i in questTextView.indices) {
            questTextView[i].text = getString(questionTexts[questionType][i])

            val radioButton1 = answerRadioGroup[i].getChildAt(0) as RadioButton
            val radioButton2 = answerRadioGroup[i].getChildAt(1) as RadioButton

            radioButton1.text = getString(questionAnswer[questionType][i][0])
            radioButton2.text = getString(questionAnswer[questionType][i][1])
        }

        val nextBtn = view.findViewById<Button>(R.id.btn_next)

        if (questionType == 3) {
            nextBtn.text = "결과보기"
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val answerRadioGroup = listOf<RadioGroup>(
            view.findViewById(R.id.rg_ans1),
            view.findViewById(R.id.rg_ans2),
            view.findViewById(R.id.rg_ans3)
        )

        val nextBtn: Button = view.findViewById(R.id.btn_next)

        nextBtn.setOnClickListener {
            val isAllAnswer = answerRadioGroup.all { it.checkedRadioButtonId != -1 }

            if (isAllAnswer) {
                val responses = answerRadioGroup.map { radioGroup ->
                    val firstBtn = radioGroup.getChildAt(0)  as RadioButton
                    if (firstBtn.isChecked) 1 else 2
                }

                (activity as? TestActivity)?.questionResults?.addResponses(responses)
                (activity as? TestActivity)?.moveNextPage()

            } else {
                Toast.makeText(context, "모든 질문에 답해주세요", Toast.LENGTH_SHORT).show()
            }

        }


    }



    companion object {
        private const val ARG_QUESTION_TYPE = "questionType"

        fun newInstance(questionType: Int): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putInt(ARG_QUESTION_TYPE, questionType)
            fragment.arguments = args
            return fragment
        }
    }
}