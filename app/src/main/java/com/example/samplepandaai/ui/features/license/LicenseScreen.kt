package com.example.samplepandaai.ui.features.license

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.samplepandaai.R
import com.example.samplepandaai.ui.theme.SamplePandaAITheme

data class LicenseItem(
    val name: String,
    val licenseName: String,
    val text: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val licenses = remember { getLicenses() }
    var selectedLicense by remember { mutableStateOf<LicenseItem?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.license_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_content_description)
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(licenses) { item ->
                ListItem(
                    headlineContent = { Text(item.name) },
                    supportingContent = { Text(item.licenseName) },
                    modifier = Modifier
                        .clickable { selectedLicense = item }
                        .fillMaxWidth()
                )
                HorizontalDivider()
            }
        }
    }

    selectedLicense?.let { license ->
        LicenseDetailDialog(
            license = license,
            onDismissRequest = { selectedLicense = null }
        )
    }
}

@Composable
fun LicenseDetailDialog(
    license: LicenseItem,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(license.name) },
        text = {
            Column {
                Text(
                    text = license.licenseName,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(license.text)
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.license_dialog_close))
            }
        }
    )
}

private fun getLicenses(): List<LicenseItem> {
    return listOf(
        LicenseItem(
            name = "Jetpack Compose",
            licenseName = "Apache License 2.0",
            text = "Copyright 2019 The Android Open Source Project..."
        ),
        LicenseItem(
            name = "Hilt",
            licenseName = "Apache License 2.0",
            text = "Copyright 2020 Google Inc..."
        ),
        LicenseItem(
            name = "Ktor",
            licenseName = "Apache License 2.0",
            text = "Copyright 2014-2024 JetBrains s.r.o..."
        ),
        LicenseItem(
            name = "Kotlinx Serialization",
            licenseName = "Apache License 2.0",
            text = "Copyright 2017-2024 JetBrains s.r.o..."
        ),
        LicenseItem(
            name = "MockK",
            licenseName = "Apache License 2.0",
            text = "Copyright 2017-2024 Oleksii Ivanchenko..."
        ),
        LicenseItem(
            name = "SLF4J",
            licenseName = "MIT License",
            text = "Copyright (c) 2004-2022 QOS.ch..."
        )
    )
}

@Preview(showBackground = true)
@Composable
fun LicenseScreenPreview() {
    SamplePandaAITheme {
        LicenseScreen(onBackClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun LicenseDetailDialogPreview() {
    SamplePandaAITheme {
        LicenseDetailDialog(
            license = LicenseItem("Sample Library", "Apache 2.0", "Full license text goes here."),
            onDismissRequest = {}
        )
    }
}
