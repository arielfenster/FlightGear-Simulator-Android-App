package com.example.joystickapplication

import android.os.AsyncTask
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.net.Socket

class Client {
    private val paths: Map<String, String>
    private val socket : Socket
    private val outStream : OutputStream

    init {
        this.paths = mapOf(
            "AILERON" to "/controls/flight/aileron",
            "ELEVATOR" to "/controls/flight/elevator"
        )
    }

    fun connect(ip : String, port : Int) {

    }

    fun sendCommand(parameter: String, value: String) {
        SendCommandTask().execute(parameter, value)
    }

    fun disconnect() {
        try {
            this.outStream?.close()
            this.socket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private inner class SendCommandTask : AsyncTask<String, Unit, Unit>() {
        override fun doInBackground(vararg strings: String?) {
            val parameter = strings[0]?.toUpperCase()
            if (!paths.containsKey(parameter)) {
                return
            }
            val value = strings[1]
            val msg = "set " + paths[parameter] + " " + value + " \r\n"
            val command = msg.toByteArray(Charsets.UTF_8)
            try {
                outStream?.write(command)
                outStream?.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}