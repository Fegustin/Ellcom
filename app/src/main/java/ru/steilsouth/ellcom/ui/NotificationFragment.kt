package ru.steilsouth.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.groupiex.plusAssign
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_notification.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.adapter.decoration.SwipeTouchCallback
import ru.steilsouth.ellcom.adapter.notification.NotificationContentItem
import ru.steilsouth.ellcom.adapter.notification.NotificationHeaderItem
import ru.steilsouth.ellcom.adapter.notification.SwipeToDeleteItem
import ru.steilsouth.ellcom.pojo.notification.MessageNotification
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM


class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private val model: MainAndSubVM by activityViewModels()
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val adapterNew = GroupAdapter<GroupieViewHolder>()
    private var section = Section()
    private var sectionNew = Section()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewNotification.adapter = adapter
        recyclerViewNotificationNew.adapter = adapterNew
        recyclerViewNotification.isNestedScrollingEnabled = false
        recyclerViewNotificationNew.isNestedScrollingEnabled = false
        ItemTouchHelper(touchCallback).attachToRecyclerView(recyclerViewNotification)
        ItemTouchHelper(touchCallbackNew).attachToRecyclerView(recyclerViewNotificationNew)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (token != null) {

            getNotificationList(token, true)
            getNotificationList(token, false)

            swipeRefresh.setOnRefreshListener {
                adapter.clear()
                adapterNew.clear()
                section.clear()
                sectionNew.clear()

                getNotificationList(token, true)
                getNotificationList(token, false)
            }
        }
    }

    private fun getNotificationList(token: String, notConfirm: Boolean) {
        if (!isOnline(requireContext())) {
            swipeRefresh.isRefreshing = false
            return
        }
        model.getNotificationList(token, notConfirm, 0).observe(viewLifecycleOwner) {
            if (it.status == "ok") {
                val listNotificationItem = mutableListOf<NotificationContentItem>()
                for (i in it.data.res) {
                    listNotificationItem.add(SwipeToDeleteItem(i))
                    readNotification(token, i.id.toString())
                }
                if (notConfirm && listNotificationItem.isNotEmpty()) {
                    sectionNew.setHeader(NotificationHeaderItem("Новые уведомления"))
                    sectionNew.addAll(listNotificationItem)
                    adapterNew += sectionNew
                }
                if (!notConfirm && listNotificationItem.isNotEmpty()) {
                    section.setHeader(NotificationHeaderItem("Прочитанные уведомления"))
                    section.addAll(listNotificationItem)
                    adapter += section
                }
            } else Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            swipeRefresh.isRefreshing = false
        }

    }

    private fun readNotification(token: String, notificationIds: String) {
        if (!isOnline(requireContext())) {
            swipeRefresh.isRefreshing = false
            return
        }
        model.readNotification(token, notificationIds).observe(viewLifecycleOwner) {
            if (it.status != "ok") {
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
            }
            swipeRefresh.isRefreshing = false
        }
    }

    private fun deleteNotification(token: String, notificationId: Int) {
        if (!isOnline(requireContext())) {
            swipeRefresh.isRefreshing = false
            return
        }
        model.deleteNotification(token, notificationId).observe(viewLifecycleOwner) {
            if (it.status != "ok") {
                Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private val touchCallback: SwipeTouchCallback by lazy {
        object : SwipeTouchCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteSection(section, viewHolder.adapterPosition, adapter)
            }
        }
    }

    private val touchCallbackNew: SwipeTouchCallback by lazy {
        object : SwipeTouchCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteSection(sectionNew, viewHolder.adapterPosition, adapterNew)
            }
        }
    }

    private fun deleteSection(section: Section, position: Int, adapter: GroupAdapter<GroupieViewHolder>) {
        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
                ?.getString("token", "")

        val item = adapter.getItem(position)
        section.remove(item)
        if (token != null) {
            val newItem = item as? SwipeToDeleteItem
            newItem?.getIdNotification()?.let { deleteNotification(token, it) }
        }
    }
}