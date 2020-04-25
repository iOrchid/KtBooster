plugins {
	id("com.android.library")
	kotlin("android")
	kotlin("android.extensions")
	kotlin("kapt")//使用dataBinding或 kotlin的注解功能时候，就需要这个
	id("androidx.navigation.safeargs.kotlin")//androidx navigation传参插件 也可以不带.kotlin（该插件引起一个变异warn getApplicationIdTextResource的api废弃问题）
	//2、发布到jitpack.io的步骤2,似乎所有依赖module都要配置
	id("com.github.dcendents.android-maven")
}

android {
	compileSdkVersion(compileSdkNum)
	buildToolsVersion(buildToolsNum)

	defaultConfig {
		minSdkVersion(minSdkNum)
		targetSdkVersion(targetSdkNum)
		versionCode = libCode
		versionName = libVersion

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
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
//	buildFeatures.dataBinding = true
}

dependencies {
	implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
	api(project(mapOf("path" to ":libnet")))
	api(project(mapOf("path" to ":libui")))
	api(project(mapOf("path" to ":libcore")))

	//region base dependencies
	addCoreDependencies()
	//endregion

	//<editor-folder desc="jetPack libs">
	addJetPackDependencies()
	//</editor-folder>


	//<editor-folder desc="test libs">
	addTestDependencies()
	//</editor-folder>
}