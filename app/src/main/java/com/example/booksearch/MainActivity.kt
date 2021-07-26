package com.example.booksearch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.booksearch.booksPage.BooksFragment
import com.example.booksearch.di.appModule
import com.example.booksearch.settingsFragment.SettingsFragment
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainActivity : AppCompatActivity() {
    private val appViewModel: SharedViewModel by viewModel<SharedViewModel>()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            openFragment(BooksFragment())
        }

        appViewModel.openSettingsPageLiveData.observe(this, {
            openFragment(SettingsFragment())
        })

        appViewModel.backToMainPageLiveData.observe(this, {
            supportFragmentManager.popBackStack()
        })
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.openFragment(
            fragment,
            R.id.fragmentContainer,
            R.anim.slide_in,
            R.anim.slide_out
        )
    }

    companion object {
        const val BOOKS_FRAGMENT_TAG = "books_fragment"
    }
}
