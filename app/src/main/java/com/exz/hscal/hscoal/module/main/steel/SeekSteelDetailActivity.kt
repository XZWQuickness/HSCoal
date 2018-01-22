package com.exz.hscal.hscoal.module.main.steel

import android.content.Intent
import android.support.v4.widget.NestedScrollView
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.module.login.LoginActivity
import com.exz.hscal.hscoal.module.main.ConfirmOrderActivity
import com.exz.hscal.hscoal.module.main.coal.SeekCocalDetailActivity
import com.exz.hscal.hscoal.pop.SelectGoodsTypePop
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.szw.framelibrary.app.MyApplication
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import com.szw.framelibrary.view.preview.PreviewActivity
import kotlinx.android.synthetic.main.action_bar_search.*
import kotlinx.android.synthetic.main.activity_seek_steel_detail.*
import org.jetbrains.anko.toast

/**
 * Created by pc on 2017/12/7.
 * 找钢材详情
 */

class SeekSteelDetailActivity : BaseActivity(), OnRefreshListener, View.OnClickListener {

    private var inspectonBody_Img = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=398952501,2656845064&fm=27&gp=0.png"

    private lateinit var pop: SelectGoodsTypePop
    private var mOffset = 0
    private var mScrollY = 0
    override fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(mContext, toolbar)
        StatusBarUtil.setPaddingSmart(mContext, blurView)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.inflateMenu(R.menu.menu_seek_cocal_detail_text)
        val actionView = toolbar.menu.getItem(0).actionView
        (actionView as TextView).text = "检验报告"
        actionView.setOnClickListener {
            if (TextUtils.isEmpty(inspectonBody_Img)) {
                toast("暂无检验报告")
                return@setOnClickListener
            }
            val intent = Intent(mContext, PreviewActivity::class.java)
            val images = ArrayList<String>()
            images.add(inspectonBody_Img)
            intent.putExtra(PreviewActivity.PREVIEW_INTENT_IMAGES, images)
            intent.putExtra(PreviewActivity.PREVIEW_INTENT_SHOW_NUM, false)
            startActivity(intent)
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_seek_steel_detail
    }

    override fun init() {
        super.init()
        initView()
        refreshLayout.autoRefresh()
    }


    private fun initData() {

        DataCtrlClass.getSteelInfo(mContext, intent.getStringExtra(Intent_Id), {
            refreshLayout?.finishRefresh()
            if (it != null) {
                name.text = it.data?.name ?: ""
                className.text = "(" + it.data?.className + ")" ?: ""
                weight.text = String.format(mContext.getString(R.string.heavy), it.data?.weight ?: "")//件重
                QTY.text = it.data?.qty + "吨" ?: ""//供应量
                price.text = it.data?.price + "元/件" ?: ""//价格
                specification.text = it.data?.specification ?: "" //规格
                materialQuality.text = it.data?.materialQuality ?: "" //材质
                warehouse.text = it.data?.warehouse ?: "" //仓库
                paymentModeName.text = it.data?.paymentModeName ?: "" //付款方式
                inspectonBody.text = it.data?.inspectonBody ?: "" //检验机构
                deliveryTime.text = it.data?.deliveryTime?.replace(",", "至") ?: ""//交货时间
                deliveryWayName.text = it.data?.deliveryWayName ?: ""//交货方式
                provinceCity.text = it.data?.provinceCity ?: ""//交货地点
                remark.text = it.data?.remark ?: ""//备注
                if (it.data?.isChoiceDelivery.equals("0")) { //是否可以选择交货方式：0否 1是"
                    ll_lay_deliveryWay.visibility = View.GONE
                }
//                inspectonBody_Img=it.data?.inspectonBodyImg ?:""
            }
        })
    }

    private fun initView() {
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
        pop = SelectGoodsTypePop(this, {
            if (!TextUtils.isEmpty(it)) {
                var intent = Intent(mContext, ConfirmOrderActivity::class.java)
                        .putExtra(ConfirmOrderActivity.Intent_Type_Address, it)
                        .putExtra(ConfirmOrderActivity.Intent_Type, "2")
                        .putExtra(ConfirmOrderActivity.Intent_Id, intent.getStringExtra(Intent_Id))
                startActivity(intent)
                finish()
            }
        })
        bt_submit.setOnClickListener(this)
        refreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {

        initData()
    }


    override fun onClick(p0: View) {
        when (p0) {
            bt_submit -> {
                if (!MyApplication.checkUserLogin()) {
                    startActivityForResult(Intent(mContext, LoginActivity::class.java), LoginActivity.RESULT_LOGIN_CANCELED)
                    return
                }
                when (deliveryWayName.text.toString().trim()) {
                    "物流配送" -> {
                        var intent = Intent(mContext, ConfirmOrderActivity::class.java)
                                .putExtra(ConfirmOrderActivity.Intent_Type_Address, "1")
                                .putExtra(ConfirmOrderActivity.Intent_Type, "2")
                                .putExtra(ConfirmOrderActivity.Intent_Id, intent.getStringExtra(Intent_Id))
                        startActivity(intent)
                        finish()
                    }
                    "到场自提" -> {
                        var intent = Intent(mContext, ConfirmOrderActivity::class.java)
                                .putExtra(ConfirmOrderActivity.Intent_Type_Address, "2")
                                .putExtra(ConfirmOrderActivity.Intent_Type, "2")
                                .putExtra(ConfirmOrderActivity.Intent_Id, intent.getStringExtra(Intent_Id))
                        startActivity(intent)
                        finish()
                    }
                    "物流配送 / 到场自提" -> {
                        pop.showPopupWindow()
                    }
                }

            }
        }
    }

    companion object {
        var Intent_Id = "id"
    }
}