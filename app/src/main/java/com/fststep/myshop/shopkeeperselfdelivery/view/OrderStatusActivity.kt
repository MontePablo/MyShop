/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.shopkeeperselfdelivery.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.fststep.myshop.R
import com.fststep.myshop.databinding.ActivityOrderStatusBinding
import com.fststep.myshop.shopkeeperselfdelivery.adapter.OrderItemListAdapter
import com.fststep.myshop.shopkeeperselfdelivery.model.OrderItemInfo
import java.util.Locale
import kotlin.collections.ArrayList

/**
 * Created by Jay Kulshreshtha on 28/05/21.
 */
class OrderStatusActivity : AppCompatActivity() {

    private val PARAM_ADRRESS = "param_address"
    private val PARAM_ORDER_TYPE = "param_order_type"
    private val PARAM_ORDER_DATE = "param_order_date"
    private val PARAM_ORDER_TIME = "param_order_time"
    private val PARAM_ORDER_STATUS = "param_order_status"
    private val PARAM_ORDER_USE_DELIVERY_PERSON = "param_order_use_delivery_person"
    private val PARAM_DELIVERY_PERSON_NAME = "param_delivery_person_name"

    private lateinit var mBinding: ActivityOrderStatusBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var orderItemListAdapter: OrderItemListAdapter
    private lateinit var orderItemList: MutableList<OrderItemInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_status)

        val extras = intent.extras
        mBinding.tvOrderAddress.text = extras!!.getString(PARAM_ADRRESS)
        mBinding.tvOrderType.text = extras.getString(PARAM_ORDER_TYPE)
        mBinding.tvOrderDate.text = extras.getString(PARAM_ORDER_DATE)
        mBinding.tvOrderTime.text = extras.getString(PARAM_ORDER_TIME)
        mBinding.tvOrderStatus.text = extras.getString(PARAM_ORDER_STATUS)

        val useDeliveryPerson = extras.getBoolean(PARAM_ORDER_USE_DELIVERY_PERSON)

        if(useDeliveryPerson) {
            val deliveryPersonName = extras.getString(PARAM_DELIVERY_PERSON_NAME)

            mBinding.layoutDeliveryPerson.visibility = View.VISIBLE
            mBinding.tvDeliveryPersonName.text = deliveryPersonName
        }

        if (mBinding.tvOrderStatus.text.contentEquals("Pending", true)) {
            mBinding.tvOrderStatus.setTextColor(Color.parseColor("#f05a28"))
            mBinding.tvDeliveryStatusText.visibility = View.GONE
            mBinding.tvDeliveryStatus.visibility = View.GONE
        } else if (mBinding.tvOrderStatus.text.contentEquals("Accepted", true)) {
            mBinding.tvOrderStatus.setTextColor(Color.parseColor("#00adee"))
            mBinding.tvDelivery.visibility = View.GONE
            mBinding.SpinnerDeliveryType.visibility = View.GONE
            mBinding.vLineDeliveryType.visibility = View.GONE
            mBinding.buttonAccept.text = getString(R.string.lbl_delivered).replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        } else if (mBinding.tvOrderStatus.text.contentEquals("Delivered", true)) {
            mBinding.tvOrderStatus.setTextColor(Color.parseColor("#00a550"))
            mBinding.tvDelivery.visibility = View.GONE
            mBinding.SpinnerDeliveryType.visibility = View.GONE
            mBinding.vLineDeliveryType.visibility = View.GONE
            mBinding.buttonReject.visibility = View.GONE
            mBinding.buttonAccept.visibility = View.GONE
        } else if (mBinding.tvOrderStatus.text.contentEquals("Rejected", true)) {
            mBinding.tvOrderStatus.setTextColor(Color.parseColor("#ec1d24"))
            mBinding.tvDelivery.visibility = View.GONE
            mBinding.SpinnerDeliveryType.visibility = View.GONE
            mBinding.vLineDeliveryType.visibility = View.GONE
            mBinding.buttonReject.visibility = View.GONE
            mBinding.buttonAccept.visibility = View.GONE
            mBinding.tvDeliveryStatusText.visibility = View.GONE
            mBinding.tvDeliveryStatus.visibility = View.GONE
            mBinding.tvPaymentModeText.visibility = View.GONE
            mBinding.tvPaymentMode.visibility = View.GONE
        }

        if (mBinding.tvPaymentMode.text.contentEquals("Cash On Delivery", true)) {
            mBinding.tvPaymentMode.setTextColor(Color.parseColor("#f05a28"))
        }

        if (mBinding.tvDeliveryStatus.text.contentEquals("Delivered", true)) {
            mBinding.tvDeliveryStatus.setTextColor(Color.parseColor("#00a550"))
        }

        // TODO: Get the value from adapter
        mBinding.tvTotalAmount.text = "₹ 150/-"

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.delivery_type_array,
            R.layout.order_status_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            mBinding.SpinnerDeliveryType.adapter = adapter
        }

        mBinding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }

        linearLayoutManager = LinearLayoutManager(this)
        mBinding.rvItemList.layoutManager = linearLayoutManager
        orderItemList = ArrayList()
        dummyData()
        orderItemListAdapter = OrderItemListAdapter(this, orderItemList as ArrayList<OrderItemInfo>)
        mBinding.rvItemList.adapter = orderItemListAdapter
    }

    private fun dummyData() {
        orderItemList.add(OrderItemInfo("Dove soap", "150", "1", 50))
        orderItemList.add(OrderItemInfo("Dove soap", "150", "1", 50))
        orderItemList.add(OrderItemInfo("Dove soap", "150", "1", 50))
        orderItemList.add(OrderItemInfo("Dove soap", "150", "1", 50))
        orderItemList.add(OrderItemInfo("Dove soap", "150", "1", 50))
    }
}
