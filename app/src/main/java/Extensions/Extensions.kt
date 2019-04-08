package Extensions

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import org.json.JSONArray
import org.json.JSONObject

operator fun JSONArray.iterator(): Iterator<JSONObject>
        = (0 until this.length()).asSequence().map { this.get(it) as JSONObject }.iterator()

fun View.displaySnack(message:String, length: Int = Snackbar.LENGTH_LONG, func:Snackbar.()-> Unit) {

    val snackbar = Snackbar.make(this, message, length)

    snackbar.func()

    snackbar.show()
}

fun Snackbar.action(message:String, listener: (View) -> Unit) = setAction(message, listener)

val View.context:Context get() = context