package com.exz.hscal.hscoal.module.mine.goodsmanage

import android.support.v4.app.Fragment
import com.exz.hscal.hscoal.R
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_segment.*

/**
 * Created by pc on 2017/12/15.
 */

class GoodsManageActivity : BaseActivity() {
    private val mTitles = arrayOf("煤炭", "金属")

    override fun initToolbar(): Boolean {
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
        return R.layout.activity_goods_manage
    }

    override fun init() {
        super.init()
        initTabBar()

    }

    private val mFragments = ArrayList<Fragment>()

    private fun initTabBar() {


        mFragments.add(GoodsManageFragment.newInstance(1))
        mFragments.add(GoodsManageFragment.newInstance(2))
        tl_1.setTabData(mTitles,this , R.id.mFrameLayout, mFragments);
    }



}
