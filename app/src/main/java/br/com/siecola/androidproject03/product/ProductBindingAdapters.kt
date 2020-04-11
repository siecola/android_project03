package br.com.siecola.androidproject03.product

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("productPrice")
fun bindProductPrice(txtProductPrice: TextView, productPrice: Float?) {
    productPrice?.let {
        val price = "$ " + "%.2f".format(productPrice)
        txtProductPrice.text = price
    }
}

@BindingAdapter("productCode")
fun bindProductCode(txtProductCode: TextView, productCode: Int?) {
    productCode?.let {
        txtProductCode.text = "$productCode"
    }
}