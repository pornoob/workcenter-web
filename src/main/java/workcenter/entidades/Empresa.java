package workcenter.entidades;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "empresas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"),
    @NamedQuery(name = "Empresa.findById", query = "SELECT e FROM Empresa e WHERE e.id = :id"),
    @NamedQuery(name = "Empresa.findByNombre", query = "SELECT e FROM Empresa e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Empresa.findByRut", query = "SELECT e FROM Empresa e WHERE e.rut = :rut"),
    @NamedQuery(name = "Empresa.findByDigitoverificador", query = "SELECT e FROM Empresa e WHERE e.digitoverificador = :digitoverificador"),
    @NamedQuery(name = "Empresa.findByTelefono", query = "SELECT e FROM Empresa e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Empresa.findByDireccion", query = "SELECT e FROM Empresa e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Empresa.findByGiro", query = "SELECT e FROM Empresa e WHERE e.giro = :giro"),
    @NamedQuery(name = "Empresa.findByLogo", query = "SELECT e FROM Empresa e WHERE e.logo = :logo")})
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Size(max = 70)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "rut")
    private Long rut;
    @Column(name = "digitoverificador")
    private Character digitoverificador;
    @Size(max = 30)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 300)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 200)
    @Column(name = "giro")
    private String giro;
    @Size(max = 400)
    @Column(name = "logo")
    private String logo;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "empresa")
    private Set<ContactoEmpresa> contactos;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "empresa")
    private Set<ContratoEmpresa> contratos;

    public Empresa() {
    }

    public Empresa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getRut() {
        return rut;
    }

    public void setRut(Long rut) {
        this.rut = rut;
    }

    public Character getDigitoverificador() {
        return digitoverificador;
    }

    public void setDigitoverificador(Character digitoverificador) {
        this.digitoverificador = digitoverificador;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<ContactoEmpresa> getContactos() {
        return contactos;
    }

    public void setContactos(Set<ContactoEmpresa> contactos) {
        this.contactos = contactos;
    }

    public Set<ContratoEmpresa> getContratos() {
        return contratos;
    }

    public void setContratos(Set<ContratoEmpresa> contratos) {
        this.contratos = contratos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if (this.getId() == null || other.getId() == null) return false;

        return this.getId().intValue() == other.getId().intValue();
    }

    @Override
    public String toString() {
        return "workcenter.entities.Empresa[ id=" + id + " ]";
    }
    
}
