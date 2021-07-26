package com.example.booksearch

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun SettingsType.isDefault(): Boolean {
    return this == SettingsType.ALL
}

fun FragmentManager.openFragment(
    fragment: Fragment,
    @IdRes containerIdRes: Int,
    @AnimRes inAnimation: Int,
    @AnimRes outAnimation: Int
) {

    val fragmentTransaction = beginTransaction()

    fragmentTransaction.setCustomAnimations(
        inAnimation,
        outAnimation,
        inAnimation,
        outAnimation
    )

    fragmentTransaction.add(containerIdRes, fragment, MainActivity.BOOKS_FRAGMENT_TAG)
    fragmentTransaction.addToBackStack(MainActivity.BOOKS_FRAGMENT_TAG)
    if (!isStateSaved) {
        fragmentTransaction.commit()
    } else {
        fragmentTransaction.commitAllowingStateLoss()
    }
}