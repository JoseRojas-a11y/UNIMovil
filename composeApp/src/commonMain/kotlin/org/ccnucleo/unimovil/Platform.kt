package org.ccnucleo.unimovil

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform