package com.example.assignment2

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment2.ui.theme.Assignment2Theme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Challenges(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun Challenges(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Surface(color = Color.Black) {
        Column(
            modifier = modifier.fillMaxSize().padding(10.dp, 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(text = "1. Different operating systems" +
                        "\n2. Must work in an unstable environment" +
                        "\n3. Must keep up with rapid changes" +
                        "\n4. Limited tool support" +
                        "\n5. Users have low tolerance",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(10.dp)
                )

                Spacer(modifier = Modifier.padding(0.dp, 10.dp))

                Button(
                    onClick = {
                        (context as? SecondActivity)?.finish()
                    }
                )
                {
                    Text(text = "Main Activity", fontSize = 28.sp)
                }
            }
    }
}