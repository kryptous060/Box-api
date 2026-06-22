package com.box.gallery

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class BoxApiService : Service() {

    private var server: NettyApplicationEngine? = null
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        startApiServer()
    }

    private fun startApiServer() {
        serviceScope.launch {
            server = embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
                install(ContentNegotiation) {
                    json()
                }
                routing {
                    // Test endpoint to verify the API is alive
                    get("/status") {
                        call.respond(mapOf("status" to "online", "message" to "Box API Server is active"))
                    }

                    // Placeholder for text generation
                    post("/v1/chat") {
                        val request = call.receive<ChatRequest>()
                        call.respond(ChatResponse(reply = "API received your prompt: ${request.prompt}"))
                    }
                }
            }.start(wait = false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        server?.stop(1000, 2000)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

@Serializable
data class ChatRequest(val prompt: String)

@Serializable
data class ChatResponse(val reply
                        : String)

