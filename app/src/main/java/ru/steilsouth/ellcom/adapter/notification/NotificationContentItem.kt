package ru.steilsouth.ellcom.adapter.notification

import android.util.Log
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_content_notification.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.pojo.notification.MessageNotification
import ru.steilsouth.ellcom.repository.MainAndSubRepository
import java.text.SimpleDateFormat
import java.util.*


open class NotificationContentItem(
    private val item: MessageNotification
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.textViewTitle.text = item.title
        viewHolder.textViewDate.text = date(item.dateSend)
        viewHolder.textViewContent.text = item.text
    }

    override fun getLayout(): Int = R.layout.item_content_notification

    private fun date(date: Long): String {
        val time = SimpleDateFormat("H", Locale("ru")).format(Date(date))
        return when {
            time.toInt() <= 1 -> "сейчас"
            time.toInt() < 8 -> "$time ч.назад"
            else -> SimpleDateFormat("dd.MM.yyyy", Locale("ru")).format(Date(date))
        }
    }
}