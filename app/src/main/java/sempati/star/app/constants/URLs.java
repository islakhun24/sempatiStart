package sempati.star.app.constants;

public class URLs {
    private static final String ROOT_URL                    = "http://192.168.73.134:8080";
//    private static final String ROOT_URL = "https://sempati-api.herokuapp.com";

    private static final String API                         = ROOT_URL+ "/api";
    private static final String AUTH                        = API +"/auth";
    private static final String MANIFEST                    = API + "/manifest";
    private static final String PENJUALAN                   = API +"/penjualan";
    private static final String TIKET                       = API +"/tiket";
    private static final String PENUMPANG                   = API + "/penumpang";
    //AUT

    public static final String SELECT_AGEN_BY_ID_TIKET      = TIKET+ "/select_agen_by_id";  // => ASAL KEBERANGKATAN
    public static final String LOGIN                        = AUTH + "/login"; // => LOGIN
    public static final String AKTIVASI                     = AUTH + "/aktivasi"; // => AKTIVASI
    public static final String SELECT_KEBERANGKATAN_TIKET   = TIKET+ "/select_keberangkatan";
    public static final String SELECT_AGEN_TUJUAN_NOT_ID_TIKET = TIKET+ "/select_agen_tujuan_not_id";
    public static final String DELETE_BATAL_PENJUALAN_TIKET = TIKET+ "/delete_batal_penjualan";
    public static final String SELECT_VIEW_KURSI_TIKET      = TIKET+ "/select_view_kursi";
    public static final String BOOKING_SEAT                 = TIKET+ "/booking_seat";
    public static final String BUY_TICKET_DETAIL            = TIKET+ "/buy_ticket_detail";
    public static final String POST_DATA_PENUMPANG          = TIKET+ "/post_data_penumpang";
    public static final String BOOKING_DATA_PENUMPANG       = TIKET+ "/booking_data_penumpang";
    public static final String DETAIL_MULTI_TIKET           = TIKET + "/detail_multi_tiket";
    //MANIFEST
    public static final String SELECT_MANIFEST              = MANIFEST + "/select_manifest";
    public static final String SELECT_KEBERANGKATAN_BY_ID   = MANIFEST + "/select_keberangkatan_by_id";
    public static final String GET_PENUMPANG_BY_ID          = MANIFEST + "/get_penumpang_by_id";
    //PENJUALAN
    public static final String SELECT_MANIFEST_PENJUALAN    = PENJUALAN + "/select_manifest";
    public static final String SELECT_KEBERANGKATAN_BY_ID_PENJUALAN = PENJUALAN + "/select_keberangkatan_by_id";
    public static final String GET_PENUMPANG_BY_ID_PENJUALAN = PENJUALAN + "/get_penumpang_by_id";
    //PENUMPANG
    public static final String SELECT_DATA_PENUMPANG        = PENUMPANG +"/select_data";
    public static final String SELECT_DETAIL_INFO_PENUMPANG = PENUMPANG +"/select_detail_info";
    public static final String SELECT_DETAIL_AGEN_BY_ID_PENUMPANG = PENUMPANG +"/select_detail_agen_by_id";
    public static final String SELECT_SUM_KURSI_PENUMPANG   = PENUMPANG +"/select_sum_kursi";
    public static final String GET_AGEN_BYID_PENUMPANG      = PENUMPANG +"/get_agen_byid";
    public static final String GET_USER_BYID_PENUMPANG      = PENUMPANG +"/get_user_byid";
    public static final String GET_PEGAWAI_PENUMPANG        = PENUMPANG +"/get_pegawai";
    public static final String GET_AKOMODASI_BYID_PENUMPANG = PENUMPANG +"/get_akomodasi_byid";
    public static final String SELECT_DETAIL_AGEN_BY_ID_CETAK_PENUMPANG = PENUMPANG +"/select_detail_agen_by_id_cetak";
    public static final String SELECT_LIST_AGEN_PENUMPANG = PENUMPANG +"/select_list_agen";
    public static final String SELECT_KEBERANGKATAN_BY_ID_PENUMPANG = PENUMPANG +"/select_keberangkatan_by_id";
    public static final String SELECT_ARMADA_ID_PENUMPANG = PENUMPANG +"/select_armada_id";
    public static final String SELECT_PEJUALANN_BY_IDKEBERANGKATAN_PENUMPANG = PENUMPANG +"/select_Pejualann_by_IdKeberangkatan";
    public static final String SELECT_VIEW_KURSI_PENUMPANG = PENUMPANG +"/select_view_kursi";
    //TIKET
    public static final String SELECT_PEJUALANN_BY_IDKEBERANGKATAN_TIKET = TIKET+ "/select_Pejualann_by_IdKeberangkatan";
    public static final String SELECT_KEBERANGKATAN_BY_ID_TIKET = TIKET+ "/select_keberangkatan_by_id";
    public static final String SELECT_DATAINFO_BY_ID_TIKET = TIKET+ "/select_datainfo_by_id";
    public static final String SELECT_RUTE_TRAYEK_ID_TIKET = TIKET+ "/select_rute_trayek_id";
    public static final String GET_SETTING_APP_ID_TIKET = TIKET+ "/get_setting_app_id";
    public static final String SELECT_PENUMPANG_BY_KTP_TIKET = TIKET+ "/select_penumpang_by_ktp";
    public static final String SELECT_HARGA_BY_TGL_TIKET = TIKET+ "/select_harga_by_tgl";
    public static final String SELECT_PENUMPANG_BY_ID_TIKET = TIKET+ "/select_penumpang_by_id";
    public static final String SELECT_PENJUALAN_BY_ID_TIKET = TIKET+ "/select_penjualan_by_id";
    public static final String SUM_PENJUALAN_BY_ID_TIKET = TIKET+ "/sum_penjualan_by_id";
    public static final String GET_TEMP_ORDER_BY_ID_TIKET = TIKET+ "/get_temp_order_by_id";
    public static final String GET_STATUS_ANDROID_TIKET = TIKET+ "/get_status_android";
    public static final String GET_KODE_REG_TIKET       = TIKET+ "/get_kode_reg";
    public static final String SELECT_TEMP_ORDER_BY_ID_TIKET = TIKET+ "/select_temp_order_by_id";
    public static final String SELECT_CEK_KURSI_TIKET   = TIKET+ "/select_cek_kursi";
    public static final String GET_INISILA_KURSI_TIKET  = TIKET+ "/get_inisila_kursi";
    public static final String SELECT_SETTING_APP_ID_TIKET = TIKET+ "/select_setting_app_id";
    public static final String SELECT_CEK_KURSI_BY_KURSI_TIKET = TIKET+ "/select_cek_kursi_by_kursi";
    public static final String SELECT_TEMP_BY_ID_TIKET  = TIKET+ "/select_temp_by_id";
    public static final String SELECT_CEK_KURSI_TEMP_TIKET = TIKET+ "/select_cek_kursi_temp";
    public static final String INSERT_TIKET             = TIKET+ "/insert";
    public static final String INSERT_PENUMPANG_TIKET   = TIKET+ "/insert_penumpang";
    public static final String INSERT_TEMP_ORDER_TIKET  = TIKET+ "/insert_temp_order";
    public static final String UPDATE_TIKET             = TIKET+ "/update";
    public static final String UPDATE_PENUMPANG_TIKET   = TIKET+ "/update_penumpang";
    public static final String DELETE_TEMP_ORDER_TIKET  = TIKET+ "/delete_temp_order";
    public static final String SELECT_FORMAT_BY_ID_TIKET= TIKET+ "/select_format_by_id";
    public static final String SHOW_MY_BOOKING_SEAT     = TIKET+ "/show_my_booking_seat";


    //fame
    //transaksi
    public static final String LIST_TRANSAKSI           = TIKET+ "/get_transaksi";
    public static final String LIST_LAPORAN_TRANSAKSI   = TIKET+ "/get_laporan_transaksi";
    public static final String LIST_BOOKING             = TIKET+ "/get_booking";
    public static final String DETAIL_AKUN              = AUTH+ "/user";
}
