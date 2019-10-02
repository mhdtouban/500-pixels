package com.moe.a500pixels.popular.util

import android.app.Activity
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import com.moe.a500pixels.popular.data.Photo
import com.moe.a500pixels.popular.data.User
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import java.util.*

val testPhotoA = Photo(
    4910422,
    "lemon",
    "",
    709,
    97.4,
    "2012-02-09T02:27:16-05:00",
    0,
    false,
    472,
    709,
    88,
    58,
    false,
    arrayListOf("http://pcdn.500px.net/4910421/c4a10b46e857e33ed2df35749858a7e45690dae7/2.jpg"),
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
)

val testPhotoB = Photo(
    4910421,
    "Orange",
    "",
    709,
    97.4,
    "2011-02-09T02:27:16-05:00",
    0,
    false,
    472,
    709,
    90,
    60,
    false,
    arrayListOf("http://pcdn.500px.net/4910421/c4a10b46e857e33ed2df35749858a7e45690dae7/2.jpg"),
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
)

/**
 * [Calendar] object used for tests.
 */
val testCalendar: Calendar = Calendar.getInstance().apply {
    set(Calendar.YEAR, 1998)
    set(Calendar.MONTH, Calendar.SEPTEMBER)
    set(Calendar.DAY_OF_MONTH, 4)
}

/**
 * Returns the content description for the navigation button view in the toolbar.
 */
fun getToolbarNavigationContentDescription(activity: Activity, toolbarId: Int) =
    activity.findViewById<Toolbar>(toolbarId).navigationContentDescription as String

/**
 * Simplify testing Intents with Chooser
 *
 * @param matcher the actual intent before wrapped by Chooser Intent
 */
fun chooser(matcher: Matcher<Intent>): Matcher<Intent> = allOf(
    hasAction(Intent.ACTION_CHOOSER),
    hasExtra(`is`(Intent.EXTRA_INTENT), matcher)
)