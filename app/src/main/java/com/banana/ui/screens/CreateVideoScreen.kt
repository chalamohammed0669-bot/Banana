package com.banana.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.banana.domain.model.Video
import com.banana.ui.viewmodel.VideoViewModel

@Composable
fun CreateVideoScreen(
    viewModel: VideoViewModel,
    onVideoUploaded: () -> Unit
) {
    var videoTitle by remember { mutableStateOf("") }
    var videoDescription by remember { mutableStateOf("") }
    var selectedMusic by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getMusicList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Create Video",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Camera Preview
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Camera Preview")
            }
        }

        // Effects Section
        Text("Effects", style = MaterialTheme.typography.labelLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(4) {
                OutlinedButton(
                    onClick = { selectedFilter = "effect_$it" },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Effect ${it + 1}")
                }
            }
        }

        // Filters Section
        Text("Filters", style = MaterialTheme.typography.labelLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(4) {
                OutlinedButton(
                    onClick = { selectedFilter = "filter_$it" },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Filter ${it + 1}")
                }
            }
        }

        // Music Selection
        Text("Music", style = MaterialTheme.typography.labelLarge)
        val musicList by viewModel.musicList.collectAsState()
        if (musicList.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(musicList.take(4)) { music ->
                    OutlinedButton(
                        onClick = { selectedMusic = music.id },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(music.title)
                    }
                }
            }
        }

        // Video Info
        OutlinedTextField(
            value = videoTitle,
            onValueChange = { videoTitle = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = videoDescription,
            onValueChange = { videoDescription = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { /* Save Draft */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save Draft")
            }
            Button(
                onClick = { /* Upload */ onVideoUploaded() },
                modifier = Modifier.weight(1f),
                enabled = videoTitle.isNotEmpty()
            ) {
                Icon(Icons.Default.Add, contentDescription = "Upload")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Upload")
            }
        }
    }
}
