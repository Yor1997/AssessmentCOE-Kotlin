package com.example.assessmentcoe_kotlin.util

class ValidationUtils {
    companion object {
        fun isValidFormat(input: String): Boolean {
            val regex = Regex(
                "^[A-Z]{2}\\d{2}\\d{2}$|" +
                        "^\\d{2}\\d{2}[A-Z]{2}$|" +
                        "^\\d{2}[A-Z]{2}\\d{2}$|" +
                        "^[A-Z]{2}\\d{2}[A-Z]{2}$|" +
                        "^[A-Z]{2}[A-Z]{2}\\d{2}$|" +
                        "^\\d{2}[A-Z]{2}[A-Z]{2}$|" +
                        "^\\d{2}[A-Z]{3}\\d{1}$|" +
                        "^\\d{1}[A-Z]{3}\\d{2}$|" +
                        "^[A-Z]{2}\\d{3}[A-Z]{1}$|" +
                        "^[A-Z]{1}\\d{3}[A-Z]{2}$|" +
                        "^[A-Z]{3}\\d{2}[A-Z]{1}$|" +
                        "^[A-Z]{1}\\d{2}[A-Z]{3}$|" +
                        "^[0-9][A-Z]{2}\\d{3}$|" +
                        "^\\d{3}[A-Z]{2}[0-9]$"
            )
            return regex.matches(input)
        }
    }
}