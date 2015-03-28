package workcenter.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.util.StringUtils;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "personal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personal.findAll", query = "SELECT p FROM Personal p ORDER BY p.apellidos"),
    @NamedQuery(name = "Personal.findAllWithUser", query = "SELECT p FROM Personal p "
            + "LEFT JOIN FETCH p.usuario"),
    @NamedQuery(name = "Personal.findByRut", query = "SELECT p FROM Personal p WHERE p.rut = :rut"),
    @NamedQuery(name = "Personal.findByRutWithAccess", query = "SELECT p FROM Personal p "
            + "LEFT JOIN FETCH p.usuario "
            + "WHERE p.rut = :rut"),
    @NamedQuery(name = "Personal.findByDigitoverificador", query = "SELECT p FROM Personal p WHERE p.digitoverificador = :digitoverificador"),
    @NamedQuery(name = "Personal.findByNombres", query = "SELECT p FROM Personal p WHERE p.nombres = :nombres"),
    @NamedQuery(name = "Personal.findByApellidos", query = "SELECT p FROM Personal p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Personal.findByTelefono", query = "SELECT p FROM Personal p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Personal.findByCelular", query = "SELECT p FROM Personal p WHERE p.celular = :celular"),
    @NamedQuery(name = "Personal.findByMail", query = "SELECT p FROM Personal p WHERE p.mail = :mail"),
    @NamedQuery(name = "Personal.findByFoto", query = "SELECT p FROM Personal p WHERE p.foto = :foto"),
    @NamedQuery(name = "Personal.findByDomicilio", query = "SELECT p FROM Personal p WHERE p.domicilio = :domicilio"),
    @NamedQuery(name = "Personal.findByEcivil", query = "SELECT p FROM Personal p WHERE p.ecivil = :ecivil"),
    @NamedQuery(name = "Personal.findByNacimiento", query = "SELECT p FROM Personal p WHERE p.nacimiento = :nacimiento"),
    @NamedQuery(name = "Personal.findByContactoe", query = "SELECT p FROM Personal p WHERE p.contactoe = :contactoe"),
    @NamedQuery(name = "Personal.findByEdomicilio", query = "SELECT p FROM Personal p WHERE p.edomicilio = :edomicilio")})
public class Personal implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutResponsable")
    private Collection<MpaEjecucionPlan> mpaEjecucionPlanCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutCreador")
    private Collection<MpaPlanPrograma> mpaPlanProgramaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutResponsable")
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
    private Integer rut;
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
    @OneToMany(fetch = FetchType.EAGER)
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

    public Personal() {
    }

    public Personal(Integer rut) {
        this.rut = rut;
    }

    public Personal(Integer rut, char digitoverificador) {
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

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
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
        if (this.getRut() == null || other.getRut() == null) return false;
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
}
