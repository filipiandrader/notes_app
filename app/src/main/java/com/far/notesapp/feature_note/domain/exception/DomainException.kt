package com.far.notesapp.feature_note.domain.exception

open class DomainException(message: String, title: String? = null) :
    RuntimeException(message, RuntimeException(title))

class MissingParamsException : DomainException("Params can't be null.")

class EmptyParamException(param: String) : DomainException("The $param can't be empty.")