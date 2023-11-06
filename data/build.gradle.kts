import java.io.File
import java.io.FileInputStream
import java.util.Properties
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.ps.data"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val prop = Properties().apply {
            load(FileInputStream(File(rootProject.rootDir, "secure.properties")))
        }
        buildConfigField("String", "APIKEY", prop.getProperty("APIKEY"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(projects.domain)
    implementation(libs.core.ktx)
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.moshi.kotlin)
    implementation(libs.bundles.retrofit)
    ksp(libs.moshi.codegen)

    testImplementation(libs.bundles.app.test)
    testImplementation(libs.test.coroutines)
}
