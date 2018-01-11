package com.exz.hscal.hscoal.module.mine.sellorder

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bigkoo.pickerview.TimePickerView
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.carprofitmuch.config.Urls
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.SellOrderAdapter
import com.exz.hscal.hscoal.bean.CargoListBean
import com.exz.hscal.hscoal.bean.TabEntity
import com.exz.hscal.hscoal.utils.SZWUtils
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.DialogUtils
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_sell_order.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pc on 2017/12/14.
 */

class SellOrderFragment : MyBaseFragment(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    lateinit var mTimePicker: TimePickerView
    var sendTime = ""
    var orderId = ""
    private var url = ""
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private val mSubTitles = arrayOf("未付款", "已付款")
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()
    private lateinit var mAdapter: SellOrderAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_sell_order, container, false)
        return rootView
    }

    override fun initView() {
        initToolbar()
        initRecycler()

        initOptionPicker()
    }

    override fun onResume() {
        super.onResume()
        refreshLayout.autoRefresh()
    }

    private fun initToolbar() {
        SZWUtils.setPaddingSmart(mRecyclerView, 55f)
        SZWUtils.setMargin(header, 55f)
        StatusBarUtil.setPaddingSmart(activity, mRecyclerView)
        StatusBarUtil.setMargin(activity, header)
        mSubTitles.indices.mapTo(mTabEntities) { TabEntity(mSubTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        mainTabBar.setTabData(mTabEntities)
        mainTabBar.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabReselect(position: Int) {
            }

            override fun onTabSelect(position: Int) {
                onRefresh(refreshLayout)
            }
        })
    }

    private fun initOptionPicker() {
        mTimePicker = TimePickerView(context, TimePickerView.Type.YEAR_MONTH_DAY)
        mTimePicker.setRange(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.YEAR) + 20)
        mTimePicker.setTime(Calendar.getInstance().time)
        mTimePicker.setTitle("发货时间")
        mTimePicker.setOnTimeSelectListener {
            if (it != null) {
                val format = SimpleDateFormat("yyyy-MM-dd")
                sendTime = format.format(it)
                DataCtrlClass.confirmPay(context,orderId , sendTime, {
                    if (it != null) {
                        refreshLayout.autoRefresh()

                    }
                })
            }
        }
    }

    private fun initRecycler() {
        mAdapter = SellOrderAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.addItemDecoration(RecycleViewDivider(activity, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(activity, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var mEntity = mAdapter.data.get(position)
                when (arguments.getInt(Fragment_Type)) {
                    1 -> {//煤炭
                        startActivity(Intent(context, SellOrderCocalDetailActivity::class.java)
                                .putExtra(SellOrderCocalDetailActivity.Intent_Order_Detaile_Id,mEntity.orderId).
                                putExtra(SellOrderCocalDetailActivity.Intent_Order_Detaile_Type,mainTabBar.currentTab+1))
                    }
                    2 -> {//金属

                        startActivity(Intent(context, SellOrderSteelDetailActivity::class.java) .putExtra(SellOrderSteelDetailActivity.Intent_Id,mEntity.orderId).
                                putExtra(SellOrderSteelDetailActivity.Intent_State,mainTabBar.currentTab+1))
                    }
                }

            }

            override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
                super.onItemChildClick(adapter, view, position)
                var mEntity = mAdapter.data.get(position)
                when (view.id) {
                    R.id.tv_left -> {//确认付款
                        orderId=mEntity.orderId
                        KeyboardUtils.hideSoftInput(activity)
                        mTimePicker.show()
                    }
                    R.id.tv_right -> {
                        DialogUtils.Call(context as BaseActivity, mEntity.mobile)
                    }
                }
            }
        })


    }


    companion object {
        var Fragment_Type = "type"
        fun newInstance(type: Int): SellOrderFragment {
            val bundle = Bundle()
            bundle.putInt(Fragment_Type, type)
            val fragment = SellOrderFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        currentPage = 1
        refreshState = Constants.RefreshState.STATE_REFRESH
        iniData()

    }

    override fun onLoadMoreRequested() {
        refreshState = Constants.RefreshState.STATE_LOAD_MORE
        iniData()
    }

    private fun iniData() {
        if (arguments.getInt(Fragment_Type) == 1) {
            url = Urls.CoalOrderSeller
        } else {
            url = Urls.SteelOrderSeller
        }
        DataCtrlClass.sellerOrderListData(context, currentPage, mainTabBar.currentTab.toString(), url) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == Constants.RefreshState.STATE_REFRESH) {
                    mAdapter.setNewData(it)
                } else {
                    mAdapter.addData(it)

                }
                if (it.isNotEmpty()) {
                    mAdapter.loadMoreComplete()
                    currentPage++
                } else {
                    mAdapter.loadMoreEnd()
                }
            } else {
                mAdapter.loadMoreFail()
            }
        }
    }

}