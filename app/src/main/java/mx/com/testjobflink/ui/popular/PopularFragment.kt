package mx.com.testjobflink.ui.popular

import android.content.Context
import android.content.res.Configuration
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_popular.*
import mx.com.testjobflink.R
import mx.com.testjobflink.base.BaseFragment
import mx.com.testjobflink.data.models.Movie
import mx.com.testjobflink.ui.adapter.reciclerview.SpacesItemDecoration
import mx.com.testjobflink.ui.favorite.model.FavoriteViewState
import mx.com.testjobflink.ui.listener.FragmentCommunication
import mx.com.testjobflink.ui.popular.model.PopularViewState
import mx.com.testjobflink.ui.popular.viewmodel.PopularViewModel
import mx.com.testjobflink.utils.extensions.*
import mx.com.testjobflink.utils.recyclerview.InfiniteScrollProvider
import org.koin.android.viewmodel.ext.android.viewModel


class PopularFragment : BaseFragment() {


    private val viewModel: PopularViewModel by viewModel()

    lateinit var communication: FragmentCommunication

    var isFirstLauch: Boolean = false

    override fun getLayoutView(): Int = R.layout.fragment_popular

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val isLandscape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
        rvPopularMovies.apply {
            moviesLayoutManager?.spanCount = getColumnsByOrientation(isLandscape)
            adapter?.notifyDataSetChanged()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        communication = context as FragmentCommunication
    }

    override fun initView() {
        initAdapterManager()
        isFirstLauch = true
    }

    override fun attachObservers() {
        viewModel.apply {
            observe(popularMovies, ::addNewMovies)
            observe(popularViewState, ::handlePopularViewState)
            observe(favoriteMoviesViewState, ::handleFavoriteViewState)
        }
    }

    private fun addNewMovies(movies: List<Movie>) {
        moviesAdapter.addNewMovies(movies)
    }

    private fun initAdapterManager() {
        rvPopularMovies?.apply {
            layoutManager = moviesLayoutManager ?: GridLayoutManager(
                requireContext(),
                getColumnsByOrientation(isLandScape)
            )
            adapter = moviesAdapter
            addItemDecoration(SpacesItemDecoration(SPACE_ITEM_DECORATION))
            addScrollListener()
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

    private fun addScrollListener() {
        InfiniteScrollProvider().attach(
            rvPopularMovies,
            object : InfiniteScrollProvider.OnLoadMoreListener {
                override fun onLoadMore() {
                    viewModel.fetchPopularMovies()
                }
            })
    }

    private fun handlePopularViewState(viewState: PopularViewState) {
        when (viewState) {
            is PopularViewState.OnSuccessFetch -> pgrsPopularMovies.gone()
            is PopularViewState.OnLoading -> pgrsPopularMovies.visible()
            is PopularViewState.OnMaxPagesReached -> snack(R.string.message_max_pages_reached)
            is PopularViewState.OnError -> {
                pgrsPopularMovies.gone()
                if (requireActivity().hasNetworkConnection())
                    snack(R.string.message_error_fetching)
                else
                    showAlert(R.string.error_verify_network_connection) {
                        viewModel.fetchPopularMovies()
                    }
            }
        }
    }

    private fun handleFavoriteViewState(viewState: FavoriteViewState) {
        when (viewState) {
            is FavoriteViewState.OnSuccessAddFavorite -> snack(R.string.message_success_add_favorites)
            else -> snack(R.string.message_success_remove_favorites)
        }
    }


    override fun onResume() {
        super.onResume()
        if (!isFirstLauch) {
            clyContainerPopular.gone()
            activity?.startActivity(activity?.intent)
        } else {
            clyContainerPopular.visible()
            isFirstLauch = false
        }
    }


}