package ru.steilsouth.ellcom.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_create_bills.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.ui.viewpager.adapter.FragmentAdapter


class CreateBillsFragment : Fragment(R.layout.fragment_create_bills) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()

        toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.buttonTotalScore -> viewPager.currentItem = 0
                    else -> viewPager.currentItem = 1
                }
            }
        }
    }

    private fun initViewPager() {
        val adapter = activity?.let { FragmentAdapter(it.supportFragmentManager, lifecycle) }
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
    }
}