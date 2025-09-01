package com.example.buscalog.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.buscalog.R
import com.example.buscalog.adapter.ProductsAdapter
import com.example.buscalog.api.RetrofitInstance
import com.example.buscalog.data.Manufacturer
import com.example.buscalog.data.ManufacturerSelector
import com.example.buscalog.data.NewProduct
import com.example.buscalog.data.Product
import com.example.buscalog.databinding.FragmentProductsBinding
import com.example.buscalog.function.SpinnerItem
import com.example.buscalog.function.customSpinnerAdapter
import com.example.buscalog.function.getDataFromApi
import com.example.buscalog.function.getSelectedOrNull
import com.example.buscalog.function.setSpinnerSelectionById
import com.example.buscalog.function.showError
import kotlinx.coroutines.launch

class ProductsFragment : Fragment(), ProductsAdapter.OnItemClickListener {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var adapter: ProductsAdapter
    private lateinit var productDataList: ArrayList<Product>
    private lateinit var gridLayoutManager: GridLayoutManager

    private var allManufacturersCache: MutableList<ManufacturerSelector>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(layoutInflater)

        updateFullList()
        preLoadAllManufacturer()

        binding.addButton.setOnClickListener{
            binding.hiddenCreateProductLayout.visibility = View.VISIBLE

            tryPopulateCreateSpinners()

            binding.hiddenCreateProductBody.setOnClickListener {
                binding.hiddenCreateProductLayout.visibility = View.GONE
            }
            binding.cancelNewProductButton.setOnClickListener {
                binding.hiddenCreateProductLayout.visibility = View.GONE
            }
            binding.createProduct.setOnClickListener{}
        }
        binding.createNewProductButton.setOnClickListener{
            binding.loading.loaderScreen.visibility = View.VISIBLE
            binding.createNewProductButton.isClickable = false

            val newProductName = binding.newProductName.text.toString()
            val newProductProtocol = binding.newProductProtocol.text.toString()
            val newProductManufacturer = binding.newProductManufacturer.getSelectedOrNull()

            when {
                newProductName.isBlank() -> showError(requireContext(),"Nome é obrigatório")
                newProductProtocol.isBlank() -> showError(requireContext(),"Protocolo é obrigatório")
                newProductManufacturer == null -> showError(requireContext(),"Fabricante é obrigatório")
                else -> {
                    val newProduct = NewProduct(
                        name = newProductName,
                        protocol = newProductProtocol,
                        munufactory_id = newProductManufacturer!!.first,
                        manufactory_label = newProductManufacturer.second
                    )

                    Log.d("API_SENDING_PRODUCT", "Enviando para API: $newProduct")
                    val permission = "product.store"
                    val api = RetrofitInstance.getApiService(requireContext(), permission)
                    lifecycleScope.launch{
                        try{
                            val response = api.newProduct(newProduct)
                            if (response.isSuccessful) {
                                Toast.makeText(requireContext(), "Produto Cadastrado", Toast.LENGTH_SHORT).show()
                                Log.e("API_SUCESS", "Product successfully injected in API: $response, novo product $newProduct")

                                binding.newProductName.text?.clear()
                                binding.newProductProtocol.text?.clear()
                                binding.newProductManufacturer.setSelection(0)

                                updateFullList()
                                binding.hiddenCreateProductLayout.visibility = View.GONE
                            }
                            else {
                                Log.e("API_ERROR", "Error trying to inject data in API: $response")
                                Log.e("API_ERROR_BODY", "Erro da API: ${response.errorBody()?.string()}")
                            }
                        } catch (e:Exception){} finally { binding.createNewProductButton.isClickable = true }
                    }
                }
            }
            binding.loading.loaderScreen.visibility = View.GONE
        }
        //terminar depois
        return binding.root
    }

    override fun onMoreInfoClick(product: Product) {
        val hiddenProductInfoLayout = binding.hiddenProductInfoLayout
        val hiddenProductInfoBody = binding.hiddenProductInfoBody
        val moreProductInfo = binding.moreProductInfo
        val productModel = binding.productModel
        val productProtocol = binding.productProtocol
        val productManufacturer = binding.productManufacturer

        hiddenProductInfoLayout.visibility = View.VISIBLE

        productModel.text = product.name
        productProtocol.text = product.protocol
        productManufacturer.text = product.manufacturer.name

        hiddenProductInfoBody.setOnClickListener {
            hiddenProductInfoLayout.visibility = View.GONE
        }
        moreProductInfo.setOnClickListener{}
    } //More info
    override fun onEditClick(product: Product){
        val hiddenEditProductLayout = binding.hiddenEditProductLayout
        val hiddenEditProductBody = binding.hiddenEditProductBody
        val editProduct = binding.editProduct
        val editProductName = binding.editProductName
        val editProductProtocol = binding.editProductProtocol
        val editProductManufacture = binding.editProductManufacture
        val editProductButton = binding.editProductButton

        hiddenEditProductLayout.visibility = View.VISIBLE

        editProductName.setText(product.name)
        editProductProtocol.setText(product.protocol)

        tryPopulateEditSpinners()
        setSpinnerSelectionById(editProductManufacture, product.manufacturer.id)

        editProductButton.setOnClickListener {
            val selectedManufacturerPair = editProductManufacture.getSelectedOrNull()
            val selectedManufacturer = selectedManufacturerPair?.let { ManufacturerSelector(it.first, it.second)}

            when {
                editProductName.text.isBlank() -> showError(requireContext(),"Nome não pode estar vazio")
                editProductProtocol.text.isBlank() -> showError(requireContext(),"Protocolo não pode estar vazio")
                selectedManufacturer == null -> showError(requireContext(),"Fabricante não pode estar vazio")
                else -> {
                    val updatedManufacturer = Manufacturer(
                        selectedManufacturer.id, selectedManufacturer.name
                    )
                    val updatedProduct = product.copy(
                        name = editProductName.text.toString(),
                        protocol = editProductProtocol.text.toString(),
                        manufacturer = updatedManufacturer
                    )

                    updateProduct(updatedProduct)
                }
            }
        }
        hiddenEditProductBody.setOnClickListener {
            hiddenEditProductLayout.visibility = View.GONE
        }
        editProduct.setOnClickListener{}
    }
    private fun updateProduct(updatedProduct: Product) {
        binding.editProductButton.isClickable = false
        binding.loading.loaderScreen.visibility = View.VISIBLE

        Log.d("API_UPDATE_PRODUCT", "Enviando para API: $updatedProduct")
        val permission = "product.update"
        val api = RetrofitInstance.getApiService(requireContext(), permission)
        lifecycleScope.launch{
            try {
                val response = api.updateProduct(updatedProduct.id, updatedProduct)
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "Status: ${response.body()?.status}, Message: ${response.body()?.text}")
                    Toast.makeText(requireContext(), "Produto Atualizadp", Toast.LENGTH_SHORT).show()

                    updateFullList()
                    binding.hiddenEditProductLayout.visibility = View.GONE
                }
                else {
                    Log.e("API_ERROR1", "Error trying to inject data in API: $response")
                    Log.e("API_ERROR_UPDATE", "Erro ao atualizar empresa. Código: ${response.code()}. Erro: ${response.errorBody()?.toString()}")
                }
            } catch (e: Exception) {} finally { binding.editProductButton.isClickable = true }
        }
        binding.loading.loaderScreen.visibility = View.GONE
    }

    override fun onDeleteClick(productId: Int){
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Deletar Produto")
            .setMessage("Você tem certeza que quer deletar esse produto?")
            .setPositiveButton("Sim") { _, _ ->
                deleteProduct(productId)
            }
            .setNegativeButton("Não", null)
            .create()

        dialog.setOnShowListener{
            dialog.window?.setBackgroundDrawableResource(android.R.color.white)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(android.graphics.Color.RED)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(requireContext(), R.color.blue))
        }
        dialog.show()
    }
    private fun deleteProduct(productId: Int){
        val permission = "product.destroy"
        val api = RetrofitInstance.getApiService(requireContext(), permission)
        lifecycleScope.launch{
            try {
                val response = api.destroyProduct(productId)
                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "Status: ${response.body()?.status}, Message: ${response.body()?.text}")
                    Toast.makeText(requireContext(), "Produto Deletado", Toast.LENGTH_SHORT).show()
                    updateFullList()
                }
                else{
                    Log.e("API_ERROR1", "Error trying to inject data in API: $response")
                    Log.e("API_ERROR_DELETE", "Erro ao deletar produto ${productId}. Código: ${response.code()}. Erro: ${response.errorBody()?.toString()}")
                }
            } catch (e:Exception) {}
        }
    }

    private fun updateFullList(){
        //Loading screen
        binding.productsRecycle.loader.visibility = View.VISIBLE
        binding.productsRecycle.recyclerView.visibility = View.GONE

        //Recycler View
        gridLayoutManager = GridLayoutManager(requireContext(), 1)
        binding.productsRecycle.recyclerView.layoutManager = gridLayoutManager

        //Getting productDataList
        productDataList = ArrayList()
        adapter = ProductsAdapter(requireContext(), productDataList, this)
        binding.productsRecycle.recyclerView.adapter = adapter

        //Getting permission
        val permission = "product.index"
        val api = RetrofitInstance.getApiService(requireContext(), permission)
        lifecycleScope.launch{
            try{
                val response = api.getProducts()
                productDataList.clear()
                productDataList.addAll(response)
                adapter.updateFullList(productDataList)

            } catch (e: Exception){
                Log.e("API_ERROR", "Error trying to acess API: ${e.message}")
            }
            binding.productsRecycle.loader.visibility = View.GONE
            binding.productsRecycle.recyclerView.visibility = View.VISIBLE
        }
    }
    private fun preLoadAllManufacturer(){
        lifecycleScope.launch{
            try{
                allManufacturersCache = getDataFromApi(requireContext(), "manufacturer.index")
                { getManufacturersId() }
            } catch (e: Exception) {
                Log.e("API_PRELOAD_ROLES_ERROR", "Erro ao carregar cargos: ${e.message}")
            }
        }
    }

    private fun tryPopulateCreateSpinners(){
        if(binding.hiddenCreateProductLayout.visibility != View.VISIBLE) return

        val allManufacturers = allManufacturersCache
        val associatedManufacturers = mutableListOf(SpinnerItem(0, "Fabricante*"))
        allManufacturers?.forEach { manufacturer ->
            associatedManufacturers.add(SpinnerItem(manufacturer.id, manufacturer.name))}
        binding.newProductManufacturer.adapter = customSpinnerAdapter(requireContext(), associatedManufacturers.distinctBy { it.id })
        binding.newProductManufacturer.setSelection(0)

    }
    private fun tryPopulateEditSpinners(){
        if(binding.hiddenEditProductLayout.visibility != View.VISIBLE) return

        val allManufacturers = allManufacturersCache
        val associatedManufacturers = mutableListOf(SpinnerItem(0, "Fabricante*"))
        allManufacturers?.forEach { manufacturer ->
            associatedManufacturers.add(SpinnerItem(manufacturer.id, manufacturer.name))}
        binding.editProductManufacture.adapter = customSpinnerAdapter(requireContext(), associatedManufacturers.distinctBy { it.id })
    }
}
