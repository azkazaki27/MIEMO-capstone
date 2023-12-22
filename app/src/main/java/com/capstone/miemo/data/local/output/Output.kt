package com.capstone.miemo.data.local.output

data class Output (
    val emotions: List<String>,
    val messages: Messages
)

data class Messages (
    val sadness: List<Quotes>,
    val anger: List<Quotes>,
    val fear: List<Quotes>,
    val love: List<Quotes>,
    val joy: List<Quotes>,
    val surprise: List<Quotes>
)

data class Quotes (
    val message: String
)

data class EmotionRersult(
    val result: String,
    val score: Float
)
