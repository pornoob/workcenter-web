package workcenter.entidades;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "personal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personal.findAll", query = "SELECT p FROM Personal p ORDER BY p.apellidos"),
    @NamedQuery(
            name = "Personal.findAllWithUser", 
            query = "SELECT DISTINCT p FROM Personal p "
            + "LEFT JOIN FETCH p.usuario u "
    ),
    @NamedQuery(name = "Personal.findByRut", query = "SELECT p FROM Personal p WHERE p.rut = :rut"),
    @NamedQuery(name = "Personal.findByRuts", query = "SELECT p FROM Personal p WHERE p.rut in :ruts"),
    @NamedQuery(
            name = "Personal.findByRutWithAccess",
            query = "SELECT DISTINCT p FROM Personal p "
            + "LEFT JOIN FETCH p.usuario u LEFT JOIN FETCH u.permisos per "
            + "LEFT JOIN FETCH per.proyecto "
            + "WHERE p.rut = :rut"
    ),
    @NamedQuery(
        name = "Personal.findByRutWithLiquidacion",
        query = "SELECT p FROM Personal p LEFT JOIN FETCH p.bonosDescuentos WHERE p = :personal"
    ),
    @NamedQuery(
        name = "Personal.findContratoActual",
        query = "SELECT cp FROM Personal p "
        + "INNER JOIN p.contratospersonalCollection cp "
        + "INNER JOIN FETCH cp.previsiones "
        + "WHERE p = :personal "
        + "ORDER BY cp.fecha DESC, cp.numero DESC"
    )
})
public class Personal implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutResponsable", fetch = FetchType.LAZY)
    private Collection<MpaEjecucionPlan> mpaEjecucionPlanCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutCreador", fetch = FetchType.LAZY)
    private Collection<MpaPlanPrograma> mpaPlanProgramaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutResponsable", fetch = FetchType.LAZY)
    private Collection<MpaPlanPrograma> mpaPlanProgramaCollection1;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sancionado", fetch = FetchType.LAZY, orphanRemoval = true)
    private Sancionado sancion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sancionado", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SancionRetiradaPersonal> sancionesRetiradas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personal", fetch = FetchType.LAZY)
    private List<DocumentoPersonal> documentos;

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    @Column(name = "rut")
    private Long rut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "digitoverificador")
    private char digitoverificador;
    @Size(max = 70)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 70)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 15)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 45)
    @Column(name = "celular")
    private String celular;
    @Size(max = 100)
    @Column(name = "mail")
    private String mail;
    @Size(max = 400)
    @Column(name = "foto")
    private String foto;
    @Size(max = 300)
    @Column(name = "domicilio")
    private String domicilio;
    @Size(max = 10)
    @Column(name = "ecivil")
    private String ecivil;
    @Column(name = "nacimiento")
    @Temporal(TemporalType.DATE)
    private Date nacimiento;
    @Size(max = 100)
    @Column(name = "contactoe")
    private String contactoe;
    @Size(max = 300)
    @Column(name = "edomicilio")
    private String edomicilio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersonal")
    private List<Remuneracion> maestroGuiaList;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "personal", cascade = CascadeType.ALL)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rut")
    private List<ContratoPersonal> contratospersonalCollection;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_servicio_ruta",
            inverseJoinColumns = {
                @JoinColumn(name = "id_servicio")
            },
            joinColumns = {
                @JoinColumn(name = "rut")
            }
    )
    private List<Servicio> servicios;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idPersonal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BonoDescuentoPersonal> bonosDescuentos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rutPersonal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CargasFamiliares> lstCargasFamiliares;

    public Personal() {
    }

    public Personal(Long rut) {
        this.rut = rut;
    }

    public Personal(Long rut, char digitoverificador) {
        this.rut = rut;
        this.digitoverificador = digitoverificador;
    }

    public String getNombreCompleto() {
        String string = getNombres() + " " + getApellidos();
        StringBuilder sb = new StringBuilder();
        for (String s : string.split(" ")) {
            sb.append(StringUtils.capitalize(s));
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    public String getApellidosNombres() {
        String string = getApellidos() + " " + getNombres();
        StringBuilder sb = new StringBuilder();
        for (String s : string.split(" ")) {
            sb.append(StringUtils.capitalize(s));
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    public Long getRut() {
        return rut;
    }

    public void setRut(Long rut) {
        this.rut = rut;
    }

    public char getDigitoverificador() {
        return digitoverificador;
    }

    public void setDigitoverificador(char digitoverificador) {
        this.digitoverificador = digitoverificador;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getEcivil() {
        return ecivil;
    }

    public void setEcivil(String ecivil) {
        this.ecivil = ecivil;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getContactoe() {
        return contactoe;
    }

    public void setContactoe(String contactoe) {
        this.contactoe = contactoe;
    }

    public String getEdomicilio() {
        return edomicilio;
    }

    public void setEdomicilio(String edomicilio) {
        this.edomicilio = edomicilio;
    }

    @XmlTransient
    public List<Remuneracion> getMaestroGuiaList() {
        return maestroGuiaList;
    }

    public void setMaestroGuiaList(List<Remuneracion> maestroGuiaList) {
        this.maestroGuiaList = maestroGuiaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rut != null ? rut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Personal)) {
            return false;
        }
        Personal other = (Personal) object;
        if (this.getRut() == null || other.getRut() == null) {
            return false;
        }
        return this.getRut().intValue() == other.getRut().intValue();
    }

    @Override
    public String toString() {
        return "workcenter.entities.Personal[ rut=" + rut + " ]";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ContratoPersonal> getContratospersonalCollection() {
        return contratospersonalCollection;
    }

    public void setContratospersonalCollection(List<ContratoPersonal> contratospersonalCollection) {
        this.contratospersonalCollection = contratospersonalCollection;
    }

    @XmlTransient
    public Collection<MpaPlanPrograma> getMpaPlanProgramaCollection() {
        return mpaPlanProgramaCollection;
    }

    public void setMpaPlanProgramaCollection(Collection<MpaPlanPrograma> mpaPlanProgramaCollection) {
        this.mpaPlanProgramaCollection = mpaPlanProgramaCollection;
    }

    @XmlTransient
    public Collection<MpaPlanPrograma> getMpaPlanProgramaCollection1() {
        return mpaPlanProgramaCollection1;
    }

    public void setMpaPlanProgramaCollection1(Collection<MpaPlanPrograma> mpaPlanProgramaCollection1) {
        this.mpaPlanProgramaCollection1 = mpaPlanProgramaCollection1;
    }

    @XmlTransient
    public Collection<MpaEjecucionPlan> getMpaEjecucionPlanCollection() {
        return mpaEjecucionPlanCollection;
    }

    public void setMpaEjecucionPlanCollection(Collection<MpaEjecucionPlan> mpaEjecucionPlanCollection) {
        this.mpaEjecucionPlanCollection = mpaEjecucionPlanCollection;
    }

    public Sancionado getSancion() {
        return sancion;
    }

    public void setSancion(Sancionado sancion) {
        this.sancion = sancion;
    }

    public List<DocumentoPersonal> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoPersonal> documentos) {
        this.documentos = documentos;
    }

    public List<SancionRetiradaPersonal> getSancionesRetiradas() {
        return sancionesRetiradas;
    }

    public void setSancionesRetiradas(List<SancionRetiradaPersonal> sancionesRetiradas) {
        this.sancionesRetiradas = sancionesRetiradas;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    public List<BonoDescuentoPersonal> getBonosDescuentos() {
        return bonosDescuentos;
    }

    public void setBonosDescuentos(List<BonoDescuentoPersonal> bonosDescuentos) {
        this.bonosDescuentos = bonosDescuentos;
    }

    public Set<CargasFamiliares> getLstCargasFamiliares() {
        return lstCargasFamiliares;
    }

    public void setLstCargasFamiliares(Set<CargasFamiliares> lstCargasFamiliares) {
        this.lstCargasFamiliares = lstCargasFamiliares;
    }

}
