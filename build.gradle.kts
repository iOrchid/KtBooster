// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0-alpha04")
        classpath(kotlin("gradle-plugin", version = kotlin_version))
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigation")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-progressive", "-Xopt-in=kotlin.RequiresOptIn")
            jvmTarget = "1.8"
        }
    }
}

//官方写法
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    afterEvaluate {
        extensions.configure<com.android.build.gradle.BaseExtension> {
            // Require that all Android projects target Java 8.
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }
}
