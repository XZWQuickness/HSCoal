package com.exz.hscal.hscoal.module.mine.goodsmanage
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.main.release.ReleaseSteelActivity
import com.exz.hscal.hscoal.utils.SZWUtils
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_goods_manage_steel_detail.*

/**
 * Created by pc on 2017/12/14.
 * 我的卖家 商品管理 金属详情
 */

class GoodsManageSteelDetailActivity : BaseActivity() {


    override fun initToolbar(): Boolean {
        mTitle.text = "订单详情"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, scrollView)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setMargin(this, header)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.inflateMenu(R.menu.menu_seek_cocal_detail_text)
        if (intent.getStringExtra(GoodsManageCocalDetailActivity.Intent_State).equals("3")) {
            var actionView = toolbar.menu.getItem(0).actionView
            (actionView as TextView).text = "修改"
            actionView.setOnClickListener {

                startActivity(Intent(mContext, ReleaseSteelActivity::class.java))
            }
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_goods_manage_steel_detail
    }

    override fun init() {
        super.init()
        initView()
        initEvent()
    }

    private fun initView() {

        when (intent.getStringExtra(Intent_State)) {
            "1" -> {//已通过
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "已通过")

            }
            "2" -> {//审核中
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "审核中")
            }
            "3" -> {//未通过
                tv_state.text = String.format(mContext.getString(R.string.inquriy_cocal_detail_state), "未通过")
                bt_delete.visibility= View.VISIBLE

            }
        }

        SZWUtils.matcherSearchTitle(tv_state, tv_state.text.toString().trim(), 3, tv_state.text.toString().trim().length, ContextCompat.getColor(mContext, R.color.colorPrimary))
    }

    private fun initEvent() {
    }


    companion object {
        var Intent_State="intent_state"
    }
}