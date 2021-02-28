import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.getByName

/**
 * ----------------------------------------------------------------
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2021年02月07日 16:09
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 依赖管理的相关扩展函数、
 */

//region 扩展dependencies

/**
 * 添加平台相关的基础依赖dependencies
 */
fun DependencyHandler.addCoreDependencies() {

    implementation(DepLibrary.KOTLIN_LIB)
    implementation(DepLibrary.KOTLIN_LIB_JDK)
    implementation(DepLibrary.KOTLIN_LIB_REFLECT)
    implementation(DepLibrary.COROUTINES_ANDROID)

    implementation(DepLibrary.APPCOMPAT)
    implementation(DepLibrary.CORE_KTX)
    implementation(DepLibrary.ACTIVITY_KTX)
    implementation(DepLibrary.FRAGMENT_KTX)

    implementation(DepLibrary.MATERIAL)
    implementation(DepLibrary.RECYCLER_VIEW)
    implementation(DepLibrary.CONSTRAINT_LAYOUT)
    implementation(DepLibrary.VIEW_PAGE2)

}


/**
 * 添加JetPack相关的依赖dependencies
 */
fun DependencyHandler.addJetPackDependencies() {

    kapt(DepLibrary.LIFECYCLE_COMMON_JAVA8)
    implementation(DepLibrary.LIFECYCLE_LIVEDATA_KTX)
    implementation(DepLibrary.LIFECYCLE_VIEWMODEL_KTX)

    implementation(DepLibrary.ROOM_COMMON)
    kapt(DepLibrary.ROOM_COMPILER)
    implementation(DepLibrary.ROOM_KTX)

    implementation(DepLibrary.PAGING_COMMON_KTX)
    implementation(DepLibrary.PAGING_RUNTIME_KTX)

    implementation(DepLibrary.WORK_RUNTIME_KTX)

    implementation(DepLibrary.NAVIGATION_FRAGMENT_KTX)
    implementation(DepLibrary.NAVIGATION_UI_KTX)

}

/**
 * 添加常用的 Github等第三方依赖dependencies
 * 如okHttp、Gson、Retrofit、glide 等等
 */
fun DependencyHandler.addGithubDependencies() {

    implementation(DepLibrary.RETROFIT)
    implementation(DepLibrary.RETROFIT_CONVERTER_GSON)
    implementation(DepLibrary.GSON)

    implementation(DepLibrary.OKHTTP)
    implementation(DepLibrary.MMKV)
    implementation(DepLibrary.COIL)
//    implementation(DepLibrary.UTIL_CODEX)


}

/**
 * 添加test相关的依赖dependencies
 */
fun DependencyHandler.addTestDependencies() {

    testImplementation(DepLibrary.JUNIT)
    androidTestImplementation(DepLibrary.JUNIT_KTX)

    androidTestImplementation(DepLibrary.TEST_CORE)
    androidTestImplementation(DepLibrary.TEST_CORE_KTX)
    androidTestImplementation(DepLibrary.TEST_RULES)
    androidTestImplementation(DepLibrary.TEST_RUNNER)
    androidTestImplementation(DepLibrary.ESPRESSO_CORE)

    androidTestImplementation(DepLibrary.COROUTINES_TEST)
}

/**
 * 自定义的扩展函数，便于使用implementation、api、testImplementation compileOnly、androidTestImplementation等
 * 参考 ImplementationConfigurationAccessors.kt、KaptConfigurationAccessors.kt、TestImplementationConfigurationAccessors.kt
 * 等等 gradle-kotlin-dsl-accessor中定义的
 */
fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

private fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

private fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.testApi(dependencyNotation: Any): Dependency? =
    add("testApi", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

fun DependencyHandler.androidReleaseImplementation(dependencyNotation: Any): Dependency? =
    add("androidReleaseImplementation", dependencyNotation)

private fun DependencyHandler.androidTestApi(dependencyNotation: Any): Dependency? =
    add("androidTestApi", dependencyNotation)

//endregion


private fun Project.setupBaseModule(): BaseExtension {
    return extensions.getByName<BaseExtension>("android").apply {
        compileSdkVersion(compileSdkNum)
        buildToolsVersion(buildToolsNum)

        defaultConfig {
            minSdkVersion(minSdkNum)
            targetSdkVersion(targetSdkNum)

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}

fun Project.setupLibraryModule(block: LibraryExtension.() -> Unit = {}): LibraryExtension {
    return (setupBaseModule() as LibraryExtension).apply {

        libraryVariants.all {
            generateBuildConfigProvider?.configure { enabled = false }
        }

        testOptions {
            unitTests.isIncludeAndroidResources = true
        }

        buildFeatures {
            dataBinding = true
        }

        block()
    }
}

fun Project.setupAppModule(block: BaseAppModuleExtension.() -> Unit = {}): BaseAppModuleExtension {

    return (setupBaseModule() as BaseAppModuleExtension).apply {
        defaultConfig {

            resConfigs("zh", "zh_CN", "CN")

            vectorDrawables.useSupportLibrary = true

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        buildFeatures {
            dataBinding = true
        }

        block()
    }
}