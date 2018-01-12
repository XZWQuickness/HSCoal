package com.exz.hscal.hscoal.utils

import com.exz.hscal.hscoal.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*

/**
 * Created by pc on 2018/1/12.
 */

class MapWebViewActivity : BaseActivity() {
    override fun initToolbar(): Boolean {
        mTitle.text = "设置详细地址"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_map_webview
    }

    override fun init() {
        super.init()
        initView()
    }

    private fun initView() {
    }
}
