package com.exz.hscal.hscoal.module.mine.inquiryo

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.carprofitmuch.config.Urls
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.InquiryOrderListAdapter
import com.exz.hscal.hscoal.bean.TabEntity
import com.exz.hscal.hscoal.utils.SZWUtils
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_inquiry.*

/**
 * Created by pc on 2017/12/14.
 * 卖家
 */

class InquiryOrderFragment : MyBaseFragment(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private val mSubTitles = arrayOf("报价中", "已确认", "已拒绝", "已过期")
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()
    private lateinit var mAdapter: InquiryOrderListAdapter
    private var url=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_inquiry, container, false)
        return rootView
    }

    override fun initView() {
        initToolbar()
        initRecycler()
        refreshLayout.autoRefresh()
    }

    private fun initToolbar() {
        SZWUtils.setPaddingSmart(mRecyclerView, 55f)
        SZWUtils.setMargin(header, 55f)
        StatusBarUtil.setPaddingSmart(activity, mRecyclerView)
        StatusBarUtil.setMargin(activity, header)
        mSubTitles.indices.mapTo(mTabEntities) { TabEntity(mSubTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        mainTabBar.setTabData(mTabEntities)
        mainTabBar.setOnTabSelectListener(object :OnTabSelectListener{
            override fun onTabSelect(position: Int) {
               onRefresh(refreshLayout)
            }

            override fun onTabReselect(position: Int) {
            }
        })
    }

    private fun initRecycler() {
        mAdapter = InquiryOrderListAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.addItemDecoration(RecycleViewDivider(activity, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(activity, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var mEntity=mAdapter.data.get(position)
                when (arguments.getInt(Fragment_Type)) {
                    1 -> {//煤炭

                        startActivity(Intent(context, InquiryOrderCocalDetailActivity::class.java).
                                putExtra(InquiryOrderCocalDetailActivity.Intent_State, mEntity.state).
                        putExtra(InquiryOrderCocalDetailActivity.Intent_Id,mEntity.id))
                    }
                    2 -> {//金属

                        startActivity(Intent(context, InquiryOrderSteelDetailActivity::class.java).
                                putExtra(InquiryOrderSteelDetailActivity.Intent_State, mEntity.state)
                                .putExtra(InquiryOrderSteelDetailActivity.Intent_Id,mEntity.id))
                    }
                }

            }
        })

    }


    companion object {
        var Fragment_Type = "type"
        fun newInstance(type: Int): InquiryOrderFragment {
            val bundle = Bundle()
            bundle.putInt(Fragment_Type, type)
            val fragment = InquiryOrderFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout?) {
        currentPage = 1
        refreshState = com.szw.framelibrary.config.Constants.RefreshState.STATE_REFRESH
        iniData()

    }

    override fun onLoadMoreRequested() {
        refreshState = com.szw.framelibrary.config.Constants.RefreshState.STATE_LOAD_MORE
        iniData()
    }

    private fun iniData() {

        if(arguments.getInt(Fragment_Type)==1){
            url=Urls.CoalQuote
        }else{
            url=Urls.SteelQuote
        }

        DataCtrlClass.InquiryOrderListData(context, currentPage,mainTabBar.currentTab.toString(),url) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                if (refreshState == com.szw.framelibrary.config.Constants.RefreshState.STATE_REFRESH) {
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