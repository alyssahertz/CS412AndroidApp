
package com.example.assignment2

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

class ThirdActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CameraActivityScreen()
        }
    }
}

@Composable
fun CameraActivityScreen() {

    var capturedImage by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == android.app.Activity.RESULT_OK) {

            val bitmap = result.data?.extras?.get("data") as? Bitmap

            capturedImage = bitmap
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {

                val cameraIntent = Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE
                )

                cameraLauncher.launch(cameraIntent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Text("Capture Image")
        }

        capturedImage?.let { bitmap ->

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Captured camera image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )

        }
    }
}
