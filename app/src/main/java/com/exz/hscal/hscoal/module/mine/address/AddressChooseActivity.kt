package com.exz.hscal.hscoal.module.mine.address

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.adapter.AddressChooseAdapter
import com.exz.hscal.hscoal.bean.AddressBean
import com.exz.hscal.hscoal.module.main.ConfirmOrderActivity.Companion.Intent_Address_Id
import com.exz.hscal.hscoal.utils.RecycleViewDivider
import com.exz.hscal.hscoal.utils.SZWUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.config.Constants
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_address_choose.*

/**
 * Created by pc on 2017/12/15.
 */

class AddressChooseActivity: BaseActivity(), OnRefreshListener {

    private var refreshState = Constants.RefreshState.STATE_REFRESH
    private var currentPage = 1
    private lateinit var mAdapter: AddressChooseAdapter<AddressBean>
    override fun initToolbar(): Boolean {
        mTitle.text = getString(R.string.address_choose_name)
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setMargin(this, header)
        SZWUtils.setPaddingSmart(mRecyclerView,10f)

        toolbar.inflateMenu(R.menu.menu_favorite_goods)
        val actionView = toolbar.menu.getItem(0).actionView
        (actionView as TextView).text=getString(R.string.address_choose_manager)
        actionView.setOnClickListener{
            startActivityForResult(Intent(this,AddressManagerActivity::class.java),100)
        }
        return false
    }

    override fun setInflateId(): Int= R.layout.activity_address_choose

    override fun init() {

        initRecycler()
        initEvent()
        SZWUtils.setRefreshAndHeaderCtrl(this,header,refreshLayout)
        refreshLayout.autoRefresh()
    }

    private fun initEvent() {
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initRecycler() {
        mAdapter = AddressChooseAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(mContext, R.color.app_bg)))
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                setResult( Activity.RESULT_OK,intent.putExtra(Intent_Address_Id,mAdapter.data[position].id)  )
                onBackPressed()
            }
        })
    }
    override fun onRefresh(refreshLayout: RefreshLayout?) {
        currentPage = 1
        refreshState = Constants.RefreshState.STATE_REFRESH
        iniData()

    }

    private fun iniData() {
        DataCtrlClass.addressChooseData(this) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onRefresh(refreshLayout)
    }
    companion object {
        var Intent_Result_Address="Intent_Result_Address"
    }

}
