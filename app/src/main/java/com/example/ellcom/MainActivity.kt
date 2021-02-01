package com.example.ellcom

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ellcom.ui.AuthFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Change text color status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

        initNavigationComponent()

        hasEntered()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun initNavigationComponent() {
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.authFragment, R.id.mainScreenFragment))
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottom_nav.setupWithNavController(navController)
        title = ""

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // На каких страницах будет отображаться нижняя навигация
            when (destination.id) {
                R.id.mainScreenFragment,
                R.id.balanceFragment,
                R.id.profileFragment,
                R.id.contactFragment -> bottom_nav.visibility = View.VISIBLE
                else -> bottom_nav.visibility = View.GONE
            }
            // ---------

            textViewTitle.text = when (destination.id) {
                R.id.authFragment -> {
                    toolbar.visibility = View.GONE
                    bottom_nav.visibility = View.GONE
                    ""
                }
                R.id.mainScreenFragment -> {
                    toolbar.visibility = View.GONE
                    ""
                }
                R.id.balanceFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.setNavigationIcon(R.drawable.ic_arrow)
                    "Баланс"
                }
                R.id.contactFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.setNavigationIcon(R.drawable.ic_arrow)
                    "Контакты"
                }
                R.id.profileFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.setNavigationIcon(R.drawable.ic_arrow)
                    val contractNum =
                        getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
                            .getString("contractNum", "")
                    "№$contractNum"
                }
                R.id.subContractListFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.setNavigationIcon(R.drawable.ic_arrow)
                    "Субдоговора"
                }
                R.id.changePasswordFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.setNavigationIcon(R.drawable.ic_arrow)
                    "Смена договора"
                }
                R.id.sessionFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.setNavigationIcon(R.drawable.ic_arrow)
                    "Экран сессии"
                }
                R.id.trafficFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.setNavigationIcon(R.drawable.ic_arrow)
                    "Экран трафика"
                }
                else -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.setNavigationIcon(R.drawable.ic_arrow)
                    ""
                }
            }
        }
    }

    private fun hasEntered() {
        val hasEntered =
            getSharedPreferences("SP_INFO", Context.MODE_PRIVATE).getBoolean("hasEntered", false)
        if (hasEntered) {
            findNavController(R.id.nav_host_fragment)
                .navigate(AuthFragmentDirections.actionAuthFragmentToNavigationToMainContent())
        }
    }
}