package ru.steilsouth.ellcom

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import ru.steilsouth.ellcom.ui.AuthFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Ellcom)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Изменить цвет текста в строке состояния
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
        // Настройка Navigation component
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.authFragment, R.id.mainScreenFragment))
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottom_nav.setupWithNavController(navController)
        // ------------
        title = ""

        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Изменение икони стрелочки назад                                          
            toolbar.setNavigationIcon(R.drawable.ic_arrow)      
                                                       
                                                       
            // На каких страницах будет отображаться нижняя навигация
            when (destination.id) {
                R.id.mainScreenFragment,
                R.id.balanceFragment,
                R.id.profileFragment,
                R.id.contactFragment -> bottom_nav.visibility = View.VISIBLE
                else -> bottom_nav.visibility = View.GONE
            }
            // ---------

            // Настройка отображени и текста туллбара
            textViewTitle.text = when (destination.id) {
                R.id.authFragment -> {
                    toolbar.visibility = View.INVISIBLE
                    ""
                }
                R.id.mainScreenFragment -> {
                    toolbar.visibility = View.GONE
                    ""
                }
                R.id.balanceFragment -> {
                    toolbar.visibility = View.VISIBLE
                    "Баланс"
                }
                R.id.contactFragment -> {
                    toolbar.visibility = View.VISIBLE
                    "Контакты"
                }
                R.id.profileFragment -> {
                    toolbar.visibility = View.VISIBLE
                    val contractNum =
                        getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
                            .getString("contractNum", "")
                    "№$contractNum"
                }
                R.id.subContractListFragment -> {
                    toolbar.visibility = View.VISIBLE
                    "Субдоговора"
                }
                R.id.changePasswordFragment -> {
                    toolbar.visibility = View.VISIBLE
                    "Смена договора"
                }
                R.id.sessionFragment -> {
                    toolbar.visibility = View.VISIBLE
                    "Экран сессии"
                }
                R.id.emailListFragment -> {
                    toolbar.visibility = View.VISIBLE
                    "Почта"
                }
                R.id.billsFragment -> {
                    toolbar.visibility = View.VISIBLE
                    "История платежей"
                }
                R.id.createBillsFragment -> {
                    toolbar.visibility = View.VISIBLE
                    "Формирование счета на оплату"
                }
                else -> {
                    toolbar.visibility = View.VISIBLE
                    ""
                }
            }
        }
    }

    // Проверка на авторизацию. Если не авторизованн то скидывает на страницу авторизации
    private fun hasEntered() {
        val hasEntered =
            getSharedPreferences("SP_INFO", Context.MODE_PRIVATE).getBoolean("hasEntered", false)
        if (hasEntered) {
            findNavController(R.id.nav_host_fragment)
                .navigate(AuthFragmentDirections.actionAuthFragmentToNavigationToMainContent())
        }
    }
}
