package br.com.emesistemas.topk.di

import androidx.room.Room
import br.com.emesistemas.topk.data.local.AppDatabase
import br.com.emesistemas.topk.data.local.ItemDao
import br.com.emesistemas.topk.data.local.RepoRepository
import br.com.emesistemas.topk.data.remote.AppHttp
import br.com.emesistemas.topk.data.remote.GithubApi
import br.com.emesistemas.topk.data.remote.ResponseHandler
import br.com.emesistemas.topk.presentation.viewmodel.RepoListViewModel
import br.com.emesistemas.topk.presentation.viewmodel.UiStateViewModel
import br.com.emesistemas.topk.ui.adapters.RepoListAdapter
import br.com.emesistemas.topk.ui.fragments.RepoDetailFragment
import br.com.emesistemas.topk.ui.fragments.RepoListFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DB_NAME = "topk.db"

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }
}

val daoModule = module {

    single<ItemDao> {
        get<AppDatabase>().getItemDao()
    }
}

val apiModule = module {
    single<GithubApi> {
        AppHttp().githubApi
    }

    single<ResponseHandler> {
        ResponseHandler()
    }
}

val repositoryModule = module {
    single<RepoRepository> {
        RepoRepository(get(), get(), get())
    }
}

val viewModelModule = module {
    viewModel<RepoListViewModel> {
        RepoListViewModel(get())
    }

    viewModel<UiStateViewModel>{
        UiStateViewModel()
    }
}

val uiModule = module {
    factory<RepoListFragment> { RepoListFragment() }
    factory<RepoDetailFragment> { RepoDetailFragment() }
    factory<RepoListAdapter> { RepoListAdapter(get()) }
}
