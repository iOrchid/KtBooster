
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        jcenter()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath(kotlin("gradle-plugin", version = kotlin_version))
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigation")

        //kotlin二进制的api管理工具
        classpath("org.jetbrains.kotlinx:binary-compatibility-validator:0.4.0")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.0.0")//语法校验工具
        //dokit启用aop功能需要的插件
        classpath("com.didichuxing.doraemonkit:dokitx-plugin:3.3.5")

        //1、发布到jitpack.io的步骤1
//        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

apply(plugin = "binary-compatibility-validator")

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")

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
