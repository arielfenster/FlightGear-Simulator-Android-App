package com.example.joystickapplication

import java.io.Serializable
import java.net.Socket

class MySocket(private val socket: Socket?) : Serializable {
    fun getSocket() = this.socket
    fun close() = this.socket?.close()
}