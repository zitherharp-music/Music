package com.zitherharp.store.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.zitherharp.store.R
import com.zitherharp.store.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val binding: FragmentMainBinding by lazy { FragmentMainBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        return binding.apply {
            activity?.let {
                val appBarConfiguration = AppBarConfiguration(
                    setOf(R.id.navigation_application, R.id.navigation_game)
                )
                activity?.actionBar?.setDisplayHomeAsUpEnabled(false)
//                navView.setupWithNavController(binding.navView)
//                setupActionBarWithNavController(it as AppCompatActivity, navController)
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}