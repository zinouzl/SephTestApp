package io.seph.presentation.extensions

import io.seph.presentation.models.Currency

fun String.addCurrencySymbol(currency: Currency = Currency.EUR) = "$this ${currency.symbol}"