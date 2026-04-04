package com.example.samplepandaai.ui.features.license

object LicenseDataProvider {
    fun getLicenses(): List<LicenseItem> {
        return listOf(
            LicenseItem(
                name = "Jetpack Compose",
                licenseName = "Apache License 2.0",
                url = "https://www.apache.org/licenses/LICENSE-2.0"
            ),
            LicenseItem(
                name = "Hilt",
                licenseName = "Apache License 2.0",
                url = "https://www.apache.org/licenses/LICENSE-2.0"
            ),
            LicenseItem(
                name = "Ktor",
                licenseName = "Apache License 2.0",
                url = "https://ktor.io/docs/welcome.html"
            ),
            LicenseItem(
                name = "Kotlinx Serialization",
                licenseName = "Apache License 2.0",
                url = "https://kotlinlang.org/docs/home.html"
            ),
            LicenseItem(
                name = "MockK",
                licenseName = "Apache License 2.0",
                url = "https://www.apache.org/licenses/LICENSE-2.0"
            ),
            LicenseItem(
                name = "SLF4J",
                licenseName = "MIT License",
                url = "https://opensource.org/licenses/MIT"
            )
        )
    }
}
