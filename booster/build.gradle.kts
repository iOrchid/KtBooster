plugins {
	id("com.android.library")
	id("kotlin-android")
	id("kotlin-kapt")
	id("kotlin-parcelize")
	id("androidx.navigation.safeargs.kotlin")
	//2、发布到jitpack.io的步骤2,似乎所有依赖module都要配置
	id("com.github.dcendents.android-maven")
}

setupLibraryModule()

dependencies {
	implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
	implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")

	api(project(":libcore"))

	implementation(DepLibrary.KOTLIN_LIB)
	implementation(DepLibrary.KOTLIN_LIB_JDK)
	implementation(DepLibrary.KOTLIN_LIB_REFLECT)
	implementation(DepLibrary.COROUTINES_ANDROID)

	implementation(DepLibrary.APPCOMPAT)
	implementation(DepLibrary.CORE_KTX)
	implementation(DepLibrary.ACTIVITY_KTX)
	implementation(DepLibrary.FRAGMENT_KTX)

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



	implementation(DepLibrary.RETROFIT)
	implementation(DepLibrary.RETROFIT_CONVERTER_GSON)
	implementation(DepLibrary.GSON)

	implementation(DepLibrary.OKHTTP)
	implementation(DepLibrary.COIL)
	implementation(DepLibrary.MMKV)

	// 滴滴助手
	debugImplementation(DepLibrary.DOKIT)
	releaseImplementation(DepLibrary.DOKIT_NO_OP)

	implementation(DepLibrary.DIALOGS_CORE)
	implementation(DepLibrary.DIALOGS_INPUT)
}