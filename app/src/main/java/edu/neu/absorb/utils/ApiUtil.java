package edu.neu.absorb.utils;

import java.util.Map;

import cn.hutool.json.JSONUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public enum ApiUtil {
    LOGIN_API(ApiUtil.SERVER_ADDRESS + "/user/login", "POST"),
    USER_INFO_API(ApiUtil.SERVER_ADDRESS + "/user/info", "GET"),
    LEADERBOARD_INFO_API(ApiUtil.SERVER_ADDRESS + "/focus/leaderboard", "GET"),
    ADD_FOCUS_RECORD_API(ApiUtil.SERVER_ADDRESS + "/focus/add", "POST"),
    GET_HISTORY_LIST_API(ApiUtil.SERVER_ADDRESS + "/focus/list", "GET"),
    REGISTER_API(ApiUtil.SERVER_ADDRESS + "/user/create", "POST");


    private String url;
    private String type;

    // ip + port
    public static final String SERVER_ADDRESS = "http://164.90.158.28:9922";

    // JSON media type
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static OkHttpClient client = new OkHttpClient();


    ApiUtil(String url, String type) {
        this.url = url;
        this.type = type;
    }

    /**
     * build request of a specific api
     *
     * @param api           target api
     * @param pathVariables path variables
     * @param body          post body
     * @return api url that was built
     */
    public static Request buildRequest(ApiUtil api, Map<String, String> pathVariables, Map<String, Object> body) {
        // final url
        StringBuilder url = new StringBuilder();
        url.append(api.url);
        // check if there are path variables
        if (pathVariables != null) {
            url.append("?");
            // append all path variables
            for (String key : pathVariables.keySet()) {
                url.append(key).append("=").append(pathVariables.get(key)).append("&");
                if (api.type.equals("POST")) url.deleteCharAt(url.length() - 1);
            }
        }
        // request builder
        Request.Builder builder = api.type.equals("POST") ? new Request.Builder()
                .url(url.toString()) : new Request.Builder()
                .url(url.toString().substring(0, url.length() - 1));
        // check request type
        if (api.type.equals("POST")) {
            // if it's post, build json body
            // parse parameter to string
            String jsonStr = JSONUtil.toJsonStr(body);
            // create request body
            RequestBody requestBody = RequestBody.create(jsonStr, JSON);
            builder.post(requestBody);
        }
        // build
        return builder.build();
    }
}
