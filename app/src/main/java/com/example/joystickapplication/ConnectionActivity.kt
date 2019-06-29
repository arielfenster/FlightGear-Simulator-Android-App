package com.example.joystickapplication

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.lang.ref.WeakReference

class ConnectionActivity : AppCompatActivity() {

    private var joystickIntent : Intent
    private var mySocket : MySocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)
    }

    init {
        val loginIntent = intent
        val ip = loginIntent.getStringExtra("ip")
        val port = loginIntent.getStringExtra("port")
        val joystickIntent = ConnectTask(this).execute(ip,port).get()

    }

    private inner class ConnectTask(activity : ConnectionActivity) : AsyncTask<String, String, Intent>() {
        private val activityReference : WeakReference<ConnectionActivity> = WeakReference(activity)
//        private var intent : Intent


        override fun onPreExecute() {
            super.onPreExecute()
            val activity = this.activityReference.get()
            Toast.makeText(activity,"Connecting...",Toast.LENGTH_SHORT).show()
            Thread.sleep(2000)
        }

        override fun doInBackground(vararg strings: String?): Intent {

        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(result: Intent?) {
            super.onPostExecute(result)
        }
    }
}
