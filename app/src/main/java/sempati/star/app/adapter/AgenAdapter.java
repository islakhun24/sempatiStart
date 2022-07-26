package sempati.star.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sempati.star.app.R;
import sempati.star.app.models.Agen;

public class AgenAdapter  extends BaseAdapter implements Filterable {

    private ArrayList<Agen> agenList;
    private ArrayList<Agen> agenListFiltered;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView namaAgen;
    }

    public AgenAdapter(ArrayList<Agen> data, Context context) {
        this.agenList = data;
        this.agenListFiltered = data;
        this.mContext = context;

    }


    private int lastPosition = -1;


    @Override
    public int getCount() {
        if (agenListFiltered == null){
            return 0;
        }
        return agenListFiltered.size() ;
    }

    @Override
    public Object getItem(int position) {
        return agenListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return agenListFiltered.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Agen agen = agenListFiltered.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_agen, parent, false);
            viewHolder.namaAgen = (TextView) convertView.findViewById(R.id.nama_agen);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.modal_out : R.anim.modal_in);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.namaAgen.setText(agen.getNamaAgen());
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = agenList.size();
                    filterResults.values = agenList;

                } else {
                    List<Agen> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (Agen itemsModel : agenList) {
                        if (itemsModel.getNamaAgen().toLowerCase().contains(searchStr)) {
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }
                Log.e( "performFiltering: ", filterResults.values.toString());
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                agenListFiltered = (ArrayList<Agen>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }

}