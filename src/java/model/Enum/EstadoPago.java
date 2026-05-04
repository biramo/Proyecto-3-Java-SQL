package model.Enum;

public enum EstadoPago {
    PENDIENTE,
    PAGADO;

    //para dejar el texto una vez que lo llame desde el crud usando el metodo en alquiler (le estamos pasando el index "key") para que nos devuelva el value
    public static EstadoPago desdeBD(int valor) {
        return EstadoPago.values()[valor];
    }

}