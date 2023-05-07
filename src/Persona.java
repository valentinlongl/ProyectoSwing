import java.time.LocalDate;

public class Persona {

    //region Atributes
    public int idPersona;
    public String nombre;
    public String apellido;
    public String residencia;
    private byte CantHijos;
    private LocalDate fechaNacimiento;
    //endregion

    //region Getters and Setters
    public byte getCantHijos() {
        return CantHijos;
    }

    public void setCantHijos(byte cantHijos) {
        CantHijos = cantHijos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    //endregion

    //region Constructor
    public Persona(int idPersona, String nombre, String apellido, String residencia, byte cantHijos, LocalDate fechaNacimiento) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.residencia = residencia;
        this.CantHijos = cantHijos;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Persona(){
        this.idPersona = -1;
    }
    //endregion


}
