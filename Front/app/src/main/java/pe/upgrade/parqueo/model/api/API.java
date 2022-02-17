package pe.upgrade.parqueo.model.api;

public interface API {
    void login(String email, String password, APIListener listener);

    void register(String name, String email, String password, String nroDoc, String celular, String licencia, String placa, APIListener listener);

    void loadDistritos(boolean disponibles, APIListener listener);

    void loadCanchas(APIListener listener);

    void createCancha(String name, String description, double price, String contact, String address, double latitude, double longitude, int distritoId, boolean enable, APIListener listener);

    void updateCancha(int id, String name, String description, double price, String contact, String address, double latitude, double longitude, int distritoId, boolean enable, APIListener listener);

    void deleteCancha(int id, APIListener listener);

    void loadCanchasDistrito(int distritoId, APIListener listener);

    void loadHorasDisp(int canchaId, String fecha, APIListener listener);

    void createAlquiler(int canchaId, String fecha, int horaId, String userId, Double price, APIListener listener);

    void loadMisAlquileres(APIListener listener);

    void deleteAlquiler(int id, APIListener listener);
}
