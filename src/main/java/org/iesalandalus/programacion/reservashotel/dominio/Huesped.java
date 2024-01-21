package org.iesalandalus.programacion.reservashotel.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huesped {
    private static final String ER_TELEFONO= "[6,7,8,9][0-9]{8}";
    private static final String ER_CORREO = "^[_A-Za-z0-9\\+]+(\\.[_A-Za-z0-9\\+]+)*@[A-Za-z0-9-]+(\\.[_A-Za-z0-9+]*(\\.[A-Za-z]{2,})*)$";
    private static final String ER_DNI="([0-9]{8})([a-zA-Z])";
    public static final String FORMATO_FECHA= ("dd/MM/yyyy");

    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;


    public String getNombre() {
    return nombre;
    }

    public void setNombre(String nombre){
        if (nombre==null){
            throw  new NullPointerException ("ERROR: El nombre de un huésped no puede ser nulo.");
        }
        this.nombre=formateaNombre(nombre);

    }

    private String formateaNombre(String nombre){
        if (nombre==null){
            throw  new NullPointerException("ERROR: El nombre de un huésped no puede ser nulo.");
        }
        nombre = nombre.trim();

        String[] palabras = nombre.split("\\s");

        StringBuilder nombreFormateado = new StringBuilder();
        for (String palabra:palabras) {
            if (nombreFormateado.length() > 0) {
                nombreFormateado.append(" ");
            }
            nombreFormateado.append((Character.toUpperCase((palabra.charAt(0)))))
                    .append((palabra.substring(1).toLowerCase()));
        }


    return nombreFormateado.toString();
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        if (telefono==null){
            throw new NullPointerException("ERROR: El teléfono de un huésped no puede ser nulo.");
        }

        if (telefono.matches(ER_TELEFONO)){
            this.telefono=telefono;
        }
    }
    public String getCorreo(){
        return correo;
    }

    public void setCorreo(String correo){
        if (correo==null){
            throw  new NullPointerException("ERROR: El correo de un huésped no puede ser nulo.");
        }
        if (correo.matches(ER_CORREO)){
            this.correo=correo;
        }
        else {
            throw  new IllegalArgumentException("El correo de un huésped no puede ser nulo.");
        }

    }

    public String getDni(){
        return dni;
    }

    private void setDni(String dni){
        if (dni==null){
            throw  new NullPointerException("ERROR: El dni de un huésped no puede ser nulo.");
        }
        if (comprobarLetraDni(dni)){
            this.dni=dni;
        }
    }

    private boolean comprobarLetraDni(String dni) {
        // boolean resultado = true;

        Pattern patron;
        Matcher comparador;
        int numeroDni;
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        char caracterDni;

        patron = Pattern.compile(ER_DNI);
        comparador = patron.matcher(dni);

        if (!comparador.matches()) {
            throw new IllegalArgumentException("El dni no es valido");
        }
        numeroDni = Integer.parseInt(comparador.group(1));
        String letraDni =comparador.group(2);
        caracterDni = letras.charAt(numeroDni % 23);

        if (letraDni.equals(String.valueOf(caracterDni))) {
            return true;
        } else{
            throw new IllegalArgumentException("El dni introducido no es correcto.");
        }
    }


    private void setFechaNacimiento(LocalDate fechaNacimiento){
        if (fechaNacimiento==null){
            throw  new NullPointerException("ERROR: La fecha de nacimiento de un huésped no puede ser nula.");
        }

        this.fechaNacimiento= fechaNacimiento;
    }
    public LocalDate getFechaNacimiento(){
       //DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA);
        return fechaNacimiento;
    }
    private String getIniciales(){
       StringBuilder iniciales=new StringBuilder();
       StringTokenizer palabras = new StringTokenizer(nombre);


        while (palabras.hasMoreTokens()) {
            String palabra = palabras.nextToken();
            char inicial = Character.toUpperCase(palabra.charAt(0));
            iniciales.append(inicial);
        }
        return iniciales.toString();
    }


public Huesped(String nombre,String dni,String correo, String telefono,LocalDate fechaNacimiento){
        if (this==null){
            throw new NullPointerException("No se pudo crear el nuevo huésped.");
        }

        setNombre(nombre);
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);

}

public Huesped (Huesped huesped){
     Objects.requireNonNull(huesped, "No es posible copiar un huésped nulo.");


        setNombre(huesped.getNombre());
        setDni(huesped.getDni());
        setCorreo(huesped.getCorreo());
        setTelefono(huesped.getTelefono());
        setFechaNacimiento(huesped.getFechaNacimiento());
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Huesped huesped = (Huesped) o;
        return Objects.equals(dni, huesped.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }
    public String toString(){
        return String.format("nombre=%s (%s), DNI=%s, correo=%s, teléfono=%s, fecha nacimiento=%s",getNombre(),getIniciales(),getDni(), getCorreo(),
                getTelefono(),getFechaNacimiento().format(DateTimeFormatter.ofPattern(FORMATO_FECHA)));
    }
}

