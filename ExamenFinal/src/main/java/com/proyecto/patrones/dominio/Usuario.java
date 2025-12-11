package com.proyecto.patrones.dominio;

public class Usuario {
    private final String id;
    private final String rol;
    public Usuario(String id, String rol){ this.id = id; this.rol = rol; }
    public String getId(){ return id; }
    public String getRol(){ return rol; }
    public boolean tieneRol(String r){ return rol != null && rol.equalsIgnoreCase(r); }
}
