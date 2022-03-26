package com.example.exploreat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.exploreat.R
import com.example.exploreat.data.model.DomainPlaceModel
import com.squareup.picasso.Picasso


class PlacesRVAdapter(var places: ArrayList<DomainPlaceModel>,
                      val listener: PlaceClickListener):
    RecyclerView.Adapter<PlacesRVAdapter.PlaceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_list_item, parent, false)
        return PlaceHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        val item = places[position]
        holder.apply {
            containerView.setOnClickListener { listener.onPlaceClicked(item) }
            placeTitle.text = item.name
            placeKind.text = item.type
            placeAddress.text = item.address
            Picasso.get().load(item.iconUrl).into(placeIconImageView)
        }
    }

    override fun getItemCount(): Int = places.size

    fun replaceItems(newItems: ArrayList<DomainPlaceModel>) {
        places.clear()
        places.addAll(newItems)
        notifyDataSetChanged()
    }

    class PlaceHolder(v: View): RecyclerView.ViewHolder(v) {

        val placeIconImageView: ImageView = v.findViewById(R.id.place_image_view)
        val placeTitle: TextView = v.findViewById(R.id.place_title_text_view)
        val placeKind: TextView = v.findViewById(R.id.place_kind_text_view)
        val placeAddress: TextView = v.findViewById(R.id.place_address_text_view)
        val containerView: CardView = v.findViewById(R.id.container_view)

    }

    interface PlaceClickListener {
        fun onPlaceClicked(place: DomainPlaceModel)
    }

}