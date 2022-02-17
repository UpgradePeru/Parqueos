package pe.upgrade.parqueo.model.api;

import java.util.List;

public interface APIListener {
    void onLogin(User user);

    void onRegister(Resp resp);

    void onDistritosLoaded(List<Distrito> distritos);

    void onCanchasLoaded(List<Cancha> canchas);

    void onCanchaCreated(Cancha cancha);

    void onCanchaUpdated(Resp resp);

    void onCanchaDeleted(Resp resp);

    void onCanchasDistritoLoaded(List<Cancha> canchas);

    void onHorasDispLoaded(List<Hora> horas);

    void onAlquilerCreated(Alquiler alquiler);

    void onMisAlquileresLoaded(List<Alquiler> alquileres);

    void onAlquilerDeleted(Resp resp);
}
