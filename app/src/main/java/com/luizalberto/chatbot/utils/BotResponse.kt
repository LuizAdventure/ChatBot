package com.luizalberto.chatbot.utils

import com.luizalberto.chatbot.utils.Constants.OPEN_GOOGLE
import com.luizalberto.chatbot.utils.Constants.OPEN_SEARCH
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..3).random()
        val message = _message.toLowerCase(Locale.ROOT)

        return when {

            //Jogar moeda
            message.contains("jogar") && message.contains("moeda") -> {
                val r = (0..1).random()
                val result = if (r == 0) "cara" else "coroa"

                "Joguei uma moeda e ela caiu $result"
            }

            //calculos matematicos
            message.contains("qual é o valor de ") -> {
                val equation: String? = message.substringAfterLast("qual é o valor de")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Desculpe, não consigo resolver isso."
                }
            }


            message.contains("oi") -> {
                when (random) {
                    0 -> "Ola!"
                    1 -> "e ai"
                    2 -> "como vai você!"
                    else -> "error"
                }
            }
            message.contains("bom dia") -> {
                when (random) {
                    0 -> "Bom dia!"
                    1 -> "Bom dia!"
                    2 -> "Bom dia!"
                    else -> "error"
                }
            }
            message.contains("boa tarde") -> {
                when (random) {
                    0 -> "Boa tarde!"
                    1 -> "Boa tarde!"
                    2 -> "Boa tarde!"
                    else -> "error"
                }
            }
            message.contains("boa noite") -> {
                when (random) {
                    0 -> "Boa noite!"
                    1 -> "Boa noite!"
                    2 -> "Boa noite!"
                    else -> "error"
                }
            }

            message.contains("como você está?") -> {
                when (random) {
                    0 -> "Estou bem, obrigado!"
                    1 -> "Estou com fome..."
                    2 -> "Muito bom! E quanto a você?"
                    else -> "error"
                }

            }
            message.contains("o que você pode fazer") -> {
                when (random) {
                    0 -> "Eu posso responder algumas das suas perguntas"
                    1 -> "Posso te ajudar, com alguma coisa"
                    2 -> "Posso tentar de ajudar"
                    else -> "error"
                }
            }
            //Horario
            message.contains("que horas são") as Boolean && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Abrir o google
            message.contains("abrir") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Pesquise na internet
            message.contains("procurar")-> {
                OPEN_SEARCH
            }

            //Quando o programa não entende ...
            else -> {
                when (random) {
                    0 -> "Não entendo..."
                    1 -> "Tente me perguntar algo diferente"
                    2 -> "Sei lá"
                    else -> "error"
                }
            }
        }
    }
}












