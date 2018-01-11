package com.exz.hscal.hscoal.module.main.goods

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.SeekGoodsListAdapter
import com.exz.hscal.hscoal.bean.SeekGoodsEntity
import com.exz.hscal.hscoal.bean.TabEntity
import com.exz.hscal.hscoal.utils.SZWUtils
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_seek_goods.*

/**
 * Created by pc on 2017/12/12.
 */

class SeekGoodsActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    private var refreshState = com.szw.framelibrary.config.Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1

    private var type = ""//类型：1煤炭 2有色金属


    private lateinit var mAdapter: SeekGoodsListAdapter
    private val mTitles = arrayOf("全部", "煤炭", "金属")
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()
    override fun initToolbar(): Boolean {
        mTitle.text = "找货源"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setMargin(this, header)
        SZWUtils.setPaddingSmart(mRecyclerView, 55f)
        SZWUtils.setMargin(header, 55f)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.activity_seek_goods
    }

    override fun init() {
        super.init()
        initTabBar()
        initRecycler()
        refreshLayout.autoRefresh()
    }


    private fun initTabBar() {
        mTitles.indices.mapTo(mTabEntities) { TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it]) }
        mainTabBar.setTabData(mTabEntities)
        mainTabBar.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabReselect(position: Int) {
            }

            override fun onTabSelect(position: Int) {
                when (position) {
                    0 -> {
                        type = ""
                    }
                    1 -> {
                        type = "1"
                    }
                    2 -> {
                        type = "2"
                    }
                }
                onRefresh(refreshLayout)
            }
        })
    }

    private fun initRecycler() {
        mAdapter = SeekGoodsListAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 15, ContextCompat.getColor(mContext, R.color.app_bg)))
        refreshLayout.setOnRefreshListener(this)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var mEntity = mAdapter.data.get(position)
                //类型：1煤炭 2有色金属
                startActivity(Intent(mContext, SeekGoodsDetailActivity::class.java).
                        putExtra(SeekGoodsDetailActivity.Intent_Order_Id, mEntity.id).
                        putExtra(SeekGoodsDetailActivity.Intent_Order_Type, mEntity.type))
            }
        })
        refreshLayout.setOnRefreshListener(this)
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
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
        DataCtrlClass.waitDeliveryOrders(mContext, currentPage, type) {
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
