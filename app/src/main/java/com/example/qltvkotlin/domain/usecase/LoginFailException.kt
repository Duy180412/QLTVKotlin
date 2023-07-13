package com.example.qltvkotlin.domain.usecase

class LoginFailException : Exception("Login fail, userName or password is invalid")