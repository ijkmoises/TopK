package br.com.emesistemas.topk.presentation.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import br.com.emesistemas.topk.R
import br.com.emesistemas.topk.presentation.viewmodel.UiStateViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: UiStateViewModel by viewModel()

    private val navController by lazy {
        findNavController(R.id.main_activity_nav_host)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            viewModel.componentsLiveData.observe(this, Observer {

                it?.let { hasComponent ->

                    if (hasComponent.homeAsUpButton) {
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    } else {
                        supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    }

                    if (hasComponent.titleToolbar) {
                        supportActionBar?.title = destination.label
                    } else {
                        supportActionBar?.title = ""
                    }
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navController.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}