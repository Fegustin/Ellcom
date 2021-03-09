package ru.steilsouth.ellcom.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_notification.*
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.adapter.NotificationContentItem
import ru.steilsouth.ellcom.adapter.NotificationHeaderItem
import ru.steilsouth.ellcom.pojo.notification.MessageNotification
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.viewmodal.MainAndSubVM


class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private val model: MainAndSubVM by activityViewModels()
    private val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewNotification.adapter = adapter
        ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(recyclerViewNotification)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        if (token != null) {
            getNotificationList(token, true)
            getNotificationList(token, false)

            swipeRefresh.setOnRefreshListener {
                adapter.clear()
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
                    listNotificationItem.add(NotificationContentItem(i))
                }
                if (notConfirm) divisionIntoSections("Новые уведомления", listNotificationItem)
                else {
                    listNotificationItem.add(
                        NotificationContentItem(
                            MessageNotification(
                                123,
                                1234,
                                "sdadsda",
                                "sdads",
                                "sdad",
                                false,
                                1233334
                            )
                        )
                    )
                    divisionIntoSections("Прочитанные уведомления", listNotificationItem)
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

    private fun divisionIntoSections(
        title: String,
        listNotificationItem: List<NotificationContentItem>
    ): ExpandableGroup {
        return ExpandableGroup(NotificationHeaderItem(title), true).apply {
            add(Section(listNotificationItem))
            adapter.add(this)
            adapter.notifyDataSetChanged()
        }
    }

    private fun itemTouchHelperCallback() =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val token =
                    activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
                        ?.getString("token", "")

                if (token != null) {

                    adapter.removeGroupAtAdapterPosition(viewHolder.adapterPosition)
                    adapter.notifyDataSetChanged()
                }
            }
        }
}