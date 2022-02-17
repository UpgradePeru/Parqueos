package pe.upgrade.parqueo.model.api;

import org.json.JSONException;
import org.json.JSONObject;

public class Resp {

    public static Resp getResp(JSONObject jsonObject) throws JSONException {
        int code = jsonObject.getInt("code");
        String status = jsonObject.getString("status");
        String msg = jsonObject.getString("msg");

        Resp resp = new Resp(code, status, msg);

        return resp;
    }

    private int code;
    private String status;
    private String msg;

    public Resp(int code, String status, String msg) {
        this.code = code;
        this.status = status;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean equals(Object obj) {
        boolean result = false;

        if (obj != null && obj instanceof Resp) {
            Resp that = (Resp) obj;
            if (this.code == that.code) {
                result = true;
            }
        }
        return result;
    }

    public String toString() {
        return this.code + "(" + this.msg + ")";
    }

}
