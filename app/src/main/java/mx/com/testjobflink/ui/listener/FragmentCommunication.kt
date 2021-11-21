package mx.com.testjobflink.ui.listener

import android.view.View
import mx.com.testjobflink.data.models.Movie


interface FragmentCommunication {
    fun selectMovie(poster: View, title: View, ranking: View, movie: Movie)
}