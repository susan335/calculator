package jp.watanave.calculator

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

/**
 * Created by Susan on 2016/01/31.
 */

infix fun Context.showToast(message : CharSequence, length : Int) {
    Toast.makeText(this, message, length).show()
}

infix fun Context.showDialog(message : CharSequence) {
    var dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(message)
    dialogBuilder.setPositiveButton("OK", null)
    dialogBuilder.show()
}