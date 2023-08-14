plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.moko.resources)
}

kotlin {
    android()

    jvm("desktop")

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation(libs.decompose)
                implementation(libs.decompose.compose)
                implementation(libs.settings)
                implementation(libs.sqldelight.runtime)
                implementation(libs.serialization.json)

                api(libs.coroutines.core)
                api(libs.koin.core)
                api(libs.moko.resources)
                api(libs.moko.resources.compose)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.appcompat)
                api(libs.compose.activity)
                implementation(libs.sqldelight.android)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.sqldelight.native)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(libs.coroutines.swing)
                implementation(libs.sqldelight.jvm)
            }
        }
    }
}

android {
    compileSdk = libs.versions.android.compile.sdk.get().toInt()
    namespace = "com.shagalalab.sozlik.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.min.sdk.get().toInt()
        targetSdk = libs.versions.android.target.sdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

sqldelight {
    database("SozlikDatabase") {
        packageName = "com.shagalalab.sozlik"
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.shagalalab.sozlik" // required
    multiplatformResourcesClassName = "CommonRes" // optional, default MR
}
