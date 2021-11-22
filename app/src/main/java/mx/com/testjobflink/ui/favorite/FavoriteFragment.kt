package mx.com.testjobflink.ui.favorite

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite.*
import mx.com.testjobflink.R
import mx.com.testjobflink.base.BaseFragment
import mx.com.testjobflink.data.models.Movie
import mx.com.testjobflink.ui.adapter.reciclerview.SpacesItemDecoration
import mx.com.testjobflink.ui.favorite.model.FavoriteViewState
import mx.com.testjobflink.ui.favorite.viewmodel.FavoritesViewModel
import mx.com.testjobflink.ui.listener.FragmentCommunication
import mx.com.testjobflink.ui.popular.PopularFragment
import mx.com.testjobflink.utils.extensions.gone
import mx.com.testjobflink.utils.extensions.observe
import mx.com.testjobflink.utils.extensions.snack
import mx.com.testjobflink.utils.extensions.visible
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment() {

    private val viewModel: FavoritesViewModel by viewModel()

    lateinit var communication: FragmentCommunication

    override fun getLayoutView(): Int = R.layout.fragment_favorite

    override fun initView() {
        initAdapterManager()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        communication = context as FragmentCommunication
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val isLandscape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
        rvFavoriteMovies?.apply {
            moviesLayoutManager?.spanCount = getColumnsByOrientation(isLandscape)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun attachObservers() {
        viewModel.apply {
            getFavoritesMovies()
            observe(favoriteMoviesViewState, ::handleFavoriteViewState)
        }
    }

    private fun handleFavoriteViewState(viewState: FavoriteViewState) {
        when (viewState) {
            is FavoriteViewState.OnSuccessAddFavorite -> snack(R.string.message_success_add_favorites)
            is FavoriteViewState.OnSuccessRemoveFavorite -> snack(R.string.message_success_remove_favorites)
            is FavoriteViewState.OnResultFavorites -> showFavoritesMovies(viewState.movies)
            is FavoriteViewState.OnEmptyFavorites -> showEmptyFavorites()
        }
    }

    private fun showEmptyFavorites() {
        rvFavoriteMovies.gone()
        tvEmptyFavoritesMovies.visible()
    }

    private fun showFavoritesMovies(movies: List<Movie>) {
        moviesAdapter.updateMovies(movies)
        rvFavoriteMovies.visible()
        tvEmptyFavoritesMovies.gone()
    }

    private fun initAdapterManager() {
        rvFavoriteMovies?.apply {
            layoutManager = moviesLayoutManager ?: GridLayoutManager(requireContext(),
                getColumnsByOrientation(isLandScape))
            addItemDecoration(SpacesItemDecoration(SPACE_ITEM_DECORATION))
            adapter = moviesAdapter
            setHasFixedSize(true)
            handleItemClickListener()
        }
    }

    private fun handleItemClickListener() {
        moviesAdapter.apply {
            setItemClickListener { poster, title, ranking, movie ->
                communication.selectMovie(poster, title, ranking, movie)
            }
            setUpdateListener {
                it.run {
                    val newValue = !isFavorite
                    isFavorite = newValue
                    viewModel.updateFavorite(id, newValue)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
       viewModel.getFavoritesMovies()

    }




}