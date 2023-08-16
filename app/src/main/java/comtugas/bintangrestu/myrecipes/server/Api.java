package comtugas.bintangrestu.myrecipes.server;

public class Api {

    //Alamat situs web API yang diperlukan untuk proses data.
    private static final String ROOT_URL = "https://myrecpiesbind1608.000webhostapp.com/endpoint/v1/Api.php?apicall=";
    //Alamat situs web API yang diperlukan untuk Resep makanan.
    private static final String FOOD_URL = "https://myrecpiesbind1608.000webhostapp.com/endpoint/v1/ApiFood2.php?apicall=";
    //Alamat situs web API yang diperlukan untuk login pengguna.
    private static final String LOGIN_URL = "https://myrecpiesbind1608.000webhostapp.com/endpoint/v1/ApiLogin.php?apicall=";
    //Alamat situs web API yang diperlukan untuk Resep makanan yang difavorit oleh pengguna.
    private static final String FAVORITE_URL = "https://myrecpiesbind1608.000webhostapp.com/endpoint/v1/ApiFavorite.php?apicall=";

    //Parameter untuk setiap proses CRUD data pengguna.
    public static final String URL_CREATE_USER = ROOT_URL + "createuser";
    public static final String URL_READ_FOODS = FOOD_URL + "getfoods";

    public static final String URL_SELECT_FOODS = FOOD_URL + "selectfood";

    public static final String URL_SELECT_FAVORITE = FAVORITE_URL + "selectfavorite&idPengguna=";

    public static final String URL_SELECT_ONE_FAVORITE = FAVORITE_URL + "caseonefavorite=";

    public static final String URL_CREATE_FAVORITE = FAVORITE_URL + "createfavorite";
    public static final String URL_PROFILE_USERS = ROOT_URL + "caseprofile";
    public static final String URL_UPDATE_USER = ROOT_URL + "updateuser";
    public static final String URL_DELETE_FAVORITE = FAVORITE_URL + "deletefavorite&idFavorite=";

    public static final String URL_LOGIN_USER = LOGIN_URL + "loginuser";




}
