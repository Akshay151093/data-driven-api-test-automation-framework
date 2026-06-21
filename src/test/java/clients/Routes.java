package clients;

import config.ConfigManager;

public class Routes {

    public static final String BASE_URL = ConfigManager.getInstance().getBaseUrl();

    public static final String POST_URL = BASE_URL + "/user";
    public static final String GET_URL = BASE_URL + "/user/{username}";
    public static final String PUT_URL = BASE_URL + "/user/{username}";
    public static final String DELETE_URL = BASE_URL + "/user/{username}";
}
