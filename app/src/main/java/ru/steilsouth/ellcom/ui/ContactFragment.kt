package ru.steilsouth.ellcom.ui

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_contact.*
import kotlinx.android.synthetic.main.fragment_main_screen.layoutContent
import kotlinx.android.synthetic.main.fragment_main_screen.progressBar
import ru.steilsouth.ellcom.R
import ru.steilsouth.ellcom.utils.enam.Path
import ru.steilsouth.ellcom.utils.isOnline
import ru.steilsouth.ellcom.utils.timerForWatchingMainContent
import ru.steilsouth.ellcom.viewmodal.ContactVM


class ContactFragment : Fragment(R.layout.fragment_contact), OnMapReadyCallback {

    private val model: ContactVM by activityViewModels()
    private lateinit var mMap: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token =
            activity?.getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)?.getString("token", "")

        timerForWatchingMainContent(progressBar, layoutContent)

        if (isOnline(requireContext())) {
            initMap()
            if (token != null) {
                getContact(token)

                managerPhone.setOnClickListener {
                    Toast.makeText(activity, "Номер скопирован", Toast.LENGTH_SHORT).show()
                    copyText(managerPhone.text.toString())
                }

                accountantPhone.setOnClickListener {
                    Toast.makeText(activity, "Номер скопирован", Toast.LENGTH_SHORT).show()
                    copyText(accountantPhone.text.toString())
                }

                imageButtonVK.setOnClickListener { openURL(Path.VK.path) }

                imageButtonInst.setOnClickListener { openURL(Path.Instagram.path) }
            }
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        if (p0 != null) {
            mMap = p0
        }

        val site = LatLng(42.98264334249988, 47.469737703757005)
        mMap.addMarker(
            MarkerOptions()
                .position(site)
                .title("Наш офис")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(site))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(site, 15.0f))
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun getContact(token: String) {
        if (isOnline(requireContext())) {
            model.getMobileContact(token).observe(viewLifecycleOwner) {
                if (it.status == "ok") {
                    managerName.text = it.data.res.manager
                    managerPhone.text = it.data.res.managerPhoneList[0].phone
                    accountantName.text = it.data.res.accountant
                    accountantPhone.text = it.data.res.accountantPhoneList[0].phone
                }
            }
        }
    }

    private fun copyText(text: String) {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip =
            ClipData.newPlainText("Main", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun openURL(url: String) {
        val page: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, page)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                activity,
                "Произошла ошибка при переходе, возможно в следущем обновление мы её исправим. ВОЗМОЖНО",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}