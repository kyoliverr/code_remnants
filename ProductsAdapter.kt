package com.example.buscalog.adapter

import com.example.buscalog.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buscalog.data.Product

class ProductsAdapter(
    private val context: android.content.Context,
    private var dataList: MutableList<Product>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<ProductHolder>(){

    private val fullList: MutableList<Product> = ArrayList(dataList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_products, parent, false)
        return ProductHolder(view)
    }
    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = dataList[position]

        holder.recProductName.text = product.name
        holder.recProductManufacturer.text = product.manufacturer.name
        holder.recProductProtocol.text = product.protocol

        holder.moreInfo.setOnClickListener {
            listener.onMoreInfoClick(product)
        }
        holder.edit.setOnClickListener {
            listener.onEditClick(product)
        }
        holder.delete.setOnClickListener {
            listener.onDeleteClick(product.id)
        }
    }

    fun updateFullList(newList: MutableList<Product>) {
        val distinctList = newList.distinctBy { it.id }
        fullList.clear()
        fullList.addAll(distinctList)

        dataList.clear()
        dataList.addAll(distinctList)

        notifyDataSetChanged()
    }
    fun filterList(query: String) {
        val lowerQuery = query.trim().lowercase()

        dataList = if (lowerQuery.isEmpty()) {
            ArrayList(fullList)
        } else {
            fullList.filter { product ->
                product.name.contains(lowerQuery, true) ||
                product.manufacturer.name.contains(lowerQuery, true) ||
                product.protocol.contains(lowerQuery, true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataList.size

    interface OnItemClickListener {
        fun onMoreInfoClick(product: Product)
        fun onEditClick(product: Product)
        fun onDeleteClick(productId: Int)
    }

}
class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    //TextView
    var recProductName: TextView = itemView.findViewById(R.id.recProductName)
    var recProductManufacturer: TextView = itemView.findViewById(R.id.recProductManufacturer)
    var recProductProtocol: TextView = itemView.findViewById(R.id.recProductProtocol)

    //Buttons
    var moreInfo: ImageButton = itemView.findViewById(R.id.moreInfo)
    var edit: ImageButton = itemView.findViewById(R.id.edit)
    var delete: ImageButton = itemView.findViewById(R.id.delete)
}