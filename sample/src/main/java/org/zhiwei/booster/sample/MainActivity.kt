package org.zhiwei.booster.sample

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.zhiwei.booster.KtActivity
import org.zhiwei.libcore.LogKt
import org.zhiwei.libnet.HttpApi
import org.zhiwei.libnet.KtHttp

class MainActivity : KtActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            LogKt.i("测试 logKt的打印栈")
            lifecycleScope.launch(Dispatchers.IO) {
                HttpApi.initConfig("http://api.qingyunke.com/api.php?key=free&appid=0&msg=你好呀，我想和你做朋友，可以吗，哈哈哈")
                    .get(mapOf("a" to "999"), "") {
                        LogKt.d("onCreate: $it")
                    }
                HttpApi.initConfig("http://api.qingyunke.com/api.php?key=free&appid=0&msg=你是谁")
                    .post(mapOf("bb" to "bb999"), "") {
                        LogKt.d("onCreate: $it")
                    }
                KtHttp.initConfig("http://api.qingyunke.com/api.php?key=free&appid=0&msg=今天天气如何")
                    .get("", mapOf("" to "")).also {
                        LogKt.d("onCreate: $it")
                    }

            }
        }
        LogKt.d("tag ddd", "测试 logKt的打印栈")

        resources.getXml(R.xml.network_config)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}