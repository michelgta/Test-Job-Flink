package mx.com.testjobflink.utils.extensions

import mx.com.testjobflink.R
import mx.com.testjobflink.utils.Constants.URL_IMAGE


val String.posterFullUrl: String
    get() = "${URL_IMAGE}$this"

val Boolean.imageFavoriteResource: Int
    get() = if (this) R.drawable.ic_favorite_full else R.drawable.ic_favorite_empty