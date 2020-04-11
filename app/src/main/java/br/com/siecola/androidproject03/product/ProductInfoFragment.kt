package br.com.siecola.androidproject03.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.com.siecola.androidproject03.databinding.FragmentProductInfoBinding
import com.google.firebase.iid.FirebaseInstanceId
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi


class ProductInfoFragment : Fragment() {

    private val productInfoViewModel: ProductInfoViewModel by lazy {
        ViewModelProviders.of(this).get(ProductInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductInfoBinding.inflate(inflater)

        binding.setLifecycleOwner(this)

        binding.productInfoViewModel = productInfoViewModel

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    productInfoViewModel.fcmRegistrationId.value = task.result?.token
                }
            }

        if (this.arguments != null) {
            if (this.arguments!!.containsKey("productInfo")) {
                val moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<Product> =
                    moshi.adapter<Product>(Product::class.java)

                jsonAdapter.fromJson(this.arguments!!.getString("productInfo")!!).let {
                    productInfoViewModel.product.value = it
                }
            } else if (this.arguments!!.containsKey("salesMessage")) {
                productInfoViewModel.salesMessage.value = this.arguments!!.getString("salesMessage")
            }
        }
        return binding.root
    }
}