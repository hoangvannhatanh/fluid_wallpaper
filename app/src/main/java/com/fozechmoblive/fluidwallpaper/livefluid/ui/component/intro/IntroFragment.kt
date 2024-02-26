package com.fozechmoblive.fluidwallpaper.livefluid.ui.component.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fozechmoblive.fluidwallpaper.livefluid.R
import com.fozechmoblive.fluidwallpaper.livefluid.databinding.FragmentIntroBinding

class IntroFragment : Fragment() {
    private lateinit var binding: FragmentIntroBinding
    private var numberIntroduce = 0

    companion object {
        private const val keyNumberIntroduce = "description"
        fun newInstance(valueNumberIntroduce: Int): IntroFragment {
            val fragment = IntroFragment()
            val bundle = Bundle()
            bundle.putInt(keyNumberIntroduce, valueNumberIntroduce)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroBinding.inflate(inflater, container, false)
        arguments?.let {
            numberIntroduce = it.getInt(keyNumberIntroduce, 0)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (numberIntroduce) {
            0 -> {
                binding.ivIntroduce.setImageResource(R.drawable.one_introduce)
            }

            1 -> {
                binding.ivIntroduce.setImageResource(R.drawable.two_introduce)
            }

            2 -> {
                binding.ivIntroduce.setImageResource(R.drawable.three_introduce)
            }

            else -> {
                binding.ivIntroduce.setImageResource(R.drawable.one_introduce)
            }
        }
    }
}