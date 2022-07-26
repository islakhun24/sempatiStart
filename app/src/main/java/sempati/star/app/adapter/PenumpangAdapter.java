package sempati.star.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import sempati.star.app.R;
import sempati.star.app.activity.SeatAct;
import sempati.star.app.models.PembayranDetail;
import sempati.star.app.models.Trayek;

public class PenumpangAdapter extends RecyclerView.Adapter<PenumpangAdapter.MyViewHolder> {
    String statusUser;
    private ArrayList<PembayranDetail> pembayranDetails;
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView noKursi;
        LinearLayout lnPrice;
        EditText nohpPenumpang, namaPenumpang, potonganHarga;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.nohpPenumpang = (EditText) itemView.findViewById(R.id.nohpPenumpang);
            this.namaPenumpang = (EditText) itemView.findViewById(R.id.namaPenumpang);
            this.potonganHarga = (EditText) itemView.findViewById(R.id.potonganHarga);
            this.noKursi = (TextView) itemView.findViewById(R.id.no_kursi);
            this.lnPrice = (LinearLayout) itemView.findViewById(R.id.lnPrice);


        }
    }

    public PenumpangAdapter(ArrayList<PembayranDetail> pembayranDetails, Context context, String statusUser) {
        this.pembayranDetails = pembayranDetails;
        this.context = context;
        this.statusUser = statusUser;
    }

    @Override
    public PenumpangAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_penumpang, parent, false);


        PenumpangAdapter.MyViewHolder myViewHolder = new PenumpangAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final PenumpangAdapter.MyViewHolder holder, final int listPosition) {
        TextView noKursi = holder.noKursi;
        LinearLayout lnPrice = holder.lnPrice;
        EditText nohpPenumpang = holder.nohpPenumpang;
        EditText namaPenumpang = holder.namaPenumpang;
        EditText potonganHarga = holder.potonganHarga;


        if(statusUser.equalsIgnoreCase("1")){
            lnPrice.setVisibility(View.VISIBLE);
        }else {
            lnPrice.setVisibility(View.GONE);
        }
        noKursi.setText(pembayranDetails.get(listPosition).getNo_kursi());

        nohpPenumpang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                pembayranDetails.get(listPosition).setPenumpang_hp(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        namaPenumpang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                pembayranDetails.get(listPosition).setPenumpang_umum(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        potonganHarga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                pembayranDetails.get(listPosition).setHarga_potongan(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return pembayranDetails.size();
    }
}