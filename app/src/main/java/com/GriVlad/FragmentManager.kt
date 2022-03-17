package com.GriVlad

import androidx.appcompat.app.AppCompatActivity

object FragmentManager {
    var currentFragment:BaseFragment?=null
    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity){
        val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.place_holder,newFrag).commit()
        currentFragment=newFrag

    }
    open fun flipCard(activity: AppCompatActivity) {


        activity.supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.card_flip_right_in,
                R.anim.card_flip_right_out,
                R.anim.card_flip_left_in,
                R.anim.card_flip_left_out
            ).replace(R.id.place_holder, WeatherFragment())
            .addToBackStack(null)
            .commit()
    }

}