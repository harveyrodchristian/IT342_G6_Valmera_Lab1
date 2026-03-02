package com.example.it342auth.api

import com.google.gson.annotations.SerializedName

// Request Models
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

// Response Models
data class AuthResponse(
    val id: Long? = null,
    val email: String? = null,
    val name: String? = null,
    val token: String? = null,
    val error: String? = null
)

data class UserInfo(
    val id: Long,
    val email: String,
    val name: String
)

data class ErrorResponse(
    val error: String? = null,
    val message: String? = null
)
