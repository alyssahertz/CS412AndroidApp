package com.example.assignment2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.assignment2.ui.theme.Assignment2Theme

class MainActivity : ComponentActivity() {
    private var myService: MyService? = null
    private var isBound by mutableStateOf(false)

    private var grade by mutableStateOf("")

    private val myBroadcastReceiver = MyBroadcastReceiver()

    private val serviceConnection = object : android.content.ServiceConnection {
        override fun onServiceConnected(
            name: android.content.ComponentName?,
            service: android.os.IBinder?
        )
        {
            val binder = service as MyService.MyBinder
            myService = binder.getService()
            isBound = true
            grade = myService?.getMyGrade() ?: ""
        }

        override fun onServiceDisconnected(
            name: android.content.ComponentName?
        )
        {
            myService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Index(
                        modifier = Modifier.padding(innerPadding),
                        grade = grade,
                        serviceConnection = serviceConnection,
                        isBound = isBound,
                        onUnbind = { isBound = false }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val filter = android.content.IntentFilter("com.example.assignment2.MY_ACTION")

        registerReceiver(
            myBroadcastReceiver,
            filter,
            RECEIVER_NOT_EXPORTED
        )
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(myBroadcastReceiver)
    }
}

@Composable
fun Index(modifier: Modifier = Modifier, grade:String, serviceConnection: android.content.ServiceConnection, isBound: Boolean, onUnbind: () -> Unit) {
    val context = LocalContext.current

    Surface(color = Color.Black) {
        Column(
            modifier = modifier.fillMaxSize().padding(10.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
            {
                Surface(color = Color.Unspecified)
                {
                    Text(
                        text = "Alyssa Hertz \n\n1384354",
                        color = Color.White,
                        fontSize = 35.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(0.dp, 10.dp))

                Button(
                    onClick = {
                        val intent = Intent(context, SecondActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.width(340.dp)
                )
                {
                    Text(text = "Start Activity Explicitly", fontSize = 28.sp)
                }

                Spacer(modifier = Modifier.padding(0.dp, 5.dp))

                Button(
                    onClick = {
                        val intent = Intent("com.example.assignment2.SECOND_ACTIVITY")
                        context.startActivity(intent)
                    },
                    modifier = Modifier.width(340.dp)
                )
                {
                    Text(text = "Start Activity Implicitly", fontSize = 28.sp)
                }

                Spacer(modifier = Modifier.padding(0.dp, 5.dp))

                Button(
                    onClick = {
                        val intent = Intent(context, MyService::class.java)
                        ContextCompat.startForegroundService(context, intent)
                    },
                    modifier = Modifier.width(340.dp)
                )
                {
                    Text(text = "Start Service", fontSize = 28.sp)
                }

                Spacer(modifier = Modifier.padding(0.dp, 5.dp))

                // Including this so I can stop the service if I ever start the app from my phone
                Button(
                    onClick = {
                        if (isBound) {
                            context.unbindService(serviceConnection)
                            onUnbind()
                        }

                        val intent = Intent(context, MyService::class.java)
                        context.stopService(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                    ),
                    modifier = Modifier.width(340.dp)
                ) {
                    Text("Stop Service", fontSize = 14.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.padding(0.dp, 5.dp))

                Button(
                    onClick = {
                        val intent = Intent(context, MyService::class.java)

                        context.bindService(
                            intent,
                            serviceConnection,
                            android.content.Context.BIND_AUTO_CREATE
                        )
                    },
                    modifier = Modifier.width(340.dp)
                )
                {
                    Text(text = "Bind Service", fontSize = 28.sp)
                }

                Spacer(modifier = Modifier.padding(0.dp, 5.dp))

                Text(
                    text = if (grade.isNotEmpty()) "Grade: $grade" else "",
                    color = Color.White,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.padding(0.dp, 5.dp))

                Button (
                    onClick = {
                        val intent = Intent("com.example.assignment2.MY_ACTION").setPackage(context.packageName)
                        context.sendBroadcast(intent)
                    },
                    modifier = Modifier.width(340.dp)
                )
                {
                    Text(text = "Send Broadcast", fontSize = 28.sp)
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun Assignment2Index() {
    Assignment2Theme {
        Index(
            grade = "",
            isBound = false,
            onUnbind = {},
            serviceConnection = object : android.content.ServiceConnection {
                override fun onServiceConnected(
                    name: android.content.ComponentName?,
                    service: android.os.IBinder?
                ) {}

                override fun onServiceDisconnected(
                    name: android.content.ComponentName?
                ) {}
            }
        )
    }
}