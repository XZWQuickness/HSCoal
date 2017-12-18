package com.exz.hscal.hscoal.module.main

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.TextView
import com.exz.carprofitmuch.app.ToolApplication
import com.exz.hscal.hscoal.R
import com.exz.hscal.hscoal.bean.SearchBean
import com.exz.hscal.hscoal.bean.SearchBean_
import com.exz.hscal.hscoal.utils.DialogUtils
import com.exz.hscal.hscoal.widget.TagAdapter
import com.exz.hscal.hscoal.widget.TagFlowLayout
import com.szw.framelibrary.base.BaseActivity
import com.szw.framelibrary.utils.StatusBarUtil
import com.zhy.view.flowlayout.FlowLayout
import io.objectbox.Box
import kotlinx.android.synthetic.main.action_bar_search.*
import kotlinx.android.synthetic.main.ativity_serach.*
import java.util.*

/**
 * Created by pc on 2017/12/7.
 * 搜索
 */

class SerachActivity : BaseActivity(), View.OnClickListener {
    private lateinit var searchBeanBox: Box<SearchBean>
    override fun initToolbar(): Boolean {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, blurView)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        edTitle.postDelayed({
            edTitle.isFocusable = true
            edTitle.isFocusableInTouchMode = true
            edTitle.requestFocus()
            val inputManager = edTitle.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(edTitle, 0)
        }, 200)
        return false
    }

    override fun setInflateId(): Int {
        return R.layout.ativity_serach
    }

    override fun init() {
        super.init()
        initView()
        initEvent()
    }

    private fun initEvent() {
        bt_delete.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0) {
            bt_delete -> DialogUtils.deleteSearch(mContext, View.OnClickListener {
                searchBeanBox.removeAll()
                initHistoryTag(searchBeanBox.query().orderDesc(SearchBean_.date).build().find())
            })
        }
    }


    private fun initView() {
        searchBeanBox = ToolApplication.getAPP(application).boxStore.boxFor(SearchBean::class.java)

        initHistoryTag(searchBeanBox.query().orderDesc(SearchBean_.date).build().find())
        edTitle.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // do something
                val searchContent = edTitle.text.toString().trim { it <= ' ' }
                if (!TextUtils.isEmpty(searchContent)) {
                    val SearchBean = searchBeanBox.find(SearchBean_.searchContent, searchContent).firstOrNull()
                    searchBeanBox.put(if (SearchBean == null) {
                        SearchBean(searchContent, Date())
                    } else {
                        SearchBean.date = Date()
                        SearchBean
                    })
                    search(searchContent)
                }
                return@OnEditorActionListener true
            }
            false
        })

    }


    /**
     * @param list 初始化历史记录tag列表
     */
    private fun initHistoryTag(list: List<SearchBean>) {
        if (list == null || list.isEmpty()) {
            historyLay.visibility = View.GONE
            return
        }
        mHistoryTagFlow.adapter = object : TagAdapter<SearchBean>(list) {
            override fun getView(parent: FlowLayout, position: Int, searchEntity: SearchBean): View {
                val layout = View.inflate(mContext, R.layout.tag_search, null) as RelativeLayout
                val textView = layout.getChildAt(0) as TextView
                textView.text = searchEntity.searchContent
                return layout
            }
        }
        mHistoryTagFlow.setOnTagClickListener(object : TagFlowLayout.OnTagClickListener {
            override fun onTagClick(view: View, position: Int, parent: FlowLayout): Boolean {
                val searchEntity = list[position]
                searchEntity.date = Date()
                searchBeanBox.put(searchEntity)
                search(list[position].searchContent)
                return false
            }

            override fun onTagLongClick(view: View, position: Int, parent: FlowLayout): Boolean {
                val layout = view as RelativeLayout
                val img = layout.getChildAt(1)
                img.visibility = View.VISIBLE
                img.setOnClickListener {
                    val searchEntity = list[position]
                    searchBeanBox.remove(searchEntity.id)
                    initHistoryTag(searchBeanBox.query().orderDesc(SearchBean_.date).build().find())
                }
                return true
            }
        })

    }


    private fun search(searchContent: String) {
        setResult(RESULTCODE_SEARCH,Intent().putExtra(Intent_Search_Content,searchContent))
        finish()
    }

    companion object {
        val Intent_Search_Content = "Intent_Search_Content"
        val RESULTCODE_SEARCH = 1000
    }
}
