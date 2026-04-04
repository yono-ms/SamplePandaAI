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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.samplepandaai.R
import com.example.samplepandaai.ui.theme.MultiLanguagePreview
import com.example.samplepandaai.ui.theme.SamplePandaAITheme

data class LicenseItem(
    val name: String,
    val licenseName: String,
    val url: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseScreen(
    onBackClick: () -> Unit,
    onLicenseClick: (LicenseItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val licenses = remember { LicenseDataProvider.getLicenses() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.license_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(licenses) { license ->
                LicenseListItem(
                    license = license,
                    onClick = { onLicenseClick(license) }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun LicenseListItem(
    license: LicenseItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = license.name,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = license.licenseName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@MultiLanguagePreview
@Composable
fun LicenseScreenPreview() {
    SamplePandaAITheme {
        LicenseScreen(
            onBackClick = {},
            onLicenseClick = {}
        )
    }
}
