package com.example.samplepandaai.ui.features.license

object LicenseDataProvider {
    fun getLicenses(): List<LicenseItem> {
        return listOf(
            LicenseItem(
                name = "Jetpack Compose",
                licenseName = "Apache License 2.0",
                url = "https://raw.githubusercontent.com/androidx/androidx/androidx-main/LICENSE.txt"
            ),
            LicenseItem(
                name = "Hilt",
                licenseName = "Apache License 2.0",
                url = "https://raw.githubusercontent.com/google/dagger/master/LICENSE.txt"
            ),
            LicenseItem(
                name = "Ktor",
                licenseName = "Apache License 2.0",
                url = "https://raw.githubusercontent.com/ktorio/ktor/main/LICENSE"
            ),
            LicenseItem(
                name = "Kotlinx Serialization",
                licenseName = "Apache License 2.0",
                url = "https://raw.githubusercontent.com/Kotlin/kotlinx.serialization/master/LICENSE"
            ),
            LicenseItem(
                name = "MockK",
                licenseName = "Apache License 2.0",
                url = "https://raw.githubusercontent.com/mockk/mockk/master/LICENSE"
            ),
            LicenseItem(
                name = "SLF4J",
                licenseName = "MIT License",
                url = "https://raw.githubusercontent.com/qos-ch/slf4j/master/LICENSE.txt"
            )
        )
    }
}
