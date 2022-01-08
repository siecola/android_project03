package br.com.siecola.androidproject03.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.com.siecola.androidproject03.databinding.FragmentProductInfoBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.messaging.FirebaseMessaging
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

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                productInfoViewModel.fcmRegistrationId.value = task.result
                return@OnCompleteListener
            }
        })

        if (this.arguments?.containsKey("productInfo") == true) {
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<Product> =
                moshi.adapter<Product>(Product::class.java)

            jsonAdapter.fromJson(this.requireArguments().getString("productInfo")!!).let {
                productInfoViewModel.product.value = it
            }
        } else if (this.arguments?.containsKey("salesMessage") == true) {
            productInfoViewModel.salesMessage.value = this.requireArguments().getString("salesMessage")
        }
        return binding.root
    }
}