package com.supernova.selleradmin.util;

public interface Constants {

    String PREF_NAME = "userdata_200_504";
    String PREF_FIRST_INSTALL = "first_install";
    String PREF_LOGGED_IN = "logged_in";

    String BASE_URL = "https://codenova.xyz/supernova-mini-user805/api/";

    String USER_EMAIL = "user_email";
    String USER_TOKEN = "user_token";

    String STATUS_SUCCESS = "success";
    String FIRST_OFFER_LOAD = "first_offer_load";

    String SHARED_PREFS = "user_data";

    int NOTIFICATION_ID_DEFAULT = 1;
    String NOTIFICATION_CHANNEL_ID = "Seller Admin";

    int SMS_REQUEST_CODE = 100;
    int ACCESSIBILITY_ENABLED = 1;

    String DATABASE_NAME = "pending_database";
    int DATABASE_VERSION = 1;


    String TABLE_PENDING = "pending";

    String TABLE_USER = "users";
    String TABLE_OFFER = "offers";
    String TABLE_TRANSACTION = "transactions";
    String ID = "id";
    String PHONE_NUMBER = "phone_number";
    String PLAYER_ID = "player_id";
    String USER_ID = "user_id";
    String IS_ACTIVATED = "is_activated";
    String USER_NAME = "user_name";
    String USER_CHIPS = "user_chips";
    String TOTAL_BUY = "total_buy";
    String OFFER_CHIPS = "offer_chips";
    String OFFER_PRICE = "offer_price";
    String IS_ACTIVE = "is_active";
    String OFFER_CREATED = "offer_created";

    String BKASH_AGENT = "bkash_agent";
    String BKASH_PAYMENT = "bkash_payment";
    String NOGOD_AGENT = "nogod_agent";
    String NOGOD_PAYMENT = "nogod_payment";
    String ROCKET = "rocket";

    String All_USER_COUNT = "all_users";
    String TOTAL_CHIP_SALE = "total_chip_sale";
    String TOTAL_AMOUNT = "total_amount";
    String ACTIVE_USERS = "active_users";
    String TRX_ID = "trx_id";
    String TRX_TIME = "trx_time";
    String TRX_AMOUNT = "trx_amount";
    String OFFER_TITLE = "offer_title";
    String OFFER_CONTENT = "offer_content";
    String CHIPS_PRICE = "chips_price";
    String CURRENT_BALANCE = "balance";

    int SETTINGS_HEADER = 1;
    int SETTINGS_BODY = 2;

    int TRANSACTION_FRAGMENT = 1;
    int PENDING_FRAGMENT = 2;
    int SETTINGS_FRAGMENT = 3;

    int DRAWABLE_RIGHT = 2;

    int PHONE_NUMBER_LENGTH = 11;
    int PASSWORD_LENGTH = 40;
    int AMOUNT_LENGTH = 5;
}
