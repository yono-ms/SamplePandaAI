package com.example.samplepandaai.ui.features.license

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    val licenses = remember { LicenseDataProvider.getLicenses() }
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
                LicenseListItem(
                    item = item,
                    onClick = { selectedLicense = item }
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
fun LicenseListItem(
    item: LicenseItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = { Text(item.name) },
        supportingContent = { Text(item.licenseName) },
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
    )
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
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
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

@Preview(showBackground = true)
@Composable
fun LicenseScreenPreview() {
    SamplePandaAITheme {
        LicenseScreen(onBackClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun LicenseListItemPreview() {
    SamplePandaAITheme {
        LicenseListItem(
            item = LicenseItem("Sample Library", "Apache 2.0", "Text"),
            onClick = {}
        )
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
