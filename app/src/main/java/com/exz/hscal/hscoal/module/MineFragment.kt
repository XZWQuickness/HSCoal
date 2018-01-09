package com.exz.hscal.hscoal.module

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.login.LoginActivity
import com.exz.hscal.hscoal.module.login.LoginActivity.Companion.RESULT_LOGIN_CANCELED
import com.exz.hscal.hscoal.module.mine.ApplyForDevelopersActivity
import com.exz.hscal.hscoal.module.mine.ApplyForDriverActivity
import com.exz.hscal.hscoal.module.mine.SettingActivity
import com.exz.hscal.hscoal.module.mine.UserInfoActivity
import com.exz.hscal.hscoal.module.mine.address.AddressManagerActivity
import com.exz.hscal.hscoal.module.mine.goodsmanage.GoodsManageActivity
import com.exz.hscal.hscoal.module.mine.inquiry.InquiryManageActivity
import com.exz.hscal.hscoal.module.mine.inquiryo.InquiryOrderActivity
import com.exz.hscal.hscoal.module.mine.myorder.MyOrderActivity
import com.exz.hscal.hscoal.module.mine.sellorder.SellOrderActivity
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.config.PreferencesService
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.toast

/**
 * Created by pc on 2017/12/4.
 */

class MineFragment : MyBaseFragment(), View.OnClickListener, OnRefreshListener {


    private var mOffset = 0
    private var mScrollY = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false)
        return rootView
    }

    override fun initView() {
        initBar()

        refreshLayout.setOnRefreshListener(this)
    }

    private fun initBar() {
        //状态栏透明和间距处理
        mTitle.text = "个人中心"
        //状态栏透明和间距处理
        StatusBarUtil.setPaddingSmart(context, toolbar)
        StatusBarUtil.setPaddingSmart(context, blurView)
        toolbar.navigationIcon = null
        refreshLayout.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderPulling(header: RefreshHeader?, percent: Float, offset: Int, bottomHeight: Int, extendHeight: Int) {
                mOffset = offset / 2
                parallax.translationY = (mOffset - mScrollY).toFloat()
                toolbar.alpha = 1 - Math.min(percent, 1f)
            }

            override fun onHeaderReleasing(header: RefreshHeader?, percent: Float, offset: Int, bottomHeight: Int, extendHeight: Int) {
                mOffset = offset / 2
                parallax.translationY = (mOffset - mScrollY).toFloat()
                toolbar.alpha = 1 - Math.min(percent, 1f)
            }
        })
        mScrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            private var lastScrollY = 0
            private val h = DensityUtil.dp2px(170f)
            override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                var scrollNewY = scrollY
                if (lastScrollY < h) {
                    scrollNewY = Math.min(h, scrollNewY)
                    mScrollY = if (scrollNewY > h) h else scrollNewY
                    parallax.translationY = (mOffset - mScrollY).toFloat()
                    blurView.alpha = 1f * mScrollY / h
                    buttonBarLayout.alpha = 1f * mScrollY / h
                }
                lastScrollY = scrollNewY

            }
        })
        buttonBarLayout.alpha = 0f
        blurView.alpha = 0f
    }

    override fun onResume() {
        super.onResume()
        if (!MyApplication.checkUserLogin()) {
           nickname.text="未登录"
            return
        }
        refreshLayout.autoRefresh()
    }

    override fun initEvent() {
        bt_userinfo.setOnClickListener(this)
        bt_manage_address.setOnClickListener(this)
        my_order.setOnClickListener(this)
        tv_inquiry.setOnClickListener(this)
        tv_inquiry_order.setOnClickListener(this)
        tv_goods_manage.setOnClickListener(this)
        tv_sell_order.setOnClickListener(this)
        tv_applyfor_developers.setOnClickListener(this)
        tv_applyfor_driver.setOnClickListener(this)
        bt_setting.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.bt_userinfo -> {
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), RESULT_LOGIN_CANCELED)
                    return
                }
                startActivity(Intent(context, UserInfoActivity::class.java))
            }
            R.id.my_order -> {//我的订单
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), RESULT_LOGIN_CANCELED)
                    return
                }
                startActivity(Intent(context, MyOrderActivity::class.java))
            }
            R.id.tv_inquiry -> {//询盘信息管理
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), RESULT_LOGIN_CANCELED)
                    return
                }
                startActivity(Intent(context, InquiryManageActivity::class.java))
            }
            R.id.tv_inquiry_order -> {//询盘订单
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), RESULT_LOGIN_CANCELED)
                    return
                }
                startActivity(Intent(context, InquiryOrderActivity::class.java))
            }

            R.id.tv_goods_manage -> {//商品管理
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), RESULT_LOGIN_CANCELED)
                    return
                }
                startActivity(Intent(context, GoodsManageActivity::class.java))
            }
            R.id.tv_sell_order -> {//已售订单
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), RESULT_LOGIN_CANCELED)
                    return
                }
                startActivity(Intent(context, SellOrderActivity::class.java))
            }
            R.id.tv_applyfor_developers -> {//申请开发商
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), RESULT_LOGIN_CANCELED)
                    return
                }
                if(businessAuthentication.equals("0")||businessAuthentication.equals("1")){ //审核中 审核通过
                    context.toast(if(businessAuthentication.equals("0")) "供应商审核中" else "供应商已认证")
                    return
                }
                startActivity(Intent(context, ApplyForDevelopersActivity::class.java).putExtra(ApplyForDevelopersActivity.Intent_State,businessAuthentication))
            }
            R.id.tv_applyfor_driver -> {//申请司机
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), RESULT_LOGIN_CANCELED)
                    return
                }
                if(driverAuthentication.equals("0")||driverAuthentication.equals("1")){ //审核中 审核通过
                    context.toast(if(driverAuthentication.equals("0")) "司机认证审核中" else "司机认证已认证")
                    return
                }
                startActivity(Intent(context, ApplyForDriverActivity::class.java).putExtra(ApplyForDriverActivity.Intent_State,driverAuthentication))
            }
            R.id.bt_manage_address -> {//管理收货地址
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), RESULT_LOGIN_CANCELED)
                    return
                }
                startActivity(Intent(context, AddressManagerActivity::class.java))
            }
            R.id.bt_setting -> {//设置
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(context, LoginActivity::class.java), RESULT_LOGIN_CANCELED)
                    return
                }
                startActivity(Intent(context, SettingActivity::class.java))
            }


        }
    }

    private fun getUserInfo() {
        if (!MyApplication.checkUserLogin()) {
            startActivityForResult(Intent(context, LoginActivity::class.java), RESULT_LOGIN_CANCELED)
                nickname.text="未登录"
            return
        }
        DataCtrlClass.getUserInfo(context) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                img_head.setImageURI(it.headImg)
                nickname.text=it.nickname
                businessAuthentication=it.businessAuthentication
                driverAuthentication=it.driverAuthentication
            }

        }
    }
    companion object {
        var businessAuthentication=""  //供应商认证：-1未申请 0待审核，1已认证 2未通过"
        var driverAuthentication="" //司机认证：-1未申请 0待审核，1已认证 2未通过"
        fun newInstance(): MineFragment {
            val bundle = Bundle()
            val fragment = MineFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        refreshlayout?.finishRefresh()
        getUserInfo()
    }
}