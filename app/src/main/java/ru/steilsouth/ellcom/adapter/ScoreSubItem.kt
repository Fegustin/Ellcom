package ru.steilsouth.ellcom.adapter

import android.widget.SeekBar
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_score_sub.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.pojo.subcontracts.SubContractsResult
import ru.steilsouth.ellcom.ui.viewpager.costCalculation


class ScoreSubItem(val item: SubContractsResult.Data.Result) : Item() {
    var quantity = 0
    var price = 0

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewContactNum.text = "№${item.title}"

        viewHolder.seekBarScore.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                viewHolder.textViewMouth.text = progress.toString() + " мес."
                val tariffPrice = item.rateList[0].tariffPrice.toDouble().toInt()

                quantity = progress
                price = costCalculation(
                    progress,
                    tariffPrice,
                    viewHolder.textViewSum
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun getLayout(): Int = R.layout.item_score_sub
}