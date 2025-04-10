plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

android {
    namespace = "kz.lab2.myweblibrary"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    kotlinOptions {
        jvmTarget = "11"
    }

    // 定义单一变体发布配置，生成源码和 Javadoc JAR 文件
    publishing {
        singleVariant("release") {
//            withSourcesJar()
//            withJavadocJar()
        }
    }
}

// afterEvaluate 确保 Android 组件配置完成后再配置发布任务
afterEvaluate {
    configure<PublishingExtension> {
        publications {
            create<MavenPublication>("myWeblibrary") {
                from(components["release"])
                // 请确保 groupId 和 artifactId 与 GitHub Packages 的要求一致
                groupId = "com.github.alem2coder1"
                artifactId = "myWeblibrary"
                version = "1.0.6" // 使用一个全新的版本号避免已存在版本冲突
            }
        }
        repositories {
            maven {
                // 如果你使用的是已有的仓库 "fristPackages"，则 URL 如下
                setUrl("https://maven.pkg.github.com/alem2coder1/fristPackages")
                credentials {
                    username = System.getenv("GITHUB_USERNAME")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}