package com.luizalberto.chatbot.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.luizalberto.chatbot.R
import com.luizalberto.chatbot.data.Message
import com.luizalberto.chatbot.utils.Constants.RECEIVE_ID
import com.luizalberto.chatbot.utils.Constants.SEND_ID
import com.luizalberto.chatbot.utils.BotResponse
import com.luizalberto.chatbot.utils.Constants.OPEN_GOOGLE
import com.luizalberto.chatbot.utils.Constants.OPEN_SEARCH
import com.luizalberto.chatbot.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"


    var messagesList = mutableListOf<Message>()

    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("Luiz", "Sara", "Bryan", "Brandon")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()

        clickEvents()

        val random = (0..3).random()
        customBotMessage("Olá! Hoje você está falando com ${botList[random]}, em que posso ajudar?")
    }

    private fun clickEvents() {

        //Envie uma mensagem
        btn_send.setOnClickListener {
            sendMessage()
        }

        //Role para trás para a posição correta quando o usuário clicar na visualização de texto
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onStart() {
        super.onStart()
        //Caso haja mensagens, role para baixo ao reabrir o aplicativo
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Adiciona à nossa lista local
            messagesList.add(Message(message, SEND_ID, timeStamp))
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Atraso de resposta falsa
            delay(1000)

            withContext(Dispatchers.Main) {
                //Obtém a resposta
                val response = BotResponse.basicResponses(message)

                //Adiciona à nossa lista local
                messagesList.add(Message(response, RECEIVE_ID, timeStamp))

                //Insere nossa mensagem no adaptador
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))


                //Rola-nos para a posição da última mensagem
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                //Inicia o Google
                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }

    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}