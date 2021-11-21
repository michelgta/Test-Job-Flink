package mx.com.testjobflink.di

import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import mx.com.testjobflink.data.local.MoviesDao
import mx.com.testjobflink.data.local.MoviesDataBase
import mx.com.testjobflink.data.remote.HttpInterceptor.createOkHttpInterceptor
import mx.com.testjobflink.data.remote.MovieEndpoints
import mx.com.testjobflink.data.repository.FavoriteRepository
import mx.com.testjobflink.data.repository.FavoriteRepositoryImpl
import mx.com.testjobflink.data.repository.PopularRepository
import mx.com.testjobflink.data.repository.PopularRepositoryImpl
import mx.com.testjobflink.domain.usecase.GetFavoritesUseCase
import mx.com.testjobflink.domain.usecase.GetFavoritesUseCaseImpl
import mx.com.testjobflink.domain.usecase.GetPopularUseCase
import mx.com.testjobflink.domain.usecase.GetPopularUseCaseImpl
import mx.com.testjobflink.ui.favorite.viewmodel.FavoritesViewModel
import mx.com.testjobflink.ui.popular.viewmodel.PopularViewModel
import mx.com.testjobflink.utils.Constants.DATA_BASE_NAME_MOVIES
import mx.com.testjobflink.utils.Constants.URL_BASE
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val ApplicationModule = module {

    factory<PopularRepository> {
        PopularRepositoryImpl(get() as MovieEndpoints, get() as MoviesDao)
    }

    factory<FavoriteRepository> {
        FavoriteRepositoryImpl(get() as MoviesDao)
    }

    factory<GetPopularUseCase> {
        GetPopularUseCaseImpl(get() as PopularRepository,
            get() as FavoriteRepository)
    }
    viewModel {
        PopularViewModel(get() as GetPopularUseCase)
    }

    factory<GetFavoritesUseCase> {
        GetFavoritesUseCaseImpl(get() as FavoriteRepository)
    }

    viewModel {
        FavoritesViewModel(get() as GetFavoritesUseCase)
    }
}


val NetworkModule = module {
    single {
        Room.databaseBuilder(androidContext(),
            MoviesDataBase::class.java, DATA_BASE_NAME_MOVIES).build()
    }

    single { get<MoviesDataBase>().moviesDao() }

    single {
        OkHttpClient.Builder()
            .addInterceptor(createOkHttpInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(URL_BASE)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .build()
    }

    single { get<Retrofit>().create(MovieEndpoints::class.java) }
}