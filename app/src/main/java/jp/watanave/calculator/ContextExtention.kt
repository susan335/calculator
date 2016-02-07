package jp.watanave.calculator

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

/**
 * Created by Susan on 2016/01/31.
 */

infix fun Context.showToastShort(message : CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

infix fun Context.showToastLong(message : CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

infix fun Context.showDialog(message : CharSequence) {
    var dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(message)
    dialogBuilder.setPositiveButton("OK", null)
    dialogBuilder.show()
}