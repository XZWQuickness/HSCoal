package com.exz.hscal.hscoal.module.main.release

import android.content.Intent
import android.view.View
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.enquiry.EnquiryCocalActivity
import com.exz.hscal.hscoal.module.main.enquiry.EnquirySteelActivity
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_release_goods.*

/**
 * Created by pc on 2017/12/7.
 * 发布卖品
 */

class ReleaseGoodsActivity : BaseActivity(), View.OnClickListener {


    override fun initToolbar(): Boolean {
        mTitle.text = intent.getStringExtra(Intent_Class_Name)
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, ll_lay)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_release_goods
    }

    override fun init() {
        super.init()
        tv_cocal.setOnClickListener(this)
        tv_metals.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            tv_cocal -> {
                if (intent.getStringExtra(Intent_Class_Name).equals("发布询价")) {

                    startActivity(Intent(mContext, EnquiryCocalActivity::class.java))
                } else if (intent.getStringExtra(Intent_Class_Name).equals("发布卖品")) {

                    startActivity(Intent(mContext, ReleaseCocalActivity::class.java))
                }
            }
            tv_metals -> {
                if (intent.getStringExtra(Intent_Class_Name).equals("发布询价")) {

                    startActivity(Intent(mContext, EnquirySteelActivity::class.java))
                } else if (intent.getStringExtra(Intent_Class_Name).equals("发布卖品")) {

                    startActivity(Intent(mContext, ReleaseSteelActivity::class.java))
                }

            }
        }
    }


    companion object {
        var Intent_Class_Name = "ClassName"
    }
}
