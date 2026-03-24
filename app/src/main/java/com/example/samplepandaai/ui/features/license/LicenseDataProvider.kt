package com.example.samplepandaai.ui.features.license

object LicenseDataProvider {
    fun getLicenses(): List<LicenseItem> {
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
}
