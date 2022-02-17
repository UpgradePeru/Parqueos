package pe.upgrade.parqueo.model.api;

import android.app.Application;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.upgrade.parqueo.model.Model;

public class WebAPI implements API {
    public static final String BASE_URL = "https://parqueoya.azurewebsites.net/";

    private final Application mApplication;
    private RequestQueue mRequestQueue;
    private Model mModel;

    public WebAPI(Application application, Model model) {
        mApplication = application;
        mRequestQueue = Volley.newRequestQueue(application);
        mModel = model;
    }

    public void login(String email, String password, final APIListener listener) {
        String url = BASE_URL + "identity/login";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        User user = User.getUser(response);
                        if (listener != null) {
                            listener.onLogin(user);
                        }
                        //Toast.makeText(mApplication, "Welcome " + user.getName(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(mApplication, "JSON Exception" + response.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, "Email y/o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, successListener, errorListener);
            mRequestQueue.add(request);

        }
        catch (JSONException e) {
            Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        }
    }

    public void register(String name, String email, String password, String nroDoc, String celular, String licencia, String placa, final APIListener listener) {
        String url = BASE_URL + "identity/register";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstname", name);
            jsonObject.put("email", email);
            jsonObject.put("password", password);

            jsonObject.put("nroDoc", nroDoc);
            jsonObject.put("celular", celular);
            jsonObject.put("licencia", licencia);
            jsonObject.put("placa", placa);

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Resp resp = Resp.getResp(response);
                        listener.onRegister(resp);
                        //mModel.setUser(user);
                        //Toast.makeText(mApplication, "Welcome " + user.getName(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(mApplication, "Exception" + response.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, "Ocurrió un error en el registro", Toast.LENGTH_SHORT).show();

                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, successListener, errorListener);
            mRequestQueue.add(request);

        }
        catch (JSONException e) {
            Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        }
    }

    @Override
    public void loadDistritos(boolean disponibles, final APIListener listener) {
        String url = BASE_URL + "distritos";

        if (disponibles) {
            url = BASE_URL + "app/distritos";
        }

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Distrito> distritos = Distrito.getDistritos(response);
                    if (listener != null) {
                        listener.onDistritosLoaded(distritos);
                    }
                } catch (JSONException e) {
                    Toast.makeText(mApplication, "JSON Exception", Toast.LENGTH_SHORT).show();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mApplication, "Ocurrió un error al traer distritos", Toast.LENGTH_SHORT).show();

            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null, successListener, errorListener) {
            public Map<String,String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + mModel.getUser().getToken());
                return headers;
            }
        };
        mRequestQueue.add(request);

    }

    @Override
    public void loadCanchas(final APIListener listener) {
        String url = BASE_URL + "parqueos";

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Cancha> canchas = Cancha.getCanchas(response);
                    if (listener != null) {
                        listener.onCanchasLoaded(canchas);
                    }
                } catch (JSONException e) {
                    Toast.makeText(mApplication, "JSON Exception", Toast.LENGTH_SHORT).show();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mApplication, "Ocurrió un error al traer canchas", Toast.LENGTH_SHORT).show();

            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null, successListener, errorListener) {
            public Map<String,String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + mModel.getUser().getToken());
                return headers;
            }
        };
        mRequestQueue.add(request);
    }

    @Override
    public void createCancha(String name, String description, double price, String contact, String address, double latitude, double longitude, int distritoId, boolean enable, final APIListener listener) {
        String url = BASE_URL + "parqueos";
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("name", name);
            jsonRequest.put("description", description);
            jsonRequest.put("price", price);
            jsonRequest.put("contact", contact);
            jsonRequest.put("address", address);
            jsonRequest.put("latitude", latitude);
            jsonRequest.put("longitude", longitude);
            jsonRequest.put("distritoId", distritoId);
            jsonRequest.put("enable", enable);

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Cancha cancha = Cancha.getCancha(response);

                        if (listener != null) {
                            listener.onCanchaCreated(cancha);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(mApplication, "JSON Exception", Toast.LENGTH_SHORT).show();
                    }

                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, "Ocurrió un error al crear estacionamiento", Toast.LENGTH_SHORT).show();

                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,jsonRequest, successListener, errorListener) {
                public Map<String,String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + mModel.getUser().getToken());
                    return headers;
                }
            };
            mRequestQueue.add(request);
        }
        catch (JSONException e) {
            Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        }
    }

    @Override
    public void updateCancha(int id, String name, String description, double price, String contact, String address, double latitude, double longitude, int distritoId, boolean enable, final APIListener listener) {
        String url = BASE_URL + "parqueos/" + id;
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("name", name);
            jsonRequest.put("description", description);
            jsonRequest.put("price", price);
            jsonRequest.put("contact", contact);
            jsonRequest.put("address", address);
            jsonRequest.put("latitude", latitude);
            jsonRequest.put("longitude", longitude);
            jsonRequest.put("distritoId", distritoId);
            jsonRequest.put("enable", enable);

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Resp resp = Resp.getResp(response);
                        if (listener != null) {
                            listener.onCanchaUpdated(resp);
                        }
                    }
                    catch (JSONException e) {
                        Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_SHORT).show();
                    }

                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, "Ocurrió un error al actualizar estacionamiento", Toast.LENGTH_SHORT).show();

                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url,jsonRequest, successListener, errorListener) {
                public Map<String,String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + mModel.getUser().getToken());
                    return headers;
                }
            };
            mRequestQueue.add(request);
        }
        catch (JSONException e) {
            Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        }
    }

    @Override
    public void deleteCancha(int id, final APIListener listener) {
        String url = BASE_URL + "parqueos/" + id;

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Resp resp = Resp.getResp(response);
                    if (listener != null) {
                        listener.onCanchaDeleted(resp);
                    }
                }
                catch (JSONException e) {
                    Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mApplication, "Ocurrió un error al eliminar parqueo", Toast.LENGTH_SHORT).show();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, successListener, errorListener) {
            public Map<String,String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + mModel.getUser().getToken());
                return headers;
            }
        };
        mRequestQueue.add(request);

    }

    @Override
    public void loadCanchasDistrito(int distritoId, final APIListener listener) {
        String url = BASE_URL + "parqueos/bydistrito?distritoId=" + distritoId;
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("distritoId", distritoId);

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        List<Cancha> canchas = Cancha.getCanchas(response);
                        //Toast.makeText(mApplication, "lista:" + response.toString(), Toast.LENGTH_SHORT).show();
                        if (listener != null) {
                            listener.onCanchasDistritoLoaded(canchas);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(mApplication, "JSON Exception", Toast.LENGTH_SHORT).show();
                    }

                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, "Ocurrió un error al traer estacionamientos", Toast.LENGTH_SHORT).show();

                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,jsonRequest, successListener, errorListener) {
                public Map<String,String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + mModel.getUser().getToken());
                    return headers;
                }

            };
            mRequestQueue.add(request);
        }
        catch (JSONException e) {
            Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        }
    }

    @Override
    public void loadHorasDisp(int canchaId, String fecha, final APIListener listener) {
        String url = BASE_URL + "horas/dispcanchafecha?canchaId=" + canchaId + "&fecha=" + fecha;

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Hora> horas = Hora.getHoras(response);
                    if (listener != null) {
                        listener.onHorasDispLoaded(horas);
                    }
                } catch (JSONException e) {
                    Toast.makeText(mApplication, "JSON Exception", Toast.LENGTH_SHORT).show();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mApplication, "Ocurrió un error al traer distritos", Toast.LENGTH_SHORT).show();

            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null, successListener, errorListener) {
            public Map<String,String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + mModel.getUser().getToken());
                return headers;
            }
        };
        mRequestQueue.add(request);

    }

    @Override
    public void createAlquiler(int canchaId, String fecha, int horaId, String userId, Double price, final APIListener listener) {
        String url = BASE_URL + "alquileres";
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("canchaId", canchaId);
            jsonRequest.put("fecha", fecha);
            jsonRequest.put("horaId", horaId);
            jsonRequest.put("userId", userId);
            jsonRequest.put("price", price);

            Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Alquiler alquiler = Alquiler.getAlquiler(response);

                        if (listener != null) {
                            listener.onAlquilerCreated(alquiler);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(mApplication, "JSON Exception", Toast.LENGTH_SHORT).show();
                    }

                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mApplication, "Ocurrió un error en reserva", Toast.LENGTH_SHORT).show();

                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,jsonRequest, successListener, errorListener) {
                public Map<String,String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + mModel.getUser().getToken());
                    return headers;
                }
            };
            mRequestQueue.add(request);
        }
        catch (JSONException e) {
            Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadMisAlquileres(final APIListener listener) {
        String url = BASE_URL + "alquileres/deusuario";

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(mApplication, "JSON Exception" + response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    List<Alquiler> alquileres = Alquiler.getAlquileres(response);
                    if (listener != null) {
                        //Toast.makeText(mApplication, "JSON Exception" + alquileres.size(), Toast.LENGTH_SHORT).show();
                        listener.onMisAlquileresLoaded(alquileres);
                    }

                } catch (JSONException e) {
                    Toast.makeText(mApplication, "JSON Exception", Toast.LENGTH_SHORT).show();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mApplication, "Ocurrió un error al traer alquileres", Toast.LENGTH_SHORT).show();

            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null, successListener, errorListener) {
            public Map<String,String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + mModel.getUser().getToken());
                return headers;
            }
        };
        mRequestQueue.add(request);
    }

    @Override
    public void deleteAlquiler(int id, final APIListener listener) {
        String url = BASE_URL + "alquileres/" + id;

        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Resp resp = Resp.getResp(response);
                    if (listener != null) {
                        listener.onAlquilerDeleted(resp);
                    }
                }
                catch (JSONException e) {
                    Toast.makeText(mApplication, "JSON exception", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mApplication, "Ocurrió un error al eliminar cancha", Toast.LENGTH_SHORT).show();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, successListener, errorListener) {
            public Map<String,String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + mModel.getUser().getToken());
                return headers;
            }
        };
        mRequestQueue.add(request);

    }


}
