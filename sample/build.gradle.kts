import com.didichuxing.doraemonkit.plugin.extension.SlowMethodExt
import groovy.lang.Closure

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.didi.dokit")
}


setupAppModule {
    defaultConfig {

        applicationId("org.zhiwei.booster.sample")
        versionCode(100)
        versionName("1.0.0")

//        multiDexEnabled = true
    }

    buildTypes {

        getByName("debug") {
            applicationIdSuffix(".debug")
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles("shrinker-rules.pro", "shrinker-rules-android.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
//            signingConfig = signingConfigs.getByName("debug")
        }
    }

}
//dokit的调试配置
dokitExt {
    //通用设置
    comm {
        //地图经纬度开关
        gpsSwitch(true)
        //网络开关
        networkSwitch(true)
        //大图开关
        bigImgSwitch(true)
        //webView js 抓包
        webViewSwitch(true)
    }

    slowMethod {
        //0:默认模式 打印函数调用栈 需添加指定入口  默认为application onCreate 和attachBaseContext
        //1:普通模式 运行时打印某个函数的耗时 全局业务代码函数插入
        strategy(0)
        //函数功能开关
        methodSwitch(true)

        //调用栈模式配置
        stackMethod(closureOf<SlowMethodExt.StackMethodExt> {
            //默认值为 5ms 小于该值的函数在调用栈中不显示
            thresholdTime(10)
            //调用栈函数入口
            enterMethods(mutableSetOf("com.ly.bigscreen.PjApplication.onCreate"))
            //黑名单 粒度最小到类 暂不支持到方法
            methodBlacklist(mutableSetOf("com.facebook.drawee.backends.pipeline.Fresco"))
        } as? Closure<SlowMethodExt.StackMethodExt?>)

        normalMethod(closureOf<SlowMethodExt.NormalMethodExt> {
            //默认值为 500ms 小于该值的函数在运行时不会在控制台中被打印
            thresholdTime(10)
            //需要针对函数插装的包名
            packageNames(mutableSetOf("com.ly.bigscreen"))
            //不需要针对函数插装的包名&类名
            methodBlacklist(mutableSetOf("com.didichuxing.doraemondemo.dokit"))
        } as? Closure<SlowMethodExt.NormalMethodExt?>)

    }
}

dependencies {

    //这里演示了不同的kts下的添加依赖的方式
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation(kotlin("stdlib", kotlin_version))

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

    //jetpack
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

    //test dependencies，使用的是buildSrc下面的自定义扩展
    testImplementation(DepLibrary.JUNIT)
    androidTestImplementation(DepLibrary.JUNIT_KTX)

    androidTestImplementation(DepLibrary.TEST_CORE)
    androidTestImplementation(DepLibrary.TEST_CORE_KTX)
    androidTestImplementation(DepLibrary.TEST_RULES)
    androidTestImplementation(DepLibrary.TEST_RUNNER)
    androidTestImplementation(DepLibrary.ESPRESSO_CORE)

    androidTestImplementation(DepLibrary.COROUTINES_TEST)


    implementation(DepLibrary.RETROFIT)
    implementation(DepLibrary.RETROFIT_CONVERTER_GSON)
    //Bugly
    implementation(DepLibrary.BUGLY)
    //其他项目配置
    implementation(project(":booster"))

}