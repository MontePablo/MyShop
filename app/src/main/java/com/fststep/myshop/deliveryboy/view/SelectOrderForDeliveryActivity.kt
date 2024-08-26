/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.deliveryboy.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivitySelectOrderForDeliveryBinding
import com.fststep.myshop.deliveryboy.model.OrderForDeliveryInfo
import com.fststep.myshop.deliveryboy.view.adapter.SelectOrderForDeliveryListAdapter

/**
 * Created by Jay Kulshreshtha on 16/05/21.
 */
class SelectOrderForDeliveryActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySelectOrderForDeliveryBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var selectOrderForDeliveryListAdapter: SelectOrderForDeliveryListAdapter
    private lateinit var orderForDeliveryList: MutableList<OrderForDeliveryInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_order_for_delivery)

        linearLayoutManager = LinearLayoutManager(this)
        mBinding.rvOrderForDeliveryList.layoutManager = linearLayoutManager
        orderForDeliveryList = ArrayList()
        dummyData()
        selectOrderForDeliveryListAdapter = SelectOrderForDeliveryListAdapter(this, orderForDeliveryList as ArrayList<OrderForDeliveryInfo>)
        mBinding.rvOrderForDeliveryList.adapter = selectOrderForDeliveryListAdapter

        mBinding.buttonDone.setOnClickListener {
            startActivity(Intent(this, DeliveryBoyDetailsActivity::class.java))
        }

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun dummyData() {
        orderForDeliveryList.add(OrderForDeliveryInfo("Raj Desai -  Pushpa Vatika B101", "Home Delivery", "Payment :- Received", "Accepted"))
        orderForDeliveryList.add(OrderForDeliveryInfo("Raj Desai -  Pushpa Vatika B101", "Home Delivery", "Payment :- Received", "Accepted"))
        orderForDeliveryList.add(OrderForDeliveryInfo("Raj Desai -  Pushpa Vatika B101", "Home Delivery", "Payment :- Cash on Delivery", "Accepted"))
        orderForDeliveryList.add(OrderForDeliveryInfo("Raj Desai -  Pushpa Vatika B101", "Home Delivery", "Payment :- Received", "Accepted"))
        orderForDeliveryList.add(OrderForDeliveryInfo("Raj Desai -  Pushpa Vatika B101", "Home Delivery", "Payment :- Received", "Accepted"))
    }
}
