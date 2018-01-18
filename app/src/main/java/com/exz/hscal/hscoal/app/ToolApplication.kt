package com.exz.carprofitmuch.app

import android.app.Application
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.exz.hscal.hscoal.bean.MyObjectBox
import com.szw.framelibrary.app.MyApplication
import io.objectbox.BoxStore
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsListener


/**
 * Created by 史忠文
 * on 2017/10/16.
 */

class ToolApplication : MyApplication() {
    lateinit var boxStore: BoxStore//数据库入口
    override fun getSaltStr(): String? = "23f2005c0334"

    override fun onCreate() {
        super.onCreate()
        init()
        //数据库初始化
        boxStore = MyObjectBox.builder().androidContext(this).build()
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
//        val cb = object : QbSdk.PreInitCallback {
//
//            override fun onViewInitFinished(arg0: Boolean) {
//                Log.i("打印日志","View是否初始化完成:" + arg0)
//            }
//
//            override fun onCoreInitFinished() {
//               Log.i("打印日志","X5内核初始化完成")
//            }
//        }
//
//        QbSdk.setTbsListener(object : TbsListener {
//            override fun onDownloadFinish(i: Int) {
//               Log.i("打印日志","腾讯X5内核 下载结束")
//            }
//
//            override fun onInstallFinish(i: Int) {
//               Log.i("打印日志","腾讯X5内核 安装完成")
//            }
//
//            override fun onDownloadProgress(i: Int) {
//               Log.i("打印日志","腾讯X5内核 下载进度:%" + i)
//            }
//        })

//        QbSdk.initX5Environment(applicationContext, cb)
    }

    companion object {
        fun getAPP(app: Application):ToolApplication = app as ToolApplication
    }
}
