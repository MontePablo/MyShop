/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.deliveryboy.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.fststep.myshop.R
import com.fststep.myshop.deliveryboy.model.DeliveryBoyInfo
import com.fststep.myshop.deliveryboy.view.SelectOrderForDeliveryActivity
import com.google.android.material.button.MaterialButton

/**
 * Created by Jay Kulshreshtha on 15/05/21.
 */
class DeliveryBoyListAdapter(var context: Context, private val dataSet: ArrayList<DeliveryBoyInfo>) :
    RecyclerView.Adapter<DeliveryBoyListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDeliveryBoyName: AppCompatTextView = view.findViewById(R.id.tvDeliveryBoyName)
        var deliveryBoyRating: AppCompatRatingBar = view.findViewById(R.id.deliveryBoyRating)
        var tvLessThanKm: AppCompatTextView = view.findViewById(R.id.tvLessThanKm)
        var tvMoreThanKm: AppCompatTextView = view.findViewById(R.id.tvMoreThanKm)
//        var ivMessageDeliveryBoy : AppCompatImageView = view.findViewById(R.id.ivMessageDeliveryBoy)
//        var ivCallDeliveryBoy : AppCompatImageView = view.findViewById(R.id.ivCallDeliveryBoy)
        private var buttonHireDeliveryBoy: MaterialButton = view.findViewById(R.id.buttonHireDeliveryBoy)

        init {
            // Define click listener for the ViewHolder's View.
            buttonHireDeliveryBoy.setOnClickListener {
                val intent = Intent(view.context, SelectOrderForDeliveryActivity::class.java)
                view.context.startActivity(intent)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_delivery_boy, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.tvDeliveryBoyName.text = dataSet[position].deliveryboyname
        viewHolder.deliveryBoyRating.rating = dataSet[position].deliveryboyrating.toFloat()
        viewHolder.tvLessThanKm.text = "Rs " + dataSet[position].ratelessthankm.toString() + "/ less then km"
        viewHolder.tvMoreThanKm.text = "Rs " + dataSet[position].ratemorethankm.toString() + "/ more then km"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}
