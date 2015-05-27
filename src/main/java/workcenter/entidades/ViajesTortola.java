package workcenter.entidades;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;


/**
 * The persistent class for the viajes_tortola database table.
 * 
 */
@Entity
@Table(name="viajes_tortola")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ViajesTortola.findAll", query = "SELECT v FROM ViajesTortola v ORDER BY v.id DESC"),
    @NamedQuery(name = "ViajesTortola.findById", query = "SELECT v FROM ViajesTortola v WHERE v.id = :id"),
    @NamedQuery(name = "ViajesTortola.findByGuia", query = "SELECT v FROM ViajesTortola v WHERE v.numGuia = :numGuia")})
public class ViajesTortola implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, insertable = true, updatable = true)
	private String id;
	
	@Column(name = "destino")
	private String destino;

	@Basic
    @Temporal( TemporalType.DATE)
    @Column(name = "fecha", nullable = false, insertable = true, updatable = true)
	private Date fecha;    
    
    @Column(name="hora")
	private String hora;
	
	@Column(name="tonelaje")
	private Integer tonelaje;
	
	@Column(name="num_guia")
	private Integer numGuia;

	@Column(name="numer_tracto")
	private Integer numerTracto;

	@Column(name="nom_conductor")
	private String nom_conductor;
	
	@Column(name="pat_tracto")
	private String nomTracto;



    public ViajesTortola() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDestino() {
		return this.destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return this.hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getNom_conductor() {
		return nom_conductor;
	}

	public void setNom_conductor(String nom_conductor) {
		this.nom_conductor = nom_conductor;
	}

	public String getNomTracto() {
		return nomTracto;
	}

	public void setNomTracto(String nomTracto) {
		this.nomTracto = nomTracto;
	}

	public Integer getTonelaje() {
		return tonelaje;
	}

	public void setTonelaje(Integer tonelaje) {
		this.tonelaje = tonelaje;
	}

	public Integer getNumGuia() {
		return numGuia;
	}

	public void setNumGuia(Integer numGuia) {
		this.numGuia = numGuia;
	}

	public Integer getNumerTracto() {
		return numerTracto;
	}

	public void setNumerTracto(Integer numerTracto) {
		this.numerTracto = numerTracto;
	}
	

	
	

}