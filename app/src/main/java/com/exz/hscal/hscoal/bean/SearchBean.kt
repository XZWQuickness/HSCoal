package com.exz.hscal.hscoal.bean

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

/**
 * Created by pc on 2017/12/7.
 */
@Entity
open class SearchBean(var searchContent: String = "", var date: Date? = null, @Id var id: Long = 0){}
