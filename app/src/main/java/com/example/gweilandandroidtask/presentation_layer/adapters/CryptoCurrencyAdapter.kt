package com.example.gweilandandroidtask.presentation_layer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.gweilandandroidtask.R
import com.example.gweilandandroidtask.data_layer.remote_data_source.models.Data
import com.example.gweilandandroidtask.databinding.ItemCryptocurrencyBinding
import formatDollarPrice

class CryptoCurrencyAdapter(private val listCrypto: List<Data>) :
    ListAdapter<Data, CryptoCurrencyAdapter.CryptoCurrencyViewHolder>(CryptoCurrencyDiffUtil()),
    Filterable {

    private var binding: ItemCryptocurrencyBinding? = null

    private var cryptoFilteredList: List<Data> = listCrypto

    inner class CryptoCurrencyViewHolder(private val itemCryptocurrencyBinding: ItemCryptocurrencyBinding) :
        ViewHolder(itemCryptocurrencyBinding.root) {

        fun bind(item: Data) {

            itemCryptocurrencyBinding.apply {
                txtShortName.text = item.symbol
                txtLongName.text = item.name
                txtUsdPrice.text = "\$${item.quote?.uSD?.price?.formatDollarPrice()} USD"
                val volumeChange24H = item.quote?.uSD?.volumeChange24h ?: 0.0
                when {
                    volumeChange24H >= 0.0 -> {
                        txtChangeInPercentage.setTextColor(
                            ContextCompat.getColor(
                                root.context,
                                android.R.color.holo_green_dark
                            )
                        )
                        Glide.with(ivCryptoGraph).load(
                            ContextCompat.getDrawable(
                                root.context,
                                R.drawable.icon_24h_positive
                            )
                        )
                            .into(ivCryptoGraph)
                    }

                    volumeChange24H < 0.0 -> {
                        txtChangeInPercentage.setTextColor(
                            ContextCompat.getColor(
                                root.context,
                                android.R.color.holo_red_dark
                            )
                        )
                        Glide.with(ivCryptoGraph).load(
                            ContextCompat.getDrawable(
                                root.context,
                                R.drawable.icon_24h_negative
                            )
                        )
                            .into(ivCryptoGraph)
                    }
                }
                txtChangeInPercentage.text = "${item.quote?.uSD?.volumeChange24h.toString()}%"

                Glide.with(ivCryptoIcon).load(
                    root.context.getString(
                        R.string.load_crypto_logo,
                        item.id.toString()
                    )
                ).into(ivCryptoIcon)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoCurrencyViewHolder {
        this.binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_cryptocurrency,
            parent,
            false
        )

        return CryptoCurrencyViewHolder(this.binding!!)
    }

    override fun onBindViewHolder(holder: CryptoCurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CryptoCurrencyDiffUtil : ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem == newItem

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.isNullOrEmpty()) {
                    cryptoFilteredList = listCrypto
                } else {
                    val filterList = mutableListOf<Data>()
                    listCrypto.forEach { out ->
                        if (out.name?.lowercase()?.contains(p0.toString().lowercase()) == true
                            || out.symbol?.lowercase()?.contains(p0.toString().lowercase()) == true
                        ) {
                            filterList.add(out)
                        }
                    }
                    cryptoFilteredList = filterList
                }
                return FilterResults().apply {
                    values = cryptoFilteredList
                }
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                cryptoFilteredList = (p1?.values!! as? List<Data>)!!
                submitList(cryptoFilteredList)
            }

        }
    }

}