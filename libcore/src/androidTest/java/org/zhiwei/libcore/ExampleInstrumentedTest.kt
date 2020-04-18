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

    @Test
    fun testSpKtx() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        SpKtx.init(context).put("a", 1)
        SpKtx.put("b", 2L)
        SpKtx.put("c", 3f)
        SpKtx.put("d", false)
        SpKtx.put("e", "eeeee")
        SpKtx.put("f", setOf("hh", "heihei", "jj"))
        SpKtx.put(mapOf("33" to 33, "ff" to "FF"))
        SpKtx.put("3333" to 3333, "ffgff" to "FggfF")

        SpKtx.get<Int, String>("a", "j").logI()
        SpKtx.get<Int, String, String>("a", "j", "e").logI()

        SpKtx.getInt("a").logD()
        SpKtx.getLong("b").logD()
        SpKtx.getFloat("c").logD()
        SpKtx.getBoolean("d").logD()
        SpKtx.getString("e")?.logD()
        SpKtx.getStringSet("f")?.logD()
        SpKtx.getAll()?.logD()
        SpKtx.remove("a", "d")
        SpKtx.has("f").logD()
        SpKtx.clear()
        SpKtx.has("f").logD()


    }

    /**
     * 内部使用扩展log函数，便利而已
     */
    private fun <T : Any> T.logD() {
        LogKt.d(this)
    }

    private fun <T : Any> T.logI() {
        LogKt.i(this)
    }
}