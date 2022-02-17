package pe.upgrade.parqueo.model;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import pe.upgrade.parqueo.model.api.API;
import pe.upgrade.parqueo.model.api.APIListener;
import pe.upgrade.parqueo.model.api.Alquiler;
import pe.upgrade.parqueo.model.api.Cancha;
import pe.upgrade.parqueo.model.api.Distrito;
import pe.upgrade.parqueo.model.api.Hora;
import pe.upgrade.parqueo.model.api.Resp;
import pe.upgrade.parqueo.model.api.User;
import pe.upgrade.parqueo.model.api.WebAPI;

public class Model {
    private static Model sInstance = null;

    private final API mApi;

    private User mUser;
    private Resp mResp;

    private List<Distrito> mDistritos;
    private List<Cancha> mCanchas;


    private List<Cancha> mCanchasDistrito;

    private List<Hora> mHorasDisp;

    private Cancha mCancha;

    private Alquiler mAlquiler;

    private List<Alquiler> mMisAlquileres;

    public static Model getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new Model(application);
        }
        return sInstance;
    }

    private final Application mApplication;

    private Model(Application application) {
        mApplication = application;
        mApi = new WebAPI(mApplication, this);

        mDistritos = new ArrayList<>();
        mCanchas = new ArrayList<>();

        mMisAlquileres = new ArrayList<>();

        mCanchasDistrito = new ArrayList<>();
        mHorasDisp = new ArrayList<>();

    }

    public Application getmApplication() { return mApplication; }

    public void login(String email, String password, APIListener listener) {
        mApi.login(email, password, listener);
    }

    public void register(String name, String email, String password, String nroDoc, String celular, String licencia, String placa, APIListener listener) {
        mApi.register(name, email, password, nroDoc, celular, licencia, placa, listener);
    }

    public void createCancha(String name, String description, double price, String contact, String address, double latitude, double longitude, int distritoId, boolean enable, APIListener listener) {
        mApi.createCancha(name, description,price, contact, address, latitude, longitude, distritoId, enable, listener);
    }

    public void updateCancha(int id, String name, String description, double price, String contact, String address, double latitude, double longitude, int distritoId, boolean enable, APIListener listener) {
        mApi.updateCancha(id, name, description,price, contact, address, latitude, longitude, distritoId, enable, listener);
    }

    public void deleteCancha(int id, APIListener listener) {
        mApi.deleteCancha(id, listener);
    }


    public User getUser() {
        return mUser;
    }
    public void setUser(User user) {
        this.mUser = user;
    }

    public Resp getResp() {
        return mResp;
    }
    public void setResp(Resp resp) {
        this.mResp = resp;
    }

    public List<Distrito> getDistritos() { return mDistritos; }
    public void loadDistritos(boolean disponibles,APIListener listener) {
        mApi.loadDistritos(disponibles, listener);
    }

    public List<Cancha> getCanchas() { return mCanchas; }
    public void loadCanchas(APIListener listener) { mApi.loadCanchas(listener); }

    public Cancha getCancha() {
        return mCancha;
    }
    public void setCancha(Cancha cancha) {
        this.mCancha = cancha;
    }

    public List<Cancha> getCanchasDistrito() { return mCanchasDistrito; }
    public void loadCanchasDistrito(int distritoId, APIListener listener) { mApi.loadCanchasDistrito(distritoId, listener); }

    public List<Hora> getHorasDisp() { return mHorasDisp; }
    public void loadHorasDisp(int canchaId, String fecha, APIListener listener) { mApi.loadHorasDisp(canchaId, fecha, listener); }

    public void createAlquiler(int canchaId, String fecha, int horaId, String userId, Double price, APIListener listener) { mApi.createAlquiler(canchaId, fecha, horaId, userId, price,listener);}

    public List<Alquiler> getMisAlquileres() { return mMisAlquileres; }
    public void loadMisAlquileres(APIListener listener) { mApi.loadMisAlquileres(listener); }

    public void deleteAlquiler(int id, APIListener listener) {
        mApi.deleteAlquiler(id, listener);
    }


}
