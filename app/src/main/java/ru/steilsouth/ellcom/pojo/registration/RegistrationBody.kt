package ru.steilsouth.ellcom.pojo.registration

data class RegistrationBody(
    val name: String,
    val organization: String,
    val phone: String,
    val address: String,
    val email: String
)
