package ru.steilsouth.ellcom.adapter

import android.widget.SeekBar
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_score_sub.*
import kotlinx.android.synthetic.main.item_score_sub.seekBarScore
import kotlinx.android.synthetic.main.item_score_sub.textViewContactNum
import kotlinx.android.synthetic.main.item_score_sub.textViewMouth
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.ui.viewpager.costCalculation
import ru.steilsouth.ellcom.viewmodal.BillsVM


class ScopeSubItem(
    private val contractNum: String,
    private val modelBills: BillsVM
) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewContactNum.text = contractNum

        viewHolder.seekBarScore.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                viewHolder.textViewMouth.text = progress.toString() + " мес."

//                sum = costCalculation(progress, 0, viewHolder.textViewSum)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun getLayout(): Int = R.layout.item_score_sub
}