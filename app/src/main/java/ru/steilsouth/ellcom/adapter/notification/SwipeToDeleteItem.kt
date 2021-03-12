package ru.steilsouth.ellcom.adapter.notification

import androidx.recyclerview.widget.ItemTouchHelper
import ru.steilsouth.ellcom.pojo.notification.MessageNotification


class SwipeToDeleteItem(private val item: MessageNotification) : NotificationContentItem(item) {
    override fun getSwipeDirs(): Int {
        return ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    }

    fun getIdNotification(): Int {
        return item.id
    }
}