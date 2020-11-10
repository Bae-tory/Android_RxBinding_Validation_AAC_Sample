package com.project.rainist_android_test.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Context.startActivityWithData(targetActivity: Class<out Any>, block: Intent.() -> Unit) {
    val i = Intent(this, targetActivity)
    block(i)
    startActivity(i)
}

fun Context.startActivity(targetActivity: Class<out Any>) {
    val i = Intent(this, targetActivity)
    startActivity(i)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

