package sempati.star.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import sempati.star.app.activity.TicketDoneAct;
import sempati.star.app.databinding.TransaksiRawBinding;
import sempati.star.app.models.TransaksiModel;

public class TransaksiManifestDetailAdapter extends RecyclerView.Adapter<TransaksiManifestDetailAdapter.ViewHolder> {

    private Context context;
    private List<TransaksiModel> list;
    private TransaksiRawBinding v;

    public TransaksiManifestDetailAdapter(Context context, List<TransaksiModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = TransaksiRawBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setDataKeView(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(TransaksiRawBinding view) {
            super(view.getRoot());
        }

        public void setDataKeView(int position) {
            TransaksiModel transaksiModel = list.get(position);

            v.tvDari.setText(transaksiModel.getAsal_nama_agen());
            v.tvKe.setText(transaksiModel.getTujuan_nama_agen());
            v.tvDari2.setText(transaksiModel.getAsal_kode_agen());
            v.tvKe2.setText(transaksiModel.getTujuan_kode_agen());
            v.tvNama.setText(transaksiModel.getTrayek_nama());
            v.tvJam.setText(transaksiModel.getKeberangkatan_jam()+ " WIB");
            v.tvHarga.setText(formatRupiah(Double.valueOf(transaksiModel.getHarga_tiket())));
//            v.tvHarga.setVisibility(View.GONE);
//            v.imgNaik.setVisibility(View.GONE);
            v.tvKelas.setText(transaksiModel.getKelas_armada_nama());
            v.tvSisaKursi.setText("Jumlah "+transaksiModel.getKelas_armada_jumlah_seat()+ " kursi");

            v.btnDetail.setOnClickListener(x -> {
//                Toast.makeText(context, "test klik", Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                String json = gson.toJson(transaksiModel);
                context.startActivity(new Intent(context, TicketDoneAct.class)
                        .putExtra("data", json)
                        .putExtra("from", "fame")
                );
            });
        }
    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}
