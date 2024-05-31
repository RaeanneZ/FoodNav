plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "sg.edu.np.mad.mad24p03team2"
    compileSdk = 34

    defaultConfig {
        applicationId = "sg.edu.np.mad.mad24p03team2"
        minSdk = 33
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("androidx.recyclerview:recyclerview-selection:1.1.0") // for recycler view
    testImplementation("junit:junit:4+")
    implementation("net.sourceforge.jtds:jtds:1.3.1") //net.sourceforge.jtds:jtds:1.3.1
    implementation("com.android.volley:volley:1.2.1") //com.android.volley:volley:1.2.1

    implementation ("com.fasterxml.jackson.core:jackson-databind:2.10.1") //com.fasterxml.jackson.core:jackson-databind:2.10.1
    implementation ("com.fasterxml.jackson.core:jackson-core:2.10.1") //com.fasterxml.jackson.core:jackson-core:2.10.1
    implementation ("com.fasterxml.jackson.core:jackson-annotations:2.10.1") //com.fasterxml.jackson.core:jackson-annotations:2.10.1


    implementation ("com.squareup.okhttp3:okhttp:4.9.1") //com.squareup.okhttp3:okhttp:4.9.1
    implementation ("com.google.code.gson:gson:2.10.1") //com.google.code.gson:gson:2.10.1
    implementation ("android.arch.lifecycle:extensions:1.1.1") //android.arch.lifecycle:extensions:1.1.1

}