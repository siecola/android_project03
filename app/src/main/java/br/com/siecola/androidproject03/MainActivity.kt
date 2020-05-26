package br.com.siecola.androidproject03

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import br.com.siecola.androidproject03.product.ProductInfoFragmentDirections
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseAnalytics.getInstance(this)
        FirebaseMessaging.getInstance().setDeliveryMetricsExportToBigQuery(true)

        if (this.intent.hasExtra("product")) {
            showProductInfo(intent.getStringExtra("product")!!)
        } else if (this.intent.hasExtra("salesMessage")) {
            showSalesMessage(intent.getStringExtra("salesMessage")!!)
        }
    }

    override fun onNewIntent(intent: Intent) {
        if (intent.hasExtra("product")) {
            showProductInfo(intent.getStringExtra("product")!!)
        } else if (intent.hasExtra("salesMessage")) {
            showSalesMessage(intent.getStringExtra("salesMessage")!!)
        }
        super.onNewIntent(intent)
    }

    private fun showProductInfo(productInfo: String) {
        this.findNavController(R.id.nav_host_fragment)
            .navigate(ProductInfoFragmentDirections.actionShowProductInfo(productInfo))
    }

    private fun showSalesMessage(salesMessage: String) {
        this.findNavController(R.id.nav_host_fragment)
            .navigate(ProductInfoFragmentDirections.actionShowSalesMessage(salesMessage))
    }
}