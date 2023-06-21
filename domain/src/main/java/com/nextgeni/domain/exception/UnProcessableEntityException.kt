package com.nextgeni.domain.exception


class UnProcessableEntityException(var errorMap: HashMap<String, String>) : Exception("Unprocessable Entity")