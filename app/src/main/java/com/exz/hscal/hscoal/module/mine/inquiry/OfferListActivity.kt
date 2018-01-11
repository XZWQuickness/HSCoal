package com.exz.hscal.hscoal.module.mine.inquiry

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.OfferListAdapter
import com.exz.hscal.hscoal.bean.OfferBean
import com.exz.hscal.hscoal.utils.DialogUtils
import com.exz.hscal.hscoal.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_offer_list.*
import kotlinx.android.synthetic.main.item_offer_list.*
import java.util.*

/**
 * Created by pc on 2017/12/14.
 * 报价列表
 */

class OfferListActivity : BaseActivity(), OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private lateinit var mAdapter: OfferListAdapter<OfferBean>

    var type = ""
    var objectId = ""

    override fun initToolbar(): Boolean {
        mTitle.text = "报价列表"
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setMargin(this, header)
        SZWUtils.setPaddingSmart(mRecyclerView, 10f)
        return false
    }

    override fun setInflateId(): Int = R.layout.activity_offer_list

    override fun init() {
        initRecycler()
        initEvent()
    }


    private fun initEvent() {
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initRecycler() {
        type=intent.getStringExtra(Intent_Type)
        objectId=intent.getStringExtra(Intent_ObjectId)
        mAdapter = OfferListAdapter()
        SZWUtils.setRefreshAndHeaderCtrl(this, header, refreshLayout)
        mAdapter.bindToRecyclerView(mRecyclerView)
        mAdapter.setOnLoadMoreListener(this, mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(mContext, R.color.app_bg)))
        mRecyclerView.addOnItemTouchListener(object : OnItemChildClickListener(){
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                var mEntity=mAdapter.data.get(position)
                when (view) {
                    tv_left -> {
                        DialogUtils.hint(mContext,"接受报价", {
                            DataCtrlClass.ConfirmEnquiry(mContext,type,objectId,mEntity.id,"1",{
                                if(it!=null){
                                    onRefresh(refreshLayout)
                                }
                            })
                        })

                    }
                    tv_mind -> {
                        DialogUtils.hint(mContext,"拒绝报价", {
                            DataCtrlClass.ConfirmEnquiry(mContext,type,objectId,mEntity.id,"2",{
                                if(it!=null){
                                    onRefresh(refreshLayout)
                                }
                            })
                        })
                    }
                }
            }
        })
        refreshLayout.autoRefresh()
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
        DataCtrlClass.OfferListData(mContext, currentPage,type,objectId) {
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

    companion object {
        var Intent_ObjectId = "ObjectId"
        var Intent_Type = "Type"
    }

}
