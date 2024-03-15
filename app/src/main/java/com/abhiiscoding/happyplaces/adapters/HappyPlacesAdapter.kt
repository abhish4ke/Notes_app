package com.abhiiscoding.happyplaces.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhiiscoding.happyplaces.R
import com.abhiiscoding.happyplaces.activities.AddHappyPlaceActivity
import com.abhiiscoding.happyplaces.activities.MainActivity
import com.abhiiscoding.happyplaces.database.DatabaseHandler
import com.abhiiscoding.happyplaces.databinding.ItemHappyPlaceBinding
import com.abhiiscoding.happyplaces.models.HappyPlaceModel

open class HappyPlacesAdapter(
    private val context: Context,
    private var list: ArrayList<HappyPlaceModel>
) : RecyclerView.Adapter<HappyPlacesAdapter.MyViewHolder>() {

    private var onclickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHappyPlaceBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.binding.ivPlaceImage.setImageURI(Uri.parse(model.image))
            holder.binding.tvTitle.text = model.title

            holder.itemView.setOnClickListener {
                if (onclickListener != null){
                    onclickListener!!.onClick(position, model)
                }
            }
        }
    }

    fun setOnclickListener(onClickListener: OnClickListener){
        this.onclickListener = onClickListener
    }

    fun removeAt(position: Int){
        val dbHandler = DatabaseHandler(context)
        val isDeleted = dbHandler.deleteHappyPlace(list[position])
        if (isDeleted > 0){
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun notifyEditItem(activity: Activity, position: Int, requestCode: Int){
        val intent = Intent(context, AddHappyPlaceActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, list[position])
        activity.startActivityForResult(intent, requestCode)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnClickListener{
        fun onClick(position: Int, model: HappyPlaceModel){

        }
    }

    class MyViewHolder(val binding: ItemHappyPlaceBinding) : RecyclerView.ViewHolder(binding.root)
}




















//// TODO (Step 6: Creating an adapter class for binding it to the recyclerview in the new package which is adapters.)
//// START
//open class HappyPlacesAdapter(
//    private val context: Context,
//    private var list: ArrayList<HappyPlaceModel>
//) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    /**
//     * Inflates the item views which is designed in xml layout file
//     *
//     * create a new
//     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
//     */
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val binding = ItemHappyPlaceBinding.inflate(LayoutInflater.from(context), parent, false)
//
//        return MyViewHolder(
//            LayoutInflater.from(context).inflate(
//                R.layout.item_happy_place,
//                parent,
//                false
//            )
//        )
//    }
//
//    /**
//     * Binds each item in the ArrayList to a view
//     *
//     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
//     * an item.
//     *
//     * This new ViewHolder should be constructed with a new View that can represent the items
//     * of the given type. You can either create a new View manually or inflate it from an XML
//     * layout file.
//     */
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val model = list[position]
//
//        if (holder is MyViewHolder) {
//            holder.itemView.iv_place_image.setImageURI(Uri.parse(model.image))
//            holder.itemView.tvTitle.text = model.title
//            holder.itemView.tvDescription.text = model.description
//        }
//    }
//
//    /**
//     * Gets the number of items in the list
//     */
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    /**
//     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
//     */
//    inner class MyViewHolder(private val binding: ItemHappyPlaceBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(model: HappyPlaceModel) {
//            // Access views using the binding variable
//            binding.ivPlaceImage.setImageURI(Uri.parse(model.image))
//            binding.tvTitle.text = model.title
//            binding.tvDescription.text = model.description
//        }
//    }
//}
//// END