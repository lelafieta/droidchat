package com.futurist.droidchat.ui.validator

object EmailValidator {

    private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    fun isValid(value: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(value)
    }
}