package workcenter.entidades;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="valores_cargas_familiares")
@NamedQuery(name="ValoresCargasFamiliare.findAll", query="SELECT v FROM ValoresCargasFamiliares v ORDER BY v.fechaVigencia DESC, v.desde ASC")
public class ValoresCargasFamiliares implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="desde", nullable=true)
	private Integer desde;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_vigencia")
	private Date fechaVigencia;

	@Column(name="hasta", nullable=true)
	private Integer hasta;
	
	@Column(name="monto", nullable=true)
	private Integer monto;

	public ValoresCargasFamiliares() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getDesde() {
		return this.desde;
	}

	public void setDesde(Integer desde) {
		this.desde = desde;
	}

	public Date getFechaVigencia() {
		return this.fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public Integer getHasta() {
		return this.hasta;
	}

	public void setHasta(Integer hasta) {
		this.hasta = hasta;
	}

	public Integer getMonto() {
		return this.monto;
	}

	public void setMonto(Integer monto) {
		this.monto = monto;
	}

}