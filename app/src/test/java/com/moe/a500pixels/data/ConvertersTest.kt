package com.moe.a500pixels.data

import com.moe.a500pixels.popular.data.User
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar.*

class ConvertersTest {

    private val cal = getInstance().apply {
        set(YEAR, 2077)
        set(MONTH, JULY)
        set(DAY_OF_MONTH, 7)
    }

    @Test
    fun calendarToDatestamp() {
        assertEquals(cal.timeInMillis, Converters().calendarToDatestamp(cal))
    }

    @Test
    fun datestampToCalendar() {
        assertEquals(Converters().datestampToCalendar(cal.timeInMillis), cal)
    }

    @Test
    fun fromUserToString() {
        assertEquals(
            Converters().fromUserToString(
                User(
                    386047,
                    "Lluisdeharo",
                    "Lluis ",
                    "de Haro Sanchez",
                    "Sabadell",
                    "Catalunya",
                    "Lluis de Haro Sanchez",
                    "http://acdn.500px.net/386047/f76ed05530afec6d1d0bd985b98a91ce0ce49049/1.jpg?0",
                    0
                )
            ),
            "{\"id\":386047,\"username\":\"Lluisdeharo\",\"firstname\":\"Lluis \",\"lastname\":\"de Haro Sanchez\",\"city\":\"Sabadell\",\"country\":\"Catalunya\",\"fullname\":\"Lluis de Haro Sanchez\",\"userpic_url\":\"http://acdn.500px.net/386047/f76ed05530afec6d1d0bd985b98a91ce0ce49049/1.jpg?0\",\"upgrade_status\":0}"
        )
    }

    @Test
    fun fromStringToUser() {
        assertEquals(
            Converters().fromStringtoUser(
                "{\"id\":386047,\"username\":\"Lluisdeharo\",\"firstname\":\"Lluis \",\"lastname\":\"de Haro Sanchez\",\"city\":\"Sabadell\",\"country\":\"Catalunya\",\"fullname\":\"Lluis de Haro Sanchez\",\"userpic_url\":\"http://acdn.500px.net/386047/f76ed05530afec6d1d0bd985b98a91ce0ce49049/1.jpg?0\",\"upgrade_status\":0}"
            ), User(
                386047,
                "Lluisdeharo",
                "Lluis ",
                "de Haro Sanchez",
                "Sabadell",
                "Catalunya",
                "Lluis de Haro Sanchez",
                "http://acdn.500px.net/386047/f76ed05530afec6d1d0bd985b98a91ce0ce49049/1.jpg?0",
                0
            )
        )
    }

    @Test
    fun fromStringToArrayList() {
        assertEquals(
            Converters().fromStringToArrayList(
                "[\"https://drscdn.500px.org/photo/1003771318/q%3D50_h%3D450/v2?sig=8c82bb7919ec06a5fd45a8591ba3d199dc0aa51f661989f40e3ee755def6a410\"]"
            ),
            arrayListOf("https://drscdn.500px.org/photo/1003771318/q%3D50_h%3D450/v2?sig=8c82bb7919ec06a5fd45a8591ba3d199dc0aa51f661989f40e3ee755def6a410")
        )
    }

    @Test
    fun fromArrayListToString() {
        assertEquals(
            Converters().fromArrayListToString(
                arrayListOf("https://drscdn.500px.org/photo/1003771318/q%3D50_h%3D450/v2?sig=8c82bb7919ec06a5fd45a8591ba3d199dc0aa51f661989f40e3ee755def6a410")
            ),
            "[\"https://drscdn.500px.org/photo/1003771318/q%3D50_h%3D450/v2?sig\\u003d8c82bb7919ec06a5fd45a8591ba3d199dc0aa51f661989f40e3ee755def6a410\"]"

        )
    }
}