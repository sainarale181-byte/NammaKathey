package com.nammakatheey.app
import com.nammakatheey.app.data.model.QuizQuestion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class QuizActivity : AppCompatActivity() {

    private var currentQuestion = 0
    private var score = 0
    private var heroName = "Hero"
    private var heroDistrict = "Karnataka"
    private lateinit var questions: List<QuizQuestion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        heroName = intent.getStringExtra("hero_name") ?: "Hero"
        heroDistrict = intent.getStringExtra("hero_district") ?: "Karnataka"

        // Set hero name
        findViewById<TextView>(R.id.tvQuizHeroName).text = heroName

        // Back button
        findViewById<ImageButton>(R.id.btnQuizBack).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Load questions
        questions = getQuestionsForHero(heroName)

        // Show first question
        showQuestion(0)

        // Option buttons
        val btnOption1 = findViewById<MaterialButton>(R.id.btnOption1)
        val btnOption2 = findViewById<MaterialButton>(R.id.btnOption2)
        val btnOption3 = findViewById<MaterialButton>(R.id.btnOption3)
        val btnNext = findViewById<MaterialButton>(R.id.btnNextQuestion)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        val optionButtons = listOf(btnOption1, btnOption2, btnOption3)

        optionButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                val correct = questions[currentQuestion].correctIndex
                if (index == correct) {
                    score++
                    tvResult.text = "✅ Correct! Well done!"
                    tvResult.setTextColor(getColor(R.color.quiz_correct))
                    button.backgroundTintList = getColorStateList(R.color.quiz_correct)
                    button.setTextColor(getColor(R.color.white))
                } else {
                    tvResult.text = "❌ Wrong! The correct answer was: ${questions[currentQuestion].options[correct]}"
                    tvResult.setTextColor(getColor(R.color.quiz_wrong))
                    button.backgroundTintList = getColorStateList(R.color.quiz_wrong)
                    button.setTextColor(getColor(R.color.white))
                    // Highlight correct answer
                    optionButtons[correct].backgroundTintList = getColorStateList(R.color.quiz_correct)
                    optionButtons[correct].setTextColor(getColor(R.color.white))
                }

                // Disable all buttons after answer
                optionButtons.forEach { it.isEnabled = false }
                tvResult.visibility = View.VISIBLE
                btnNext.visibility = View.VISIBLE

                // Change Next button text on last question
                if (currentQuestion == questions.size - 1) {
                    btnNext.text = "See Results 🏆"
                }
            }
        }

        // Next question button
        btnNext.setOnClickListener {
            if (currentQuestion < questions.size - 1) {
                currentQuestion++
                showQuestion(currentQuestion)
                tvResult.visibility = View.GONE
                btnNext.visibility = View.GONE
                // Reset option buttons
                optionButtons.forEach {
                    it.isEnabled = true
                    it.backgroundTintList = getColorStateList(R.color.white)
                    it.setTextColor(getColor(R.color.text_primary))
                }
            } else {
                // Go to Profile with badge
                goToResults()
            }
        }
    }

    private fun showQuestion(index: Int) {
        val question = questions[index]
        findViewById<TextView>(R.id.tvQuestion).text = question.question
        findViewById<TextView>(R.id.tvQuizProgress).text = "Question ${index + 1} of ${questions.size}"
        findViewById<ProgressBar>(R.id.progressQuiz).progress = index + 1

        val buttons = listOf(
            findViewById<MaterialButton>(R.id.btnOption1),
            findViewById<MaterialButton>(R.id.btnOption2),
            findViewById<MaterialButton>(R.id.btnOption3)
        )
        buttons.forEachIndexed { i, btn ->
            btn.text = question.options[i]
        }
    }

    private fun goToResults() {
        val prefs = getSharedPreferences("namma_kathey_prefs", MODE_PRIVATE)
        val badges = prefs.getStringSet("badges", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        badges.add(heroName)
        prefs.edit().putStringSet("badges", badges).apply()

        val resultIntent = Intent(this, ProfileActivity::class.java)
        resultIntent.putExtra("badge_earned", heroName)
        resultIntent.putExtra("score", score)
        resultIntent.putExtra("total", questions.size)
        startActivity(resultIntent)
        finish()
    }

    private fun getQuestionsForHero(hero: String): List<QuizQuestion> {
        return when (hero) {
            "Sangolli Rayanna" -> listOf(
                QuizQuestion(
                    "Which queen was Sangolli Rayanna the army chief of?",
                    listOf("Rani Lakshmibai", "Kittur Chennamma", "Chand Bibi"),
                    1
                ),
                QuizQuestion(
                    "In which district was Sangolli Rayanna born?",
                    listOf("Mysuru", "Belagavi", "Dharwad"),
                    1
                ),
                QuizQuestion(
                    "In which year was Sangolli Rayanna hanged by the British?",
                    listOf("1831", "1857", "1799"),
                    0
                )
            )
            "Kittur Chennamma" -> listOf(
                QuizQuestion(
                    "Which British policy did Kittur Chennamma resist?",
                    listOf("Doctrine of Lapse", "Divide and Rule", "Subsidiary Alliance"),
                    0
                ),
                QuizQuestion(
                    "In which year did Kittur Chennamma fight the British?",
                    listOf("1857", "1824", "1799"),
                    1
                ),
                QuizQuestion(
                    "Where was Kittur Chennamma imprisoned?",
                    listOf("Mysuru Fort", "Chitradurga Fort", "Bailhongal Fort"),
                    2
                )
            )
            "Tipu Sultan" -> listOf(
                QuizQuestion(
                    "What was Tipu Sultan's famous nickname?",
                    listOf("Lion of Mysore", "Tiger of Mysore", "Eagle of Mysore"),
                    1
                ),
                QuizQuestion(
                    "Which weapon did Tipu Sultan introduce in warfare?",
                    listOf("Cannon", "Rocket Artillery", "Catapult"),
                    1
                ),
                QuizQuestion(
                    "In which year did Tipu Sultan die?",
                    listOf("1799", "1857", "1831"),
                    0
                )
            )
            else -> listOf(
                QuizQuestion(
                    "Which state is this hero from?",
                    listOf("Tamil Nadu", "Karnataka", "Kerala"),
                    1
                ),
                QuizQuestion(
                    "What did this hero fight for?",
                    listOf("Personal wealth", "Freedom of India", "Foreign rule"),
                    1
                ),
                QuizQuestion(
                    "What does Namma Kathey mean?",
                    listOf("Our Story", "Their Story", "Old Story"),
                    0
                )
            )
        }
    }
}