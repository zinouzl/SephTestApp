package io.seph.presentation.extensions


fun Double.format(precision: Int = 1) = String.format("%.${precision}f".format(this))