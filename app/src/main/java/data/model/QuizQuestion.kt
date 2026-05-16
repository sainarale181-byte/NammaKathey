package com.nammakatheey.app.data.model

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)