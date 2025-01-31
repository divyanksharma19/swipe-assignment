package com.divyank.swipeandroidassignment.ui.screens

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.divyank.swipeandroidassignment.R
import com.divyank.swipeandroidassignment.data.models.Product
import com.divyank.swipeandroidassignment.databinding.FragmentAddNewProductBinding
import com.divyank.swipeandroidassignment.ui.viewmodels.ProductViewModel
import com.divyank.swipeandroidassignment.utils.UiState
import com.divyank.swipeandroidassignment.utils.isValidImage
import com.divyank.swipeandroidassignment.utils.toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.android.ext.android.inject
import java.io.File


class AddNewProductFragment : Fragment() {

    private lateinit var binding: FragmentAddNewProductBinding
    private val viewModel by inject<ProductViewModel>()


    private var selectedImageUri: Uri? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it

                if (it.isValidImage(requireContext())) {
                    binding.editTextImageFileName.setText(it.lastPathSegment)
                }

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNewProductBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        val productTypes = resources.getStringArray(R.array.product_types)
        val spinnerAdapter = ArrayAdapter(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, productTypes)
        binding.spinnerProductType.adapter = spinnerAdapter

        binding.btnAddProduct.setOnClickListener {
            val productName = binding.editTextProductName.text.toString()
            val productType = binding.spinnerProductType.selectedItem.toString()
            val price = binding.editTextSellingPrice.text.toString().toDoubleOrNull()
            val tax = binding.editTextTaxRate.text.toString().toDoubleOrNull()

            if (validateInputs(productName, productType, price, tax)) {
                val product = Product(null, price!!, productName, productType, tax!!)
                val image = getImageMultipart() // Implement this function to get the MultipartBody.Part of the image

                viewModel.addProduct(product, image)
            }
        }

        binding.addProductBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnAddImage.setOnClickListener {
            getContent.launch("image/*")
        }


    }

    private fun observeViewModel() {

        viewModel.addProductResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is UiState.Loading -> {
                    binding.addProductProgress.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    binding.addProductProgress.visibility = View.GONE
                    if (result.data) {
                        toast("Product added successfully!")
                        findNavController().navigateUp()
                    } else {
                            toast("Failed to add product.")
                    }
                }

                is UiState.Failure -> {
                    binding.addProductProgress.visibility = View.GONE
                    toast("Failed to add product.")
                    Log.e("add", "${result.error}")
                }
            }


        }
    }


    private fun getImageMultipart(): MultipartBody.Part? {
        selectedImageUri?.let { uri ->
            // Convert the image URI to a File
            val imageFile = File(uri.path ?: "")
            val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())

            val imagedata = MultipartBody.Part.createFormData("image", imageFile.name, imageRequestBody)
           Log.e("AddNewProductFragment","$imagedata")
            return imagedata
        }

        return null
    }

    private fun validateInputs(
        productName: String,
        productType: String,
        price: Double?,
        tax: Double?
    ): Boolean {
        if (productName.isEmpty()) {
            binding.editTextProductName.error = "Product name cannot be empty"
            return false
        }

        if (productType.isEmpty()) {
        toast("Please select a product type")
            return false
        }

        if (price == null) {
            binding.editTextSellingPrice.error = "Invalid price"
            return false
        }

        if (tax == null) {
            binding.editTextTaxRate.error = "Invalid tax rate"
            return false
        }

        return true
    }



}