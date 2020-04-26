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
import org.zhiwei.libnet.KtHttp
import org.zhiwei.libnet.KtHttp.toBean

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
                val toBean = KtHttp.initConfig("http://m.pm25.com/city/")
                    .get("beijing.html").toBean(String::class.java)
//                LogKt.d("onCreate: $toBean")
            }
        }
        LogKt.d("tag ddd", "测试 logKt的打印栈")

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