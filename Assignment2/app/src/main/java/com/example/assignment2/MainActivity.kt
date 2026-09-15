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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment2.ui.theme.Assignment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Index(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Index(modifier: Modifier = Modifier) {
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
                    }
                )
                {
                    Text(text = "Start Activity Explicitly", fontSize = 28.sp)
                }

                Spacer(modifier = Modifier.padding(0.dp, 5.dp))

                Button(
                    onClick = {
                        val intent = Intent("com.example.assignment2.SECOND_ACTIVITY")
                        context.startActivity(intent)
                    }
                )
                {
                    Text(text = "Start Activity Implicitly", fontSize = 28.sp)
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun Assignment2Index() {
    Assignment2Theme {
        Index()
    }
}