import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.openapi.generator)
}

android {
    namespace = "com.example.samplepandaai"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.samplepandaai"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "BASE_URL", "\"https://api.github.com\"")
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.github.com\"")
        }
    }

    testOptions {
        unitTests.all {
            it.systemProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug")
        }
    }

    sourceSets {
        getByName("main") {
            kotlin.srcDir("$buildDir/generate-resources/main/src/main/kotlin")
        }
    }
}

val generateGitHubDto = tasks.register<GenerateTask>("generateGitHubDto") {
    generatorName.set("kotlin")
    inputSpec.set("$projectDir/src/main/openapi/github_repos.yaml")

    outputDir.set("$buildDir/generate-resources/main/src/main/kotlin")

    packageName.set("com.example.samplepandaai.data.remote.dto")
    modelPackage.set("com.example.samplepandaai.data.remote.dto")

    generateApiTests.set(false)
    generateModelTests.set(false)
    configOptions.set(
        mapOf(
            "serializationLibrary" to "kotlinx_serialization",
            "enumPropertyNaming" to "UPPERCASE",
            "collectionType" to "list",
            "useContextualSerialization" to "true"
        )
    )
}

tasks.named("preBuild") {
    dependsOn(generateGitHubDto)
}

configurations {
    testImplementation { exclude(group = "com.github.tony19", module = "logback-android") }
    testRuntimeOnly { exclude(group = "com.github.tony19", module = "logback-android") }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.slf4j.api)
    implementation(libs.logback.android)
    testImplementation(libs.slf4j.simple)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
