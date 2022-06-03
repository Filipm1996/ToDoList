package com.example.todolist.ui
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.db.entities.Activity


class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder> (){
    private var array : List<Activity>? = null
    private var onClickDeleteItem :((Activity)->Unit)? = null
    private var onIsCheckedItem : ((Activity)->Unit)? = null
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.text)
        var deleteButton : ImageButton = view.findViewById(R.id.deleteButton)
        var checkBox : CheckBox = view.findViewById(R.id.isChecked)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view_element, parent, false)
        return ViewHolder(view)
    }

    @JvmName("setOnClickDeleteItem1")
    fun setOnClickDeleteItem(callback:(Activity)-> Unit){
        this.onClickDeleteItem = callback
    }

    @JvmName("setOnIsCheckedItem1")
    fun setOnIsCheckedItem(callback: (Activity) -> Unit){
        this.onIsCheckedItem = callback
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = array!![position]
        holder.textView.text = activity.description
        holder.checkBox.isChecked = activity.done.toBoolean()
        holder.deleteButton.setOnClickListener{ onClickDeleteItem?.invoke(activity) }
        holder.checkBox.setOnClickListener { onIsCheckedItem?.invoke(activity)}
    }

    override fun getItemCount(): Int {
        return array!!.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addItem (items: List<Activity>) {
        this.array = items
        notifyDataSetChanged()
    }
}