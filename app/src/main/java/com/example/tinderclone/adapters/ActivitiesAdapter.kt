import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tinderclone.R
import com.example.tinderclone.models.Activity
import com.example.tinderclone.utils.DatabaseHelper

class ActivitiesAdapter(private val activities: MutableList<Activity>,context : Context) : RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder>() {

    private lateinit var dbHelper: DatabaseHelper
    val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activity = activities[position]
        holder.bind(activity)
    }

    override fun getItemCount(): Int {
        return activities.size
    }

    inner class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val activityNameTextView: TextView = itemView.findViewById(R.id.activity_name)

        fun bind(activity: Activity) {
            activityNameTextView.text = activity.activity
        }
    }

    fun deleteItem(position: Int) {

        dbHelper = DatabaseHelper(this.context)
        dbHelper.deleteActivityByKey(activities.get(position).key)
        activities.drop(position)
        activities.removeAt(position)
        notifyItemRemoved(position)
    }
}