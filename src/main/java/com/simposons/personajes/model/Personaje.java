package com.simposons.personajes.model;

import java.io.Serializable;

public class Personaje implements Serializable {
    private String nombre;
    private String ocupacion;
    private String genero;
    private String edad;
    private String estado;
    private String cita;
    private String imagen;

    public Personaje() {}

    public Personaje(String nombre, String ocupacion, String genero, String edad, 
                     String estado, String cita, String imagen) {
        this.nombre = nombre;
        this.ocupacion = ocupacion;
        this.genero = genero;
        this.edad = edad;
        this.estado = estado;
        this.cita = cita;
        this.imagen = imagen;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getOcupacion() { return ocupacion; }
    public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getEdad() { return edad; }
    public void setEdad(String edad) { this.edad = edad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCita() { return cita; }
    public void setCita(String cita) { this.cita = cita; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    @Override
    public String toString() {
        return nombre;
    }
}
