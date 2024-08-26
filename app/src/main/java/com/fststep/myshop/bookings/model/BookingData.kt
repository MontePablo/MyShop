/* Copyright (C) 2021 Fststep Next2Me - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package com.fststep.myshop.bookings.model

/**
 * Created by Shubham Singh on 11/07/21.
 */
object BookingData {

    fun getBookingData(): ArrayList<Booking> {
        return arrayListOf(
            Booking(
                0,
                "Mr. John Doe",
                "05/06/2021",
                "10:00 AM",
                "5485698452",
                "abc@xyz.com"
            ),
            Booking(
                1,
                "Mr. Ram",
                "16/06/2021",
                "12:00 PM",
                "5485698452",
                "abc@xyz.com"
            ),
            Booking(
                2,
                "Mr. Jeet",
                "20/06/2021",
                "2:00 PM",
                "5485698452",
                "abc@xyz.com"
            ),
            Booking(
                3,
                "Miss Geeta",
                "25/06/2021",
                "11:00 AM",
                "5485698452",
                "abc@xyz.com"
            ),
            Booking(
                4,
                "Mrs. Hema",
                "30/06/2021",
                "4:00 PM",
                "5485698452",
                "abc@xyz.com"
            ),
            Booking(
                5,
                "Mr. Amit",
                "08/07/2021",
                "1:00 PM",
                "5485698452",
                "abc@xyz.com"
            ),
            Booking(
                6,
                "Mr. Sami",
                "08/07/2021",
                "3:00 PM",
                "5485698452",
                "abc@xyz.com"
            ),
            Booking(
                7,
                "Mrs. Sunita",
                "10/07/2021",
                "2:00 PM",
                "5485698452",
                "abc@xyz.com"
            ),
            Booking(
                8,
                "Mr. Shailesh",
                "11/07/2021",
                "5:00 PM",
                "5485698452",
                "abc@xyz.com"
            ),
            Booking(
                9,
                "Mr. Shailesh test",
                "12/07/2021",
                "5:00 PM",
                "5485698452",
                "abc@xyz.com"
            ),
        )
    }

    fun getBookingsByDate(dates: ArrayList<String>): ArrayList<Booking> {
        return getBookingData().filter { booking -> dates.contains(booking.date) }.toMutableList() as ArrayList<Booking>
    }

    fun getBookingsByDate(date: String): ArrayList<Booking> {
        return getBookingsByDate(arrayListOf(date))
    }
}
