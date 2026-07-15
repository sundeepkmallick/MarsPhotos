package com.marsphoto.common

interface Mapper<F, T> {
    fun map(from: F): T
}