package com.GriVlad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.GriVlad.databinding.FragmentFrontSideBinding


class FrontSide : BaseFragment() {


    lateinit var binding:FragmentFrontSideBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentFrontSideBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardView.setOnClickListener {
            val fragstart=requireActivity() as MainActivity

            FragmentManager.flipCard(fragstart)
            fragstart.getCurrentData()
        }

    }


    companion object {

        @JvmStatic
        fun newInstance() = FrontSide()
            }
    }
