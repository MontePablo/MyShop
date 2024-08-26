/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.bookings

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.GridLayout
import android.widget.PopupWindow
import android.widget.Space
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.contains
import androidx.core.view.marginStart
import androidx.databinding.DataBindingUtil
import com.fststep.myshop.R
import com.fststep.myshop.bookings.model.Booking
import com.fststep.myshop.bookings.model.BookingData
import com.fststep.myshop.core.extension.formatDisplayDate
import com.fststep.myshop.core.extension.formatDisplayDateFromServer
import com.fststep.myshop.core.extension.px
import com.fststep.myshop.databinding.LayoutSlotDetailsBinding
import com.fststep.myshop.databinding.LayoutSlotViewBinding
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.collections.ArrayList

/**
 * Created by Shubham Singh on 04/07/21.
 */
class SlotActivity : AppCompatActivity() {

    private lateinit var mBinding: LayoutSlotViewBinding
    private lateinit var dayList: Array<String>
    private lateinit var timeSlotList: Array<String>

    private var gridSlots: GridLayout? = null

    private var currentWeekNumber = 0 // current week
    private var weekDatesList = arrayListOf<String>()
    private var bookingList = arrayListOf<Booking>()
    private var mode: SlotMode = SlotMode.VIEW
    private var tempBooking: Booking? = null
    private var tempCheckBoxPos: Pair<Int, Int>? = null
    private var tempIndex: Int? = null
    private var delCheckBox: CompoundButton? = null

    val displayDate: DateFormat = SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH)
    val serverDate: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.layout_slot_view)
        mBinding.slotMode = mode
        bookingList = BookingData.getBookingData()
        dayList = resources.getStringArray(R.array.week_day_entries)
        timeSlotList = resources.getStringArray(R.array.time_slot_entries)
        mBinding.btNext.setOnClickListener {
            currentWeekNumber += 7 // increment day by 7 to go to next week
            updateWeek()
        }

        mBinding.btPrev.setOnClickListener {
            currentWeekNumber -= 7 // decrement day by 7 to go to prev week
            updateWeek()
        }

        mBinding.buttonDone.setOnClickListener {
            if (mode == SlotMode.EDIT) {
                if (tempBooking == null || tempBooking?.date.isNullOrEmpty() || tempBooking?.time.isNullOrEmpty() || tempCheckBoxPos == null || tempIndex == null) {
                    Toast.makeText(this, "Please select a slot", Toast.LENGTH_SHORT).show()
                } else {
                    bookingList[tempIndex!!] = tempBooking!! // Update the booking with updated data
                    mode = SlotMode.VIEW
                    mBinding.slotMode = mode

                    // Clear all temp variables
                    tempBooking = null
                    tempCheckBoxPos = null
                    tempIndex = null
                }
            } else {
                finish() // TODO pass data back
            }
        }

        mBinding.buttonCancel.setOnClickListener {
            mode = SlotMode.VIEW
            tempBooking = null
            tempCheckBoxPos = null
            tempIndex = null
            updateWeek()
            mBinding.slotMode = mode
        }

        updateWeek()
    }

    @SuppressLint("SetTextI18n")
    fun updateWeek() {
        val calenderThisWeek = Calendar.getInstance()
        if (currentWeekNumber != 0) {
            calenderThisWeek.add(Calendar.DAY_OF_WEEK, if (currentWeekNumber> 0) +currentWeekNumber else currentWeekNumber)
        }
        calenderThisWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val startWeek: String = displayDate.format(calenderThisWeek.time)

        calenderThisWeek.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        val endWeek: String = displayDate.format(calenderThisWeek.time)

        weekDatesList = getDates(startWeek, endWeek)

        mBinding.tvDate.text = "${startWeek.formatDisplayDate()} to ${endWeek.formatDisplayDate()}"

        initWeekView()
    }

    private fun getDates(dateString1: String, dateString2: String): ArrayList<String> {
        val dates = ArrayList<String>()
        var date1: Date? = null
        var date2: Date? = null
        try {
            date1 = displayDate.parse(dateString1)
            date2 = displayDate.parse(dateString2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        while (!cal1.after(cal2)) {
            dates.add(serverDate.format(cal1.time))
            cal1.add(Calendar.DATE, 1)
        }
        return dates
    }

    private fun isPastSlot(row: Int, col: Int): Boolean {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy h:mm a", Locale.ENGLISH)
        val now = Date()

        val date = weekDatesList.getOrNull(col)
        val time = timeSlotList.getOrNull(row)

        if (date != null && time != null) {
            val suffix = if (time.split(":")[0].toInt() in 10..11) "AM" else "PM"
            val dateString = "$date $time $suffix"
            val slot = dateFormat.parse(dateString)

            return slot.time < now.time
        } else {
            return false
        }
    }

    private fun initWeekView() {
        clearLayout()
        val markForMCTypeface = ResourcesCompat.getFont(this, R.font.markformc)
        gridSlots = GridLayout(this)
        gridSlots?.layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)

        gridSlots?.columnCount = 8 // 7 days + 1 for header
        gridSlots?.rowCount = 11 // 10 time slots + 1 for header
        (gridSlots?.layoutParams as ViewGroup.MarginLayoutParams).setMargins(16.px, 16.px, 16.px, 16.px)
        gridSlots?.marginStart
        gridSlots?.useDefaultMargins = true

        for (rowCount in 0 until gridSlots!!.rowCount) {
            for (colCount in 0 until gridSlots!!.columnCount) {

                // Add Space at 0,0
                if (rowCount == 0 && colCount == 0) {
                    val spacer = Space(this)
                    spacer.tag = getBookingBySlotPos(rowCount, colCount)
                    spacer.layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    gridSlots?.addView(spacer, getGridSpecParams(rowCount, colCount))
                } else if (rowCount == 0) { // Add day headers
                    val dateHeader = AppCompatTextView(this)
                    dateHeader.tag = getIndexByRowCol(rowCount, colCount)
                    dateHeader.layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    dateHeader.text = dayList[colCount - 1]
                    dateHeader.gravity = Gravity.CENTER
                    dateHeader.typeface = markForMCTypeface
                    dateHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                    dateHeader.setTextColor(ContextCompat.getColor(this, R.color.header_text_color))
                    gridSlots?.addView(dateHeader, getGridSpecParams(rowCount, colCount))
                } else if (colCount == 0) { // Add time slot headers
                    val timeSlotHeader = AppCompatTextView(this)
                    timeSlotHeader.tag = getIndexByRowCol(rowCount, colCount)
                    timeSlotHeader.layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    timeSlotHeader.text = timeSlotList[rowCount - 1]
                    timeSlotHeader.gravity = Gravity.CENTER
                    timeSlotHeader.typeface = markForMCTypeface
                    timeSlotHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                    timeSlotHeader.setTextColor(ContextCompat.getColor(this, R.color.header_text_color))
                    gridSlots?.addView(timeSlotHeader, getGridSpecParams(rowCount, colCount))
                } else { // Add checkboxes
                    val slotCheckBox = CheckBox(this)
                    slotCheckBox.layoutParams = ViewGroup.LayoutParams(21.px, 21.px)
                    slotCheckBox.tag = getIndexByRowCol(rowCount, colCount)
                    slotCheckBox.isClickable = true
                    slotCheckBox.isFocusable = true
                    slotCheckBox.isChecked = isSlotBooked(rowCount - 1, colCount - 1)
                    slotCheckBox.background = ContextCompat.getDrawable(this, R.drawable.cb_slot)
                    slotCheckBox.buttonDrawable = null
                    slotCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (mode == SlotMode.EDIT) {
                            val tempView = if (tempCheckBoxPos != null) gridSlots?.findViewWithTag<CheckBox>(getIndexByRowCol(tempCheckBoxPos!!.first, tempCheckBoxPos!!.second)) else null
                            if (isSlotBooked(rowCount - 1, colCount - 1) && (buttonView.tag != tempView?.tag)) {
                                buttonView.isChecked = !isChecked
                            } else if (isPastSlot(rowCount - 1, colCount - 1)) {
                                buttonView.isChecked = !isChecked
                                Toast.makeText(this, "Can't select past slot", Toast.LENGTH_SHORT).show()
                            } else {
                                onSlotsChange(rowCount - 1, colCount - 1, buttonView, isChecked)
                            }
                        } else {
                            if (buttonView.id == delCheckBox?.id) {
                                delCheckBox = null
                            } else {
                                buttonView.isChecked = !isChecked
                                if (isSlotBooked(rowCount - 1, colCount - 1)) {
                                    showDetailsPopup(
                                        buttonView,
                                        getBookingBySlotPos(rowCount - 1, colCount - 1),
                                        isPastSlot(rowCount - 1, colCount - 1),
                                        Pair(rowCount, colCount)
                                    )
                                }
                            }
                        }
                    }
                    gridSlots?.addView(
                        slotCheckBox,
                        getGridSpecParams(rowCount, colCount).apply {
                            width = 21.px
                            height = 21.px
                            setGravity(Gravity.CENTER)
                        }
                    )
                }
            }
        }

        mBinding.gridHolder.addView(gridSlots)
    }

    private fun showDetailsPopup(view: CompoundButton, booking: Booking?, isPastSlot: Boolean, pos: Pair<Int, Int>) {
        val popupBinding = DataBindingUtil.inflate<LayoutSlotDetailsBinding>(LayoutInflater.from(this), R.layout.layout_slot_details, null, false)
        popupBinding.tvName.text = booking?.name
        popupBinding.tvDate.text = booking?.date?.formatDisplayDateFromServer()
        popupBinding.tvTime.text = booking?.time

        val popupView = popupBinding.root
        val popupWindow = PopupWindow(popupView, MATCH_PARENT, WRAP_CONTENT, true)

        popupBinding.ivCall.setOnClickListener {
            popupWindow.dismiss()
            booking?.let { it1 -> onCall(it1) }
        }

        popupBinding.ivMessage.setOnClickListener {
            popupWindow.dismiss()
            booking?.let { it1 -> onChat(it1) }
        }

        popupBinding.ivEdit.setOnClickListener {
            if (isPastSlot) {
                Toast.makeText(this, "Can't edit past slot", Toast.LENGTH_SHORT).show()
            } else {
                popupWindow.dismiss()
                booking?.let { it1 -> onEdit(it1, pos) }
            }
        }

        popupBinding.ivDelete.setOnClickListener {
            popupWindow.dismiss()
            booking?.let { it1 -> onDelete(view, it1) }
        }

        popupWindow.elevation = 32f
        popupWindow.showAsDropDown(view)
    }

    private fun getIndexByRowCol(row: Int, col: Int): Int {
        var index = -1
        for (rowCount in 0 until 11) {
            for (colCount in 0 until 8) {
                index += 1
                if (rowCount == row && colCount == col) {
                    return index
                }
            }
        }
        return index
    }

    private fun isSlotBooked(row: Int, col: Int): Boolean {
        val date = weekDatesList.getOrNull(col)
        val time = timeSlotList.getOrNull(row)

        if (mode == SlotMode.EDIT) {
            val tempBookingList = ArrayList<Booking>()
            tempBookingList.addAll(bookingList)
            tempBookingList[tempIndex!!] = tempBooking!!

            if (date != null && time != null) {
                val booking = tempBookingList.filter { booking -> (date == booking.date) && (time == booking.getTimeVal()) }
                return booking.isNotEmpty()
            } else {
                return false
            }
        } else {
            if (date != null && time != null) {
                val booking =
                    bookingList.filter { booking -> (date == booking.date) && (time == booking.getTimeVal()) }
                return booking.isNotEmpty()
            } else {
                return false
            }
        }
    }

    private fun getBookingBySlotPos(row: Int, col: Int): Booking? {
        val date = weekDatesList.getOrNull(col)
        val time = timeSlotList.getOrNull(row)

        if (date != null && time != null) {
            val booking = bookingList.filter { booking -> (date == booking.date) && (time == booking.getTimeVal()) }
            return if (booking.isNotEmpty()) booking[0] else null
        } else {
            return null
        }
    }

    private fun clearLayout() {
        gridSlots?.let {
            if (mBinding.gridHolder.contains(it)) {
                mBinding.gridHolder.removeView(it)
            }
        }
    }

    private fun onSlotsChange(row: Int, col: Int, view: CompoundButton, isChecked: Boolean) {
        if (mode == SlotMode.EDIT) {
            if (!isChecked) {
                tempCheckBoxPos = null
                tempBooking?.date = ""
                tempBooking?.time = ""
            } else {
                if (tempCheckBoxPos != null) {
                    val index = getIndexByRowCol(
                        tempCheckBoxPos!!.first,
                        tempCheckBoxPos!!.second
                    )
                    val childView = gridSlots?.findViewWithTag<CheckBox>(index)
                    childView?.isChecked = false
                }
                tempCheckBoxPos = Pair(row + 1, col + 1)
                tempBooking?.date = weekDatesList.getOrNull(col) ?: ""
                tempBooking?.time = timeSlotList.getOrNull(row) ?: ""
            }
        }
    }

    private fun getGridSpecParams(row: Int, col: Int): GridLayout.LayoutParams {
        return GridLayout.LayoutParams(GridLayout.spec(row, 1f), GridLayout.spec(col, 1f))
    }

    private fun onEdit(booking: Booking, pos: Pair<Int, Int>) {
        mode = SlotMode.EDIT
        mBinding.slotMode = mode
        tempBooking = booking.copy()
        tempIndex = bookingList.indexOf(booking)
        tempCheckBoxPos = pos
    }

    private fun onDelete(view: CompoundButton, booking: Booking) {
        delCheckBox = view
        view.isChecked = false
        bookingList.remove(booking)
    }

    private fun onCall(booking: Booking) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + booking.contact.replace("\\W".toRegex(), ""))
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "Action not supported", Toast.LENGTH_LONG).show()
        }
    }

    private fun onChat(booking: Booking) {
    }
}

enum class SlotMode {
    VIEW,
    EDIT,
    BOOK,
    NONE
}
