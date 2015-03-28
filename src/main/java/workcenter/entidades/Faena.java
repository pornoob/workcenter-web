package workcenter.entidades;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;


/**
 * clase faena entidad que servira para la persistencia a la base de dato.
 * 
 */
@NamedQueries({
	@NamedQuery(name = "Faena.findAll", query = "SELECT f FROM Faena f ORDER BY f.nombre"),
	@NamedQuery(name = "Faena.findByFaena", query = "SELECT f FROM Faena f WHERE f.nombre LIKE :nombre")
})
@Entity
@Table(name = "faena")
public class Faena implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name ="id")
	private int id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@ManyToMany(mappedBy="faena")
	 private List<Personal> personal= new ArrayList<Personal>();
	
	
    public List<Personal> getPersonal() {
		return personal;
	}

	public void setPersonal(List<Personal> personal) {
		this.personal = personal;
	}

	public Faena() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}