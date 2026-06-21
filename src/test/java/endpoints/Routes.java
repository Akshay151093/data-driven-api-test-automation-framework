package endpoints;

import reader.PropertyFileReader;

public class Routes {

    public static String BASE_URL = PropertyFileReader.getInstance().getBaseUrl();

    public static String POST_URL = BASE_URL + "/user";
    public static String GET_URL = BASE_URL + "/user/{username}";
    public static String PUT_URL = BASE_URL + "/user/{username}";
    public static String DELETE_URL = BASE_URL + "/user/{username}";
}
