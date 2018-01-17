package com.exz.hscal.hscoal.module

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.exz.carprofitmuch.config.Urls
import com.exz.hscal.hscoal.R
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * Created by pc on 2017/12/4.
 */

class NewsFragment : MyBaseFragment() {
    private var currentProgress: Int = 0
    private var isAnimStart = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_news, container, false)
        return rootView
    }

    override fun initView() {
        initBar()
        initWeb()
    }

    private fun initWeb() {
        mWebView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键
                        mWebView.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false
            }
        })
        //启用数据库
        val webSettings = mWebView.getSettings()
        webSettings.setDatabaseEnabled(true)
        val dir = context.getDir("database", Context.MODE_PRIVATE).getPath()
//启用地理定位
        webSettings.setGeolocationEnabled(true)
//设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(dir)
        webSettings.javaScriptEnabled = true
//最重要的方法，一定要设置，这就是出不来的主要原因
        webSettings.setDomStorageEnabled(true)
        this.mWebView.setBackgroundColor(ContextCompat.getColor(context, R.color.app_bg))
        mWebView.loadUrl(Urls.NewsList)
//        mWebView.loadUrl("http://www.baidu.com")
        mWebView.webChromeClient = object : com.tencent.smtt.sdk.WebChromeClient() {
            override fun onProgressChanged(view: com.tencent.smtt.sdk.WebView, newProgress: Int) {
                currentProgress = mProgressBar.getProgress()
                if (newProgress >= 100 && !isAnimStart) {
                    isAnimStart = true
                    mProgressBar.setProgress(newProgress)
                    startDismissAnimation(mProgressBar.getProgress())
                } else {
                    startProgressAnimation(newProgress)
                }


            }

        }
        mWebView.webViewClient = object : com.tencent.smtt.sdk.WebViewClient() {
            override fun shouldOverrideUrlLoading(view: com.tencent.smtt.sdk.WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

        }
    }


    public fun OnKeyListener(){
        mWebView.goBack()  //后退
    }

    private fun startDismissAnimation(progress: Int) {
        val anim = ObjectAnimator.ofFloat(this.mProgressBar, "alpha", 1.0f, 0.0f)
        anim.duration = 1500L
        anim.interpolator = DecelerateInterpolator()
        anim.addUpdateListener { valueAnimator ->
            val fraction = valueAnimator.animatedFraction
            val offset = 100 - progress
            mProgressBar.setProgress((progress.toFloat() + offset.toFloat() * fraction).toInt())
        }
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mProgressBar.setProgress(0)
                mProgressBar.setVisibility(View.GONE)
                isAnimStart = false
            }
        })
        anim.start()
    }


    private fun startProgressAnimation(newProgress: Int) {
        val animator = ObjectAnimator.ofInt(this.mProgressBar, "progress", this.currentProgress, newProgress)
        animator.duration = 300L
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    private fun initBar() {
        toolbar.navigationIcon = null
        mTitle.text = "信息资讯"
        mTitle.setTextColor(ContextCompat.getColor(context, R.color.White))
        //状态栏透明和间距处理
        StatusBarUtil.immersive(activity)
        StatusBarUtil.setPaddingSmart(activity, toolbar)
        StatusBarUtil.setPaddingSmart(activity, blurView)

    }


    companion object {
        fun newInstance(): NewsFragment {
            val bundle = Bundle()
            val fragment = NewsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}