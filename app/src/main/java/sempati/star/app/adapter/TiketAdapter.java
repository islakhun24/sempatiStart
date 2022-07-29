package sempati.star.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import sempati.star.app.R;
import sempati.star.app.activity.SeatAct;
import sempati.star.app.models.PembayranDetail;
import sempati.star.app.models.Trayek;
import sempati.star.app.utils.FormatUtils;

public class TiketAdapter extends RecyclerView.Adapter<TiketAdapter.MyViewHolder> {

    private ArrayList<PembayranDetail> tiketArrayList;
    private Context context;
    private int jumlahKursi;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView keberangkatanTujuan, tanggalKeberangkatan, tvBussClass, tvPenumpang, tvNomorKursi, tvTanggal, tvAgen_kantor, tvMetode_keterangan, tvHarga, qrCode;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.keberangkatanTujuan = (TextView) itemView.findViewById(R.id.keberangkatanTujuan);
            this.tanggalKeberangkatan = (TextView) itemView.findViewById(R.id.tanggalKeberangkatan);
            this.tvBussClass = (TextView) itemView.findViewById(R.id.tvBussClass);
            this.tvPenumpang = (TextView) itemView.findViewById(R.id.tvPenumpang);
            this.tvNomorKursi = (TextView) itemView.findViewById(R.id.tvNomorKursi);
            this.imageView = (ImageView) itemView.findViewById(R.id.qr);
            this.tvTanggal = (TextView) itemView.findViewById(R.id.tvTanggal);
            this.tvAgen_kantor = (TextView) itemView.findViewById(R.id.tvAgen);
            this.tvMetode_keterangan = (TextView) itemView.findViewById(R.id.tvMetode);
            this.tvHarga = (TextView) itemView.findViewById(R.id.tvHarga);
            this.qrCode = (TextView) itemView.findViewById(R.id.qrCode);
        }
    }

    public TiketAdapter(ArrayList<PembayranDetail> tikets, Context context) {
        this.tiketArrayList = tikets;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView keberangkatanTujuan, tanggalKeberangkatan, tvBussClass, tvPenumpang, tvNomorKursi, tvTanggal, tvAgen_kantor, tvMetode_keterangan, tvHarga, qrCode;
        ImageView imageView;
        PembayranDetail object = tiketArrayList.get(listPosition);
        FormatUtils formatUtils = new FormatUtils();

        keberangkatanTujuan = holder.keberangkatanTujuan;
        tanggalKeberangkatan = holder.tanggalKeberangkatan;
        tvBussClass = holder.tvBussClass;
        imageView = holder.imageView;
        tvPenumpang = holder.tvPenumpang;
        tvNomorKursi = holder.tvNomorKursi;
        tvTanggal = holder.tvTanggal;
        tvAgen_kantor = holder.tvAgen_kantor;
        tvMetode_keterangan = holder.tvMetode_keterangan;
        tvHarga = holder.tvHarga;
        qrCode = holder.qrCode;

        String rupiah = formatRupiah(Double.valueOf(object.getHarga_tiket()));
        tvHarga.setText(rupiah);
        keberangkatanTujuan.setText(object.getKeberangkatan().getTrayek().getNama_trayek());

//        tanggalKeberangkatan.setText(formatUtils.dateIndonesia(object.getKeberangkatan().getTanggal()));
        tvBussClass.setText(object.getKeberangkatan().getLambung().getKelas_armada().getNama_kelas());
        tvPenumpang.setText(object.getPenumpang_umum());
        tvNomorKursi.setText(object.getNo_kursi());
//        tvTanggal.setText(formatUtils.dateIndonesia(object.getCr_datetime()));
        tvAgen_kantor.setText("Kantor");
        tvMetode_keterangan.setText(object.getKet());
        qrCode.setText(object.getQrcode());
        Glide.with(context).load("https://chart.googleapis.com/chart?chs=250x250&cht=qr&chl="+object.getQrcode()).into(imageView);

    }

    @Override
    public int getItemCount() {
        return tiketArrayList.size();
    }

    private String formatRupiah(Double number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }


}
