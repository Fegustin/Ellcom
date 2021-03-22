package ru.steilsouth.ellcom.ui.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.steilsouth.ellcom.ui.viewpager.MainScoreFragment
import ru.steilsouth.ellcom.ui.viewpager.ScoreSubContractsFragment


class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> ScoreSubContractsFragment()
            else -> MainScoreFragment()
        }
    }
}