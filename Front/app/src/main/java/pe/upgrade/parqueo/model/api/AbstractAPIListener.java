package pe.upgrade.parqueo.model.api;

import java.util.List;

public class AbstractAPIListener implements APIListener {
    @Override
    public void onLogin(User user) { }

    @Override
    public void onRegister(Resp resp) { }

    @Override
    public void onDistritosLoaded(List<Distrito> distritos) { }

    @Override
    public void onCanchasLoaded(List<Cancha> canchas) { }

    @Override
    public void onCanchaCreated(Cancha cancha) { }

    @Override
    public void onCanchaUpdated(Resp resp) { }

    @Override
    public void onCanchaDeleted(Resp resp) { }

    @Override
    public void onCanchasDistritoLoaded(List<Cancha> canchas) { }

    @Override
    public void onHorasDispLoaded(List<Hora> horas) { }

    @Override
    public void onAlquilerCreated(Alquiler alquiler) { }

    @Override
    public void onMisAlquileresLoaded(List<Alquiler> alquileres) { }

    @Override
    public void onAlquilerDeleted(Resp resp) { }
}
