/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.deliveryboy.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fststep.myshop.R
import com.fststep.myshop.deliveryboy.model.OrderForDeliveryInfo

/**
 * Created by Jay Kulshreshtha on 16/05/21.
 */
class SelectOrderForDeliveryListAdapter(var context: Context, private val dataSet: ArrayList<OrderForDeliveryInfo>) :
    RecyclerView.Adapter<SelectOrderForDeliveryListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDeliveryAddress: AppCompatTextView = view.findViewById(R.id.tvDeliveryAddress)
        var tvDeliveryType: AppCompatTextView = view.findViewById(R.id.tvDeliveryType)
        var tvPaymentType: AppCompatTextView = view.findViewById(R.id.tvPaymentType)
        var tvDeliveryStatus: AppCompatTextView = view.findViewById(R.id.tvDeliveryStatus)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_order_for_delivery, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvDeliveryAddress.text = dataSet[position].deliveryaddress
        viewHolder.tvDeliveryType.text = dataSet[position].deliverytype
        viewHolder.tvPaymentType.text = dataSet[position].paymenttype
        viewHolder.tvDeliveryStatus.text = dataSet[position].deliverystatus

        if (viewHolder.tvPaymentType.text.contains("Cash on Delivery")) {
            viewHolder.tvPaymentType.setTextColor(Color.parseColor("#ec1d24"))
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}
