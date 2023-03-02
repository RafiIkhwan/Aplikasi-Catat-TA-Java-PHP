package com.rafiikhwan.catatta.Config;

public class APIConfig {

//    public static final String IPHP             = "192.168.43.35";
    public static final String IPHP             =  "172.16.100.3";
//    public static final String IPHP             = "192.168.151.35";
//    public static final String IPHP             = "192.168.138.35";
    public static final String Folder           = "finaltask";
    public static final String Port             = ":8080";
    public static final String URL_GET_DATAS    = "http://"+IPHP+Port+"/"+Folder+"/data/ShowData.php";
    public static final String URL_ADD_DATA     = "http://"+IPHP+Port+"/"+Folder+"/data/action/AddData.php";
    public static final String URL_GET_DATA     = "http://"+IPHP+Port+"/"+Folder+"/data/action/GetData.php?id=";
    public static final String URL_UPDATE_DATA  = "http://"+IPHP+Port+"/"+Folder+"/data/action/UpdateData.php";
    public static final String URL_DELETE_DATA  = "http://"+IPHP+Port+"/"+Folder+"/data/action/DeleteData.php?id=";
    public static final String URL_SEARCH_DATA  = "http://"+IPHP+Port+"/"+Folder+"/data/action/SearchData.php?search=";

    public static final String URL_GET_USERS    = "http://"+IPHP+Port+"/"+Folder+"/admin/ShowData.php";
    public static final String URL_ADD_USER     = "http://"+IPHP+Port+"/"+Folder+"/admin/action/AddData.php";
    public static final String URL_CHECK_USER   = "http://"+IPHP+Port+"/"+Folder+"/admin/action/CheckData.php";
    public static final String URL_GET_USER     = "http://"+IPHP+Port+"/"+Folder+"/admin/action/GetData.php?iduser=";
    public static final String URL_UPDATE_USER  = "http://"+IPHP+Port+"/"+Folder+"/admin/action/UpdateData.php";
    public static final String URL_DELETE_USER  = "http://"+IPHP+Port+"/"+Folder+"/admin/action/DeleteData.php?iduser=";

    public static final String KEY_ID           =   "id";
    public static final String KEY_NO_INDUK     =   "no_induk";
    public static final String KEY_JUDUL        =   "judul";
    public static final String KEY_PEMILIK      =   "pemilik";
    public static final String KEY_PEMBIMBING   =   "pembimbing";
    public static final String KEY_TEMPAT_PKL   =   "tempat_pkl";
    public static final String KEY_ANGKATAN     =   "angkatan";

    public static final String KEY_ID_USER      =   "iduser";
    public static final String KEY_USERNAME     =   "username";
    public static final String KEY_PASSWORD     =   "password";
    public static final String KEY_DATE         =   "date";

    public static final String TAG_JSON_ARRAY   =   "result_data";
    public static final String TAG_ID           =   "id";
    public static final String TAG_NO_INDUK     =   "no_induk";
    public static final String TAG_JUDUL        =   "judul";
    public static final String TAG_PEMILIK      =   "pemilik";
    public static final String TAG_PEMBIMBING   =   "pembimbing";
    public static final String TAG_TEMPAT_PKL   =   "tempat_pkl";
    public static final String TAG_ANGKATAN     =   "angkatan";

    public static final String TAG_JSON_USERS   =   "users_data";
    public static final String TAG_ID_USER      =   "iduser";
    public static final String TAG_USERNAME     =   "username";
    public static final String TAG_PASSWORD     =   "password";
    public static final String TAG_DATE         =   "date";

}
