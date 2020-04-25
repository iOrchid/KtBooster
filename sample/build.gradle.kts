plugins {
	id("com.android.application")
	id("kotlin-android")
	id("kotlin-android-extensions")
	id("kotlin-kapt")
	id("androidx.navigation.safeargs.kotlin")
}

android {
	compileSdkVersion(compileSdkNum)
	buildToolsVersion(buildToolsNum)

	defaultConfig {
		applicationId = "org.zhiwei.booster.sample"
		minSdkVersion(minSdkNum)
		targetSdkVersion(targetSdkNum)
		versionCode = libCode
		versionName = libVersion

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
		}
	}

//AndroidStudio 4 以下使用这种方式配置dataBinding
	dataBinding {
		isEnabled = true
	}
	//AS4 以上版本使用这个配置dataBinding
//    buildFeatures.dataBinding = true

}

dependencies {
	//这里演示了不同的kts下的添加依赖的方式
	implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
	implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.72")
	//    implementation(group = "org.jetbrains.kotlinx",name = "kotlinx-coroutines-android",version = "1.3.3")
	implementation(DepLibrary.APPCOMPAT)
	implementation(DepLibrary.CORE_KTX)
	implementation(DepLibrary.COROUTINES_ANDROID)
	implementation(DepLibrary.MATERIAL)
	implementation(DepLibrary.CONSTRAINT_LAYOUT)
	implementation(DepLibrary.RECYCLER_VIEW)

	implementation(DepLibrary.NAVIGATION_FRAGMENT_KTX)
	implementation(DepLibrary.NAVIGATION_UI_KTX)
//    implementation(project(mapOf("path" to ":booster")))//module使用形式
	implementation("com.github.zhiwei1990:KtBooster:0.1.2.1")//尽管jitpack上版本号v0.1.1，这里可以不带v的

	//test dependencies，使用的是buildSrc下面的自定义扩展
	addTestDependencies()
}