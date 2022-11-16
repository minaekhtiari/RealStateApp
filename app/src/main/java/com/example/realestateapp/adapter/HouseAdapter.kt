package com.example.realestateapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.realestateapp.HousePojo

import com.example.realestateapp.Commands
import com.example.realestateapp.R
import com.example.realestateapp.cons.Cons

import com.squareup.picasso.Picasso


class HouseAdapter(private var houseList: MutableList<HousePojo>
                   , private val onClickListener: OnClickListener,
                   private val lat: Int, private val lon:Int
) :
    RecyclerView.Adapter<HouseAdapter.HouseViewHolder>(), Filterable {

    var houseFilterList :MutableList<HousePojo>

    private lateinit var mContext: Context



    class HouseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val city: TextView = view.findViewById(R.id.textView_city)
        val zipcode: TextView = view.findViewById(R.id.textView_zipCod)
        val price: TextView = view.findViewById(R.id.textView_price)
        val bedRoomNumber:TextView=view.findViewById(R.id.textView_bed)
        val bathRoomNumber:TextView=view.findViewById(R.id.textView_bath)
        val layer:TextView=view.findViewById(R.id.textView_layer)
        val location:TextView=view.findViewById(R.id.textView_location)
        val img:ImageView=view.findViewById(R.id.imageView_city)


    }

    init {
        houseFilterList = houseList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_item, parent, false)
        val sch = HouseViewHolder(v)
        mContext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return houseFilterList.size
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {


        val house = houseFilterList[position]
       holder.city.text = house.city
        holder.location.text = Commands
            .distance(house.latitude,house.longitude,lat,lon).toString()
        holder.price.text ="$" + "%,d".format(house.price)
        holder.bedRoomNumber.text= house.bedrooms.toString()
        holder.bathRoomNumber.text=house.bathrooms.toString()
        holder.layer.text=house.id.toString()
        holder.zipcode.text=house.zip

        Picasso.get().load(Cons.IMAGE_URL + house.image)
            .into(holder.img)

        holder.itemView.setOnClickListener {
            // onItemClick?.invoke(house)
            onClickListener.onClick(house)
            Log.d("ONCLICK", "Clicked")


        }




    }
    class OnClickListener(val clickListener: (houseList: HousePojo) -> Unit) {
        fun onClick(houseList: HousePojo) = clickListener(houseList)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                houseFilterList = if (charString.isEmpty()) houseList else {
                    val filteredList = ArrayList<HousePojo>()
                    houseList
                        .filter {
                            (it.city!!.lowercase().contains(constraint!!))

                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = houseFilterList }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                houseFilterList = results?.values as MutableList<HousePojo>
                notifyDataSetChanged()


            }

        }
    }




}
