@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.ps.movies"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {

    implementation(projects.domain)

    implementation(libs.bundles.androidx.compose.bom)
    implementation(platform(libs.kotlin.bom))

    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.compose.navigation)
    implementation(libs.compose.lifecycle)
    implementation(libs.androidx.lifecycle)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    ksp(libs.hilt.compiler)

    implementation(libs.image.glide)
    implementation(libs.bundles.retrofit)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.codegen)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junit)
    testImplementation(libs.test.turbine)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(libs.test.junitExtension)
    androidTestImplementation(libs.test.compose.ui.test)
    debugImplementation(libs.test.compose.ui.tooling)
    debugImplementation(libs.test.compose.manifest)
    androidTestImplementation(libs.test.hilt.android)
    testImplementation(libs.test.mock)
    testImplementation(libs.test.coroutines)
    testImplementation(libs.test.core)
    testImplementation(libs.test.hilt.android)
    kspTest(libs.test.hilt.android.compiler)
}
