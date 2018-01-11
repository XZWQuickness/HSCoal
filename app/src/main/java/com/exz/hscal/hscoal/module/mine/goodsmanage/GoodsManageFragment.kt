package com.exz.hscal.hscoal.module.mine.goodsmanage

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
import com.exz.hscal.hscoal.adapter.GoodsManageAdapter
import com.exz.hscal.hscoal.bean.CargoListBean
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
import kotlinx.android.synthetic.main.fragment_goods_manage.*

/**
 * Created by pc on 2017/12/14.
 */

class GoodsManageFragment : MyBaseFragment(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private var classType = 1 //1 煤炭 2 有色金属
    private var state = 1 //状态：0审核中 1已通过 2未通过
    private var url = Urls.CoalSeller
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private val mSubTitles = arrayOf("已通过", "审核中", "未通过")
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()
    private lateinit var mAdapter: GoodsManageAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_goods_manage, container, false)
        return rootView
    }

    override fun initView() {
        initToolbar()
        initRecycler()
    }

    override fun onResume() {
        super.onResume()
        refreshLayout.autoRefresh()
    }

    private fun initToolbar() {
        classType = arguments.getInt(Fragment_Type)
        if (classType == 1) {
            url = Urls.CoalSeller
        } else {
            url = Urls.SteelSeller
        }
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
                when (position) {
                    0 -> {
                        state = 1
                    }
                    1 -> {
                        state = 0
                    }
                    2 -> {
                        state = 2
                    }
                }
                onRefresh(refreshLayout)
            }
        })
    }

    private fun initRecycler() {
        mAdapter = GoodsManageAdapter(classType)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter.loadMoreEnd()
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.addItemDecoration(RecycleViewDivider(activity, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(activity, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var mEntity = mAdapter.data.get(position)
                when (arguments.getInt(Fragment_Type)) {
                    1 -> {//煤炭
                        startActivity(Intent(context, GoodsManageCocalDetailActivity::class.java)
                                .putExtra(GoodsManageCocalDetailActivity.Intent_Id, mEntity.id)
                                .putExtra(GoodsManageCocalDetailActivity.Intent_State, state.toString()))
                    }
                    2 -> {//金属

                        startActivity(Intent(context, GoodsManageSteelDetailActivity::class.java).
                                putExtra(GoodsManageSteelDetailActivity.Intent_Id, mEntity.id)
                                .putExtra(GoodsManageSteelDetailActivity.Intent_State, state.toString()))
                    }
                }

            }
        })

    }


    companion object {
        var Fragment_Type = "type"
        fun newInstance(type: Int): GoodsManageFragment {
            val bundle = Bundle()
            bundle.putInt(Fragment_Type, type)
            val fragment = GoodsManageFragment()
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
        DataCtrlClass.GoodsManageListData(context, currentPage, state, url) {
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