package com.example.joystickapplication

import android.os.AsyncTask
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket

/**
 * The client is communicating with the simulator - sending the commands that fly the aircraft
 */
class Client {
    private val paths: Map<String, String>
    private lateinit var socket: Socket
    private lateinit var outStream: OutputStream

    init {
        this.paths = mapOf(
            "AILERON" to "/controls/flight/aileron",
            "ELEVATOR" to "/controls/flight/elevator"
        )
    }

    /**
     * Establish connecting to a socket that is connected to the server.
     *
     * @param ip - the ip of the server
     * @param port - the port of the server
     */
    fun connect(ip: String, port: Int) {
        val t = Thread(Runnable {
            try {
                val serverAddr: InetAddress = InetAddress.getByName(ip)
                println("Connecting...")
                this.socket = Socket()
                this.socket.connect(InetSocketAddress(serverAddr, port))
                println("Connected!")
                this.outStream = this.socket.getOutputStream()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        t.start()
        t.join()
    }

    /**
     * Sending a command to the server with the given parameter and value. This is done asynchronously
     *
     * @param parameter
     * @param value
     */
    fun sendCommand(parameter: String, value: String) {
        SendCommandTask().execute(parameter, value)
    }

    /**
     * Closing the connection to the server
     */
    fun disconnect() {
        try {
            this.outStream.close()
            this.socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Asynchronously sending commands to the server
     */
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
                outStream.write(command)
                outStream.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}