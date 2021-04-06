plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")

}

group = "com.github.InternetED"

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdk = 21
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")


    // 第三方登入
    implementation("com.google.android.gms:play-services-auth:19.0.0")
    implementation("com.facebook.android:facebook-login:7.1.0")
    implementation("com.linecorp:linesdk:5.0.1")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
}

// Because the components are created only during the afterEvaluate phase, you must
// configure your publications using the afterEvaluate() lifecycle method.
//afterEvaluate {
//    publishing()
//    publishing {
//        publications {
//            // Creates a Maven publication called "release".
//            release(MavenPublication) {
//                // Applies the component for the release build variant.
//                from components.release
//
//                        // You can then customize attributes of the publication as shown below.
//                        groupId = 'com.example.MyLibrary'
//                artifactId = 'final'
//                version = '1.0'
//            }
//            // Creates a Maven publication called “debug”.
//            debug(MavenPublication) {
//                // Applies the component for the debug build variant.
//                from components.debug
//
//                        groupId = 'com.example.MyLibrary'
//                artifactId = 'final-debug'
//                version = '1.0'
//            }
//        }
//    }
//
//}

//publishing {
//    (publications) {
//
//        // Publish the release aar artifact
//        register("defaultAar", MavenPublication::class) {
//            from(components["android"])
//            groupId = "digital.wup.android-maven-publish"
//            version = "${project.version}"
//
//        }
//    }
//}

//val sourcesJar by tasks.registering(Jar::class) {
//    archiveClassifier.set("sources")
//    from(android.sourceSets.getByName("main").java.srcDirs)
//}

afterEvaluate {
    publishing {
        publications {

            registering(MavenPublication::class) {

                from(components["java"])

                groupId = "com.github.interneted"
                artifactId = "loginlibrary"
                version = "1.0.0"
            }
        }


        repositories {
            maven {
                credentials {
                    username = "$usr"
                    password = "$pwd"
                }

                url = "https://maven.pkg.jetbrains.space/mycompany/p/projectkey/my-maven-repo"
            }
        }
    }

}

//publishing {
//    (publications) {
//
//        // Publish the release aar artifact
//        register("defaultAar", MavenPublication::class) {
//            from(components["java"])
//            groupId = "digital.wup.android-maven-publish"
//            version = "${project.version}"
//
//        }
//    }
//}


//afterEvaluate {
//    publishing {
//        repositories {
//            maven {
//                name = "GitHubPackages"
//                url = uri("https://maven.pkg.github.com/msfjarvis/github-packages-deployment-sample")
//                credentials {
//                    username = project.findProperty("gpr.user") ?: System.getenv("USERNAME")
//                    password = project.findProperty("gpr.key") ?: System.getenv("PASSWORD")
//                }
//            }
//        }
//        publications {
//            // Simple convenience function to hide the nullability of `findProperty`.
//            private fun getProperty(key: String): String {
//                return findProperty(key)?.toString() ?: error("Failed to find property for $key")
//            }
//            create<MavenPublication>("release") {
//                from(components.getByName("release"))
//                groupId = getProperty("GROUP")
//                artifactId = "deployment-sample-library"
//                version = getProperty("VERSION")
//            }
//        }
//    }
//}