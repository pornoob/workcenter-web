package workcenter.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="cargas_familiares")
@NamedQuery(name="CargasFamiliares.findAll", query="SELECT c FROM CargasFamiliares c")
public class CargasFamiliares implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="rut_carga")
	private Long rutCarga;
	
	@Column(name="apellidos")
	private String apellidos;

	@Column(name="dv_verificador_carga")
	private String dvVerificadorCarga;

	@Temporal(TemporalType.DATE)
	private Date nacimiento;
	
	@Column(name="nombres")
	private String nombres;

	@ManyToOne(fetch = FetchType.LAZY)	@JoinColumn(name="rut_personal" ,referencedColumnName="rut")
	private Personal rutPersonal;

	public CargasFamiliares() {
	}

	public Long getRutCarga() {
		return this.rutCarga;
	}

	public void setRutCarga(Long rutCarga) {
		this.rutCarga = rutCarga;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDvVerificadorCarga() {
		return this.dvVerificadorCarga;
	}

	public void setDvVerificadorCarga(String dvVerificadorCarga) {
		this.dvVerificadorCarga = dvVerificadorCarga;
	}

	public Date getNacimiento() {
		return this.nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Personal getRutPersonal() {
		return this.rutPersonal;
	}

	public void setRutPersonal(Personal rutPersonal) {
		this.rutPersonal = rutPersonal;
	}

}