package com.exz.hscal.hscoal.module.mine.address;

import android.app.Activity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exz.carprofitmuch.config.Urls
import com.exz.hscal.hscoal.DataCtrlClass
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.AddressBean
import com.exz.hscal.hscoal.module.mine.address.AddressAddOrUpdateActivity.Companion.INTENT_IS_DELETE
import com.exz.hscal.hscoal.utils.DialogUtils
import com.exz.hscal.hscoal.utils.SZWUtils
import com.exz.hscoal.adapter.AddressManagerAdapter
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.RecycleViewDivider
import com.szw.framelibrary.utils.StatusBarUtil
import kotlinx.android.synthetic.main.action_bar_custom.*
import kotlinx.android.synthetic.main.activity_address_manager.*

/**
 * on 2017/10/17.
 */
class AddressManagerActivity : BaseActivity() {

    private lateinit var mAdapter: AddressManagerAdapter<AddressBean>
    override fun initToolbar(): Boolean {
        mTitle.text = getString(R.string.address_manager_name)
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
        StatusBarUtil.setPaddingSmart(this, blurView)
        StatusBarUtil.setMargin(this, header)
        SZWUtils.setPaddingSmart(mRecyclerView, 10f)
        return false
    }

    override fun setInflateId(): Int = R.layout.activity_address_manager

    override fun init() {
        initRecycler()
        initEvent()
        initData()
    }

    private fun initData() {
        DataCtrlClass.addressChooseData(this) {
            refreshLayout?.finishRefresh()
            if (it != null) {
                mAdapter.setNewData(it)
                mAdapter.loadMoreEnd()
            }
        }
    }

    private fun initEvent() {
        toolbar.setNavigationOnClickListener { finish() }
        bt_submit.setOnClickListener {
            val intent = Intent(this, AddressAddOrUpdateActivity::class.java)
            intent.putExtra(INTENT_IS_DELETE,mAdapter.data.size>0)
            startActivityForResult(intent, 100)
        }
    }

    private fun initRecycler() {
        mAdapter = AddressManagerAdapter()
        mAdapter.bindToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addItemDecoration(RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(mContext, R.color.app_bg)))
        mRecyclerView.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
                var entity=mAdapter.data.get(position)
                when (view.id) {
                    R.id.bt_edit -> {
                        val intent = Intent(mContext, AddressAddOrUpdateActivity::class.java)
                        intent.putExtra(AddressAddOrUpdateActivity.Intent_AddressId, mAdapter.data[position].id)
                        intent.putExtra(INTENT_IS_DELETE,true)
                        startActivityForResult(intent, 100)
                    }
                    R.id.bt_delete -> {//删除
                        DialogUtils.delete(mContext) {
                            DataCtrlClass.deleteAddressData(mContext,  entity.id) {
                                initData()
                            }
                        }
                    }
                    R.id.radioButton -> {//设置默认地址
                        DataCtrlClass.setAddressData(mContext, entity.id) {
                                initData()

                        }

                    }
                }
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode)
            initData()
    }
}