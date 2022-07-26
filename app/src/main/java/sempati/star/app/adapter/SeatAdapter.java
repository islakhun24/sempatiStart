package sempati.star.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import sempati.star.app.R;
import sempati.star.app.activity.SeatAct;
import sempati.star.app.models.Kursi;
import sempati.star.app.models.TempSeat;
import sempati.star.app.models.Trayek;
import sempati.star.app.services.SharedPrefManager;
import sempati.star.app.services.TempSeatPrefManager;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.MyViewHolder>{
    private TempSeatPrefManager tempSeatPrefManager;
    private ArrayList<Kursi.Seat> seatArrayList;
    private ArrayList<TempSeat> arrayList = new ArrayList<>();
    private SharedPrefManager sharedPrefManager;
    private String keberangkatanId;
    private Context context;
    OnShareClickedListener mCallback;
    public void setOnShareClickedListener(OnShareClickedListener mCallback) {
        this.mCallback = mCallback;
    }
    public interface OnShareClickedListener {
        public void ShareClicked(String noKursi, String namaKursi);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout kolom1, kolom2, kolom3, kolom4, kolom5, pintu, deck;
        LinearLayout emptykolom1, emptykolom2, emptykolom3, emptykolom4, emptykolom5;
        TextView no_kursi1,no_kursi2,no_kursi3,no_kursi4,no_kursi5;
        public MyViewHolder(View itemView) {
            super(itemView);

            this.kolom1 = (LinearLayout) itemView.findViewById(R.id.kolom1);
            this.kolom2 = (LinearLayout) itemView.findViewById(R.id.kolom2);
            this.kolom3 = (LinearLayout) itemView.findViewById(R.id.kolom3);
            this.kolom4 = (LinearLayout) itemView.findViewById(R.id.kolom4);
            this.kolom5 = (LinearLayout) itemView.findViewById(R.id.kolom5);
            this.pintu = (LinearLayout) itemView.findViewById(R.id.pintu);
            this.deck = (LinearLayout) itemView.findViewById(R.id.deck);
            this.no_kursi1 = (TextView) itemView.findViewById(R.id.no_kursi1);
            this.no_kursi2 = (TextView) itemView.findViewById(R.id.no_kursi2);
            this.no_kursi3 = (TextView) itemView.findViewById(R.id.no_kursi3);
            this.no_kursi4 = (TextView) itemView.findViewById(R.id.no_kursi4);
            this.no_kursi5 = (TextView) itemView.findViewById(R.id.no_kursi5);
            this.emptykolom1 = (LinearLayout) itemView.findViewById(R.id.emptykolom1);
            this.emptykolom2 = (LinearLayout) itemView.findViewById(R.id.emptykolom2);
            this.emptykolom3 = (LinearLayout) itemView.findViewById(R.id.emptykolom3);
            this.emptykolom4 = (LinearLayout) itemView.findViewById(R.id.emptykolom4);
            this.emptykolom5 = (LinearLayout) itemView.findViewById(R.id.emptykolom5);
        }
    }

    public SeatAdapter(ArrayList<Kursi.Seat> seatArrayList, Context context, String keberangkatanId, OnShareClickedListener mCallback) {
        this.seatArrayList = seatArrayList;
        this.context = context;
        this.keberangkatanId = keberangkatanId;
        this.mCallback = mCallback;
    }

    @Override
    public SeatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_seat, parent, false);


        SeatAdapter.MyViewHolder myViewHolder = new SeatAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final SeatAdapter.MyViewHolder holder, final int listPosition) {
        Kursi.Seat seat = seatArrayList.get(listPosition);
        tempSeatPrefManager = new TempSeatPrefManager(context);
        TempSeat temp = new TempSeat();
        arrayList = tempSeatPrefManager.getSeat();
//        for (int i = 0; i <arrayList.size(); i++){
//            TempSeat tempSeat = arrayList.get(i);
//            if(
//                tempSeat.getBaris().equalsIgnoreCase(String.valueOf(seat.getBaris())) &&
//                tempSeat.getKeberangkatanId().equalsIgnoreCase(String.valueOf(keberangkatanId))){
//                if(tempSeat.getNoKursi().equalsIgnoreCase(seat.getIsKolumn1().getStatus())){
//                    holder.kolom1.setBackgroundResource(R.drawable.seat_green);
//                }else if(tempSeat.getNoKursi().equalsIgnoreCase(seat.getIsKolumn2().getStatus())){
//                    holder.kolom2.setBackgroundResource(R.drawable.seat_green);
//                }else if(tempSeat.getNoKursi().equalsIgnoreCase(seat.getIsKolumn3().getStatus())){
//                    holder.kolom3.setBackgroundResource(R.drawable.seat_green);
//                }else if(tempSeat.getNoKursi().equalsIgnoreCase(seat.getIsKolumn4().getStatus())){
//                    holder.kolom4.setBackgroundResource(R.drawable.seat_green);
//                }else if(tempSeat.getNoKursi().equalsIgnoreCase(seat.getIsKolumn5().getStatus())){
//                    holder.kolom5.setBackgroundResource(R.drawable.seat_green);
//                }
//            }
//        }
        holder.no_kursi1.setText(seat.getKolom1());
        holder.no_kursi2.setText(seat.getKolom2());
        holder.no_kursi3.setText(seat.getKolom3());
        holder.no_kursi4.setText(seat.getKolom4());
        holder.no_kursi5.setText(seat.getKolom5());

        if(seat.getIsKolumn1().getStatus().equalsIgnoreCase("lunas")){
            holder.kolom1.setBackgroundResource(R.drawable.seat_merah);
            holder.kolom1.setEnabled(false);
        }else if(seat.getIsKolumn1().getStatus().equalsIgnoreCase("booking")&& seat.getIsKolumn1().isMe() == false) {
            holder.kolom1.setBackgroundResource(R.drawable.seat_orange);
            holder.kolom1.setEnabled(false);
        }else if(seat.getIsKolumn1().getStatus().equalsIgnoreCase("booking") && seat.getIsKolumn1().isMe() == true) {
            holder.kolom1.setBackgroundResource(R.drawable.seat_green);
            holder.kolom1.setEnabled(false);
        }else{
            holder.kolom1.setBackgroundResource(R.drawable.seat_white);
            holder.kolom1.setEnabled(true);
        }

        if(seat.getIsKolumn2().getStatus().equalsIgnoreCase("lunas")){
            holder.kolom2.setBackgroundResource(R.drawable.seat_merah);
            holder.kolom2.setEnabled(false);
        }else if(seat.getIsKolumn2().getStatus().equalsIgnoreCase("booking")&& seat.getIsKolumn2().isMe() == false) {
            holder.kolom2.setBackgroundResource(R.drawable.seat_orange);
            holder.kolom2.setEnabled(false);
        }else if(seat.getIsKolumn2().getStatus().equalsIgnoreCase("booking") && seat.getIsKolumn2().isMe() == true) {
            holder.kolom2.setBackgroundResource(R.drawable.seat_green);
            holder.kolom2.setEnabled(false);
        }else{
            holder.kolom2.setBackgroundResource(R.drawable.seat_white);
            holder.kolom2.setEnabled(true);
        }
        if(seat.getIsKolumn3().getStatus().equalsIgnoreCase("lunas")){
            holder.kolom3.setBackgroundResource(R.drawable.seat_merah);
            holder.kolom3.setEnabled(false);
        }else if(seat.getIsKolumn3().getStatus().equalsIgnoreCase("booking")&& seat.getIsKolumn3().isMe() == false) {
            holder.kolom3.setBackgroundResource(R.drawable.seat_orange);
            holder.kolom3.setEnabled(false);
        }else if(seat.getIsKolumn3().getStatus().equalsIgnoreCase("booking") && seat.getIsKolumn3().isMe() == true) {
            holder.kolom3.setBackgroundResource(R.drawable.seat_green);
            holder.kolom3.setEnabled(false);
        }else{
            holder.kolom3.setBackgroundResource(R.drawable.seat_white);
            holder.kolom3.setEnabled(true);
        }
        if(seat.getIsKolumn4().getStatus().equalsIgnoreCase("lunas")){
            holder.kolom4.setBackgroundResource(R.drawable.seat_merah);
            holder.kolom4.setEnabled(false);
        }else if(seat.getIsKolumn4().getStatus().equalsIgnoreCase("booking")&& seat.getIsKolumn4().isMe() == false) {
            holder.kolom4.setBackgroundResource(R.drawable.seat_orange);
            holder.kolom4.setEnabled(false);
        }else if(seat.getIsKolumn4().getStatus().equalsIgnoreCase("booking") && seat.getIsKolumn4().isMe() == true) {
            holder.kolom4.setBackgroundResource(R.drawable.seat_green);
            holder.kolom4.setEnabled(false);
        }else{
            holder.kolom4.setBackgroundResource(R.drawable.seat_white);
            holder.kolom4.setEnabled(true);
        }

        if(seat.getIsKolumn4().getStatus().equalsIgnoreCase("lunas")){
            holder.kolom5.setBackgroundResource(R.drawable.seat_merah);
            holder.kolom5.setEnabled(false);
        }else if(seat.getIsKolumn5().getStatus().equalsIgnoreCase("booking" )&& seat.getIsKolumn5().isMe() == false) {
            holder.kolom5.setBackgroundResource(R.drawable.seat_orange);
            holder.kolom5.setEnabled(false);
        }else if(seat.getIsKolumn5().getStatus().equalsIgnoreCase("booking") && seat.getIsKolumn5().isMe() == true) {
            holder.kolom5.setBackgroundResource(R.drawable.seat_green);
            holder.kolom5.setEnabled(false);
        }else{
            holder.kolom5.setBackgroundResource(R.drawable.seat_white);
            holder.kolom5.setEnabled(true);
        }
        if (seat.getKolom1().equalsIgnoreCase("D")){
            holder.deck.setVisibility(View.VISIBLE);
            holder.kolom1.setVisibility(View.GONE);
            holder.kolom2.setVisibility(View.GONE);
            holder.kolom3.setVisibility(View.GONE);
            holder.kolom4.setVisibility(View.GONE);
            holder.kolom5.setVisibility(View.GONE);
        }else{
            holder.deck.setVisibility(View.GONE);
            if(seat.getKolom1().equalsIgnoreCase("G") && seat.getKolom2().equalsIgnoreCase("P")){
                holder.pintu.setVisibility(View.VISIBLE);
                holder.kolom1.setVisibility(View.GONE);
                holder.kolom2.setVisibility(View.GONE);

                if(seat.getKolom3().equalsIgnoreCase("J")){
                    holder.kolom3.setVisibility(View.GONE);
                    holder.emptykolom3.setVisibility(View.VISIBLE);
                }else if( seat.getKolom3().equalsIgnoreCase("-")){
                    holder.kolom3.setVisibility(View.GONE);
                    holder.emptykolom3.setVisibility(View.VISIBLE);
                }else {
                    holder.kolom3.setVisibility(View.VISIBLE);
                    holder.emptykolom3.setVisibility(View.GONE);
                }

                if(seat.getKolom4().equalsIgnoreCase("J")){
                    holder.kolom4.setVisibility(View.GONE);
                    holder.emptykolom4.setVisibility(View.VISIBLE);
                }else if(seat.getKolom4().equalsIgnoreCase("-")){
                    holder.kolom4.setVisibility(View.GONE);
                    holder.emptykolom4.setVisibility(View.VISIBLE);
                }else {
                    holder.kolom4.setVisibility(View.VISIBLE);
                    holder.emptykolom4.setVisibility(View.GONE);
                }

                if(seat.getKolom5().equalsIgnoreCase("J")){
                    holder.kolom5.setVisibility(View.GONE);
                    holder.emptykolom5.setVisibility(View.VISIBLE);
                } else if(seat.getKolom5().equalsIgnoreCase("-")) {
                    holder.kolom5.setVisibility(View.GONE);
                    holder.emptykolom5.setVisibility(View.VISIBLE);
                }else {
                    holder.kolom5.setVisibility(View.VISIBLE);
                    holder.emptykolom5.setVisibility(View.GONE);
                }

            }else {
                holder.pintu.setVisibility(View.GONE);
                if(seat.getKolom1().equalsIgnoreCase("J")){
                    holder.kolom1.setVisibility(View.GONE);
                    holder.emptykolom1.setVisibility(View.VISIBLE);
                }else if (seat.getKolom1().equalsIgnoreCase("-")) {
                    holder.kolom1.setVisibility(View.GONE);
                    holder.emptykolom1.setVisibility(View.VISIBLE);
                }else {
                    holder.kolom1.setVisibility(View.VISIBLE);
                    holder.emptykolom1.setVisibility(View.GONE);
                }

                if(seat.getKolom2().equalsIgnoreCase("J")){
                    holder.kolom2.setVisibility(View.GONE);
                    holder.emptykolom2.setVisibility(View.VISIBLE);
                }else if(seat.getKolom2().equalsIgnoreCase("-")){
                    holder.kolom2.setVisibility(View.GONE);
                    holder.emptykolom2.setVisibility(View.VISIBLE);
                }else {
                    holder.kolom2.setVisibility(View.VISIBLE);
                    holder.emptykolom2.setVisibility(View.GONE);
                }

                if(seat.getKolom3().equalsIgnoreCase("J")){
                    holder.kolom3.setVisibility(View.GONE);
                    holder.emptykolom3.setVisibility(View.VISIBLE);
                }else if( seat.getKolom3().equalsIgnoreCase("-")){
                    holder.kolom3.setVisibility(View.GONE);
                    holder.emptykolom3.setVisibility(View.VISIBLE);
                }else {
                    holder.kolom3.setVisibility(View.VISIBLE);
                    holder.emptykolom3.setVisibility(View.GONE);
                }

                if(seat.getKolom4().equalsIgnoreCase("J")){
                    holder.kolom4.setVisibility(View.GONE);
                    holder.emptykolom4.setVisibility(View.VISIBLE);
                }else if(seat.getKolom4().equalsIgnoreCase("-")){
                    holder.kolom4.setVisibility(View.GONE);
                    holder.emptykolom4.setVisibility(View.VISIBLE);
                }else {
                    holder.kolom4.setVisibility(View.VISIBLE);
                    holder.emptykolom4.setVisibility(View.GONE);
                }

                if(seat.getKolom5().equalsIgnoreCase("J")){
                    holder.kolom5.setVisibility(View.GONE);
                    holder.emptykolom5.setVisibility(View.VISIBLE);
                } else if(seat.getKolom5().equalsIgnoreCase("-")) {
                    holder.kolom5.setVisibility(View.GONE);
                    holder.emptykolom5.setVisibility(View.VISIBLE);
                }else {
                    holder.kolom5.setVisibility(View.VISIBLE);
                    holder.emptykolom5.setVisibility(View.GONE);
                }
                sharedPrefManager = new SharedPrefManager(context);
                holder.kolom1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.ShareClicked(String.valueOf(seat.getKolom1()), String.valueOf(seat.getNamaKursi3()));

                    }
                });
                holder.kolom2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.ShareClicked(String.valueOf(seat.getKolom2()), String.valueOf(seat.getNamaKursi3()));

                    }
                });
                holder.kolom3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.ShareClicked(String.valueOf(seat.getKolom3()), String.valueOf(seat.getNamaKursi3()));
                    }
                });
                holder.kolom4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.ShareClicked(String.valueOf(seat.getKolom4()), String.valueOf(seat.getNamaKursi3()));

                    }
                });
                holder.kolom5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.ShareClicked(String.valueOf(seat.getKolom5()), String.valueOf(seat.getNamaKursi3()));

                    }
                });

            }
        }

//        TextView tvNama = holder.tvNama;

//        tvNama.setText(trayekArrayList.get(listPosition).getTblTrayek().getNamaTrayek());
    }

    @Override
    public int getItemCount() {
        return seatArrayList.size();
    }

    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }
}