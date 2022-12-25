package sempati.star.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sempati.star.app.databinding.ItemLayoutBinding;
import sempati.star.app.models.Agen;

public class ListFameAdapter extends RecyclerView.Adapter<ListFameAdapter.ViewHolder> {
    private Context context;
    private List<Agen> list;
    private ItemLayoutBinding v;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       v = ItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setDataKeView(position);
    }

    @Override
    public int getItemViewType(int position) {
        return  position;
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public ListFameAdapter(Context context, List<Agen> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(ItemLayoutBinding view){
            super(view.getRoot());
        }

        public void setDataKeView(int position) {
            v.label.setText(list.get(position).getNamaAgen());
        }
    }

}
