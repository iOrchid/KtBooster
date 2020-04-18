import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年03月23日 16:09
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 依赖管理的相关扩展函数、
 */


/**
 * 添加平台相关的基础依赖dependencies
 */
fun DependencyHandler.addCoreDependencies() {
    implementation(DepLibrary.KOTLIN_LIB)
    implementation(DepLibrary.KOTLIN_LIB_JDK)
    implementation(DepLibrary.KOTLIN_LIB_REFLECT)
    implementation(DepLibrary.APPCOMPAT)
    implementation(DepLibrary.CORE_KTX)
    implementation(DepLibrary.COROUTINES_ANDROID)
    implementation(DepLibrary.COROUTINES_CORE)
    implementation(DepLibrary.ACTIVITY_KTX)
    implementation(DepLibrary.FRAGMENT_KTX)
    implementation(DepLibrary.MATERIAL)
    implementation(DepLibrary.RECYCLER_VIEW)
    implementation(DepLibrary.CONSTRAINT_LAYOUT)
}


/**
 * 添加JetPack相关的依赖dependencies
 */
fun DependencyHandler.addJetPackDependencies() {
    kapt(DepLibrary.LIFECYCLE_COMMON_JAVA8)
    implementation(DepLibrary.LIFECYCLE_LIVEDATA)
    implementation(DepLibrary.LIFECYCLE_LIVEDATA_KTX)
    implementation(DepLibrary.LIFECYCLE_RUNTIME)
    implementation(DepLibrary.LIFECYCLE_VIEWMODEL)
    implementation(DepLibrary.LIFECYCLE_VIEWMODEL_KTX)

    implementation(DepLibrary.ROOM_COMMON)
    kapt(DepLibrary.ROOM_COMPILER)
    implementation(DepLibrary.ROOM_KTX)
    implementation(DepLibrary.ROOM_RUNTIME)

    implementation(DepLibrary.PAGING_COMMON)
    implementation(DepLibrary.PAGING_COMMON_KTX)
    implementation(DepLibrary.PAGING_RUNTIME)
    implementation(DepLibrary.PAGING_RUNTIME_KTX)

    implementation(DepLibrary.WORK_RUNTIME)
    implementation(DepLibrary.WORK_RUNTIME_KTX)

    implementation(DepLibrary.NAVIGATION_FRAGMENT)
    implementation(DepLibrary.NAVIGATION_FRAGMENT_KTX)
    implementation(DepLibrary.NAVIGATION_RUNTIME)
    implementation(DepLibrary.NAVIGATION_UI)
    implementation(DepLibrary.NAVIGATION_UI_KTX)
}

/**
 * 添加常用的 Github等第三方依赖dependencies
 * 如okHttp、Gson、Retrofit、glide 等等
 */
fun DependencyHandler.addGithubDependencies() {

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
private fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
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

private fun DependencyHandler.androidTestApi(dependencyNotation: Any): Dependency? =
    add("androidTestApi", dependencyNotation)