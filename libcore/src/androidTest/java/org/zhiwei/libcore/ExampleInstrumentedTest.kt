package org.zhiwei.libcore

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("org.zhiwei.libcore", appContext.packageName)
    }

    @Test
    fun testLogKtx() {
        LogKt.v("测试看看logv的样式如何 ")
        LogKt.d("然后再看看logd的样子，在一起会怎样 ")
        LogKt.i("那么，logi也不会缺席，总要来的吧 ", tr = NullPointerException("测试Logi的抛出异常会怎样"))
        LogKt.w("未来简史，The feature time for you & You and Me ")
        LogKt.e("朝花夕拾、红楼梦、三国演义、菜根谭、围炉夜话、格言联璧、古文观止、道德经、资治通鉴、大秦帝国")
    }
}