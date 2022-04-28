import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.rickandmortyguide.R
import com.squareup.picasso.Picasso

class AdapterList(private var activity: Activity, private var items: MutableList<HashMap <String, Any>>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var nameTextView: TextView? = null
        var speciesTextView: TextView? = null
        var genderTextView: TextView? = null
        var imageView: ImageView? = null
        init {
            this.nameTextView = row?.findViewById(R.id.name)
            this.speciesTextView = row?.findViewById(R.id.species)
            this.genderTextView = row?.findViewById(R.id.gender)
            this.imageView = row?.findViewById(R.id.avatar)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.list_pod, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var userDto = items.get(position)
        viewHolder.nameTextView?.text = userDto.get("Name").toString()
        viewHolder.speciesTextView?.text = userDto.get("Species").toString()
        viewHolder.genderTextView?.text = userDto.get("Gender").toString()
        Picasso.get().load(userDto.get("Image").toString()).into(viewHolder.imageView)

        return view as View
    }

    override fun getItem(i: Int): HashMap<String, Any> {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}