@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.ps.movie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ps.movie"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.ps.movie.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.domain)
    implementation(projects.data)

    implementation(libs.bundles.androidx.compose.bom)
    implementation(platform(libs.kotlin.bom))

    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.compose.navigation)
    implementation(libs.compose.lifecycle)
    implementation(libs.androidx.lifecycle)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
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
