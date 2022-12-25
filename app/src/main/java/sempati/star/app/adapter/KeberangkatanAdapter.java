package sempati.star.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sempati.star.app.R;
import sempati.star.app.activity.SeatAct;
import sempati.star.app.activity.TiketResultAct;
import sempati.star.app.models.Trayek;

public class KeberangkatanAdapter extends RecyclerView.Adapter<KeberangkatanAdapter.MyViewHolder> {

    private ArrayList<Trayek> trayekArrayList;
    private Context context;
    private int jumlahKursi;
    private String jumlahKursiString;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout btnBeli;
        TextView tvNama, tvDari, tvKe, tvDari2, tvKe2;
        TextView tvJam;
        TextView tvHarga;
        TextView tvSisaKursi;
        TextView tvKelas;
        ImageView imgNaik;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.tvNama = (TextView) itemView.findViewById(R.id.tvNama);
            this.btnBeli = (LinearLayout) itemView.findViewById(R.id.btnBeli);
            this.tvDari = (TextView) itemView.findViewById(R.id.tvDari);
            this.tvKe = (TextView) itemView.findViewById(R.id.tvKe);
            this.tvDari2 = (TextView) itemView.findViewById(R.id.tvDari2);
            this.tvKe2 = (TextView) itemView.findViewById(R.id.tvKe2);
            this.tvJam = (TextView) itemView.findViewById(R.id.tvJam);
            this.tvHarga = (TextView) itemView.findViewById(R.id.tvHarga);
            this.tvSisaKursi = (TextView) itemView.findViewById(R.id.tvSisaKursi);
            this.tvKelas = (TextView) itemView.findViewById(R.id.tvKelas);
            this.imgNaik = (ImageView) itemView.findViewById(R.id.imgNaik);
        }
    }

    public KeberangkatanAdapter(ArrayList<Trayek> trayeks, Context context, int jumlahKursi, String jumlahKursiString) {
        this.trayekArrayList = trayeks;
        this.context = context;
        this.jumlahKursi = jumlahKursi;
        this.jumlahKursiString = jumlahKursiString;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_search_tiket, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        LinearLayout btnBeli = holder.btnBeli;
        TextView tvNama = holder.tvNama;
        TextView tvJam = holder.tvJam;
        TextView tvDari = holder.tvDari;
        TextView tvKe = holder.tvKe;
        TextView tvDari2 = holder.tvDari2;
        TextView tvKe2 = holder.tvKe2;
        TextView tvHarga = holder.tvHarga;
        TextView tvSisaKursi = holder.tvSisaKursi;
        TextView tvKelas = holder.tvKelas;
        ImageView imgNaik = holder. imgNaik;
        String rupiah, rupiahNoFormat;
        if(trayekArrayList.get(listPosition).getPerubahanHarga() == 0){
            rupiah = formatRupiah(Double.valueOf(trayekArrayList.get(listPosition).getHargaTrayek()));
            rupiahNoFormat = String.valueOf(trayekArrayList.get(listPosition).getHargaTrayek());
        }else {
            rupiah = formatRupiah(Double.valueOf(trayekArrayList.get(listPosition).getPerubahanHarga()));
            rupiahNoFormat = String.valueOf(trayekArrayList.get(listPosition).getPerubahanHarga());
        }

        tvDari.setText(trayekArrayList.get(listPosition).getTblTrayek().getAsal().getNamaAgen());
        tvKe.setText(trayekArrayList.get(listPosition).getTblTrayek().getTujuan().getNamaAgen());
        tvDari2.setText(trayekArrayList.get(listPosition).getTblTrayek().getAsal().getKodeAgn());
        tvKe2.setText(trayekArrayList.get(listPosition).getTblTrayek().getTujuan().getKodeAgn());
        tvNama.setText(trayekArrayList.get(listPosition).getTblTrayek().getNamaTrayek());
        tvJam.setText(trayekArrayList.get(listPosition).getTblKeberangkatan().getJam()+ " WIB");
        tvHarga.setText(rupiah);
        tvKelas.setText(trayekArrayList.get(listPosition).getTblKeberangkatan().getRefLambung().getRefKelaArmada().getNamaKelas());
        tvSisaKursi.setText("Jumlah "+trayekArrayList.get(listPosition).getTblKeberangkatan().getRefLambung().getRefKelaArmada().getJumlahSeat()+ " kursi");
        if(trayekArrayList.get(listPosition).getStatus().equalsIgnoreCase("naik")){
            imgNaik.setVisibility(View.VISIBLE);
        }else {
            imgNaik.setVisibility(View.GONE);
        }
        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SeatAct.class);
                i.putExtra("keberankatanId", trayekArrayList.get(listPosition).getTblKeberangkatan().getId());
                i.putExtra("asalAgenId", trayekArrayList.get(listPosition).getTblTrayek().getAsal().getId());
                i.putExtra("tujuanAgenId", trayekArrayList.get(listPosition).getTblTrayek().getTujuan().getId());
                i.putExtra("jumlahPenumpang", jumlahKursi);
                i.putExtra("jumlahKursiString", jumlahKursiString);
                i.putExtra("rupiah", rupiah);
                i.putExtra("rupiahNoFormat", rupiahNoFormat);
                i.putExtra("hargaTiket", trayekArrayList.get(listPosition).getHargaTrayek());
                i.putExtra("perubahanHarga", trayekArrayList.get(listPosition).getPerubahanHarga());
                i.putExtra("jadwalHarga", trayekArrayList.get(listPosition).getJadwalHarga());
                i.putExtra("hargaAwal", trayekArrayList.get(listPosition).getHargaAwal());
                i.putExtra("dari", trayekArrayList.get(listPosition).getTblTrayek().getAsal().getNamaAgen());
                i.putExtra("dari2", trayekArrayList.get(listPosition).getTblTrayek().getAsal().getKodeAgn());
                i.putExtra("ke", trayekArrayList.get(listPosition).getTblTrayek().getTujuan().getNamaAgen());
                i.putExtra("ke2", trayekArrayList.get(listPosition).getTblTrayek().getTujuan().getKodeAgn());
                i.putExtra("kursi", trayekArrayList.get(listPosition).getTblKeberangkatan().getRefLambung().getRefKelaArmada().getJumlahSeat());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trayekArrayList.size();
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}