@Suppress("DSL_SCOPE_VIOLATION")
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
    implementation(libs.compose.navigation)
    implementation(libs.compose.lifecycle)
    implementation(libs.androidx.lifecycle)

    implementation(libs.bundles.hilt)
    implementation(libs.appcompat)
    implementation(libs.material)
    ksp(libs.hilt.compiler)

    implementation(libs.image.glide)
    implementation(libs.bundles.retrofit)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.codegen)

    testImplementation(libs.bundles.app.test)
    testImplementation(libs.test.coroutines)
    testImplementation(libs.test.hilt.android)
    kspTest(libs.test.hilt.android.compiler)
}
