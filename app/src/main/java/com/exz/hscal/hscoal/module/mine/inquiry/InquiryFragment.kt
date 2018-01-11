package com.exz.hscal.hscoal.module.mine.inquiry

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.carprofitmuch.config.Urls
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.InquiryListAdapter
import com.exz.hscal.hscoal.bean.InquiryOrderBean
import com.exz.hscal.hscoal.bean.TabEntity
import com.exz.hscal.hscoal.module.mine.inquiryo.InquiryOrderFragment
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
 */

class InquiryFragment : MyBaseFragment(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private val mSubTitles = arrayOf("询价中", "已确认", "已过期", "审核中", "未通过")
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()
    private lateinit var mAdapter: InquiryListAdapter
    private var url = ""
    private var state = "1"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_inquiry, container, false)
        return rootView
    }

    override fun initView() {
        initToolbar()
        initRecycler()

    }

    override fun onResume() {
        super.onResume()
        onRefresh(refreshLayout)
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
                //0审核中 1询价中 2未通过 3已过期 4已确认
                when (position) {
                    0 -> {//询价中
                        state = "1"
                    }
                    1 -> { //已确认
                        state = "4"
                    }
                    2 -> {//已过期
                        state = "3"
                    }
                    3 -> {//审核中
                        state = "0"
                    }
                    4 -> {//未通过
                        state = "2"
                    }
                }
                onRefresh(refreshLayout)
            }
        })
    }

    private fun initRecycler() {
        mAdapter = InquiryListAdapter()
        mAdapter.setHeaderAndEmpty(true)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(activity, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(activity, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var mEntity=mAdapter.data.get(position)
                when (arguments.getInt(Fragment_Type)) {
                    1 -> {//煤炭
                        startActivity(Intent(context, InquiryCocalDetailActivity::class.java).
                                putExtra(InquiryCocalDetailActivity.Intent_Id,mEntity.id)
                                .putExtra(InquiryCocalDetailActivity.Intent_Order_state,state))
                    }
                    2 -> {//金属

                        startActivity(Intent(context, InquirySteelDetailActivity::class.java) .putExtra(InquirySteelDetailActivity.Intent_Id,mEntity.id)
                                .putExtra(InquirySteelDetailActivity.Intent_Order_state,state))
                    }
                }

            }
        })
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
    }


    companion object {
        var Fragment_Type = "type"
        fun newInstance(type: Int): InquiryFragment {
            val bundle = Bundle()
            bundle.putInt(Fragment_Type, type)
            val fragment = InquiryFragment()
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

        if (arguments.getInt(InquiryOrderFragment.Fragment_Type) == 1) {
            url = Urls.CoalEnquiryManage
        } else {
            url = Urls.SteelEnquiryManage
        }

        DataCtrlClass.InquiryOrderListData(context, currentPage, state, url) {
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
