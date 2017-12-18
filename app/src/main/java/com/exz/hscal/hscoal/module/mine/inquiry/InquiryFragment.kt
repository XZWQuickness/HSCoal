package com.exz.hscal.hscoal.module.mine.inquiry

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.InquiryListAdapter
import com.exz.hscal.hscoal.bean.InquiryOrderBean
import com.exz.hscal.hscoal.bean.TabEntity
import com.exz.hscal.hscoal.utils.SZWUtils
import com.flyco.tablayout.listener.CustomTabEntity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.MyBaseFragment
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_inquiry.*

/**
 * Created by pc on 2017/12/14.
 */

class InquiryFragment : MyBaseFragment(), OnRefreshListener {
    private val mSubTitles = arrayOf("询价中", "已确认", "已过期", "审核中", "审核通过")
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()
    private lateinit var mAdapter: InquiryListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_inquiry, container, false)
        return rootView
    }

    override fun initView() {
        initToolbar()
        initRecycler()
    }

    private fun initToolbar() {
        SZWUtils.setPaddingSmart(mRecyclerView, 55f)
        SZWUtils.setMargin(header, 55f)
        StatusBarUtil.setPaddingSmart(activity, mRecyclerView)
        StatusBarUtil.setMargin(activity, header)
        mSubTitles.indices.mapTo(mTabEntities) { TabEntity(mSubTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        mainTabBar.setTabData(mTabEntities)
    }

    private var data = ArrayList<InquiryOrderBean>()
    private fun initRecycler() {
        data.add(InquiryOrderBean("1"))
        data.add(InquiryOrderBean("1"))
        data.add(InquiryOrderBean("1"))
        data.add(InquiryOrderBean("1"))
        data.add(InquiryOrderBean("1"))
        mAdapter = InquiryListAdapter()
        mAdapter.setHeaderAndEmpty(true)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter.setNewData(data)
        mAdapter.loadMoreEnd()
        mRecyclerView.addItemDecoration(RecycleViewDivider(activity, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(activity, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                when (arguments.getInt(Fragment_Type)) {
                    1 -> {//煤炭
                        startActivity(Intent(context, InquiryCocalDetailActivity::class.java))
                    }
                    2 -> {//金属

                        startActivity(Intent(context, InquirySteelDetailActivity::class.java))
                    }
                }

            }
        })

    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        refreshlayout?.finishRefresh()
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
}
