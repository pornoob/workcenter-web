package workcenter.presentacion.cargamasiva;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Concepto;
import workcenter.entidades.Dinero;
import workcenter.entidades.Personal;
import workcenter.negocio.cargamasiva.LogicaCargaMasiva;
import workcenter.util.components.FacesUtil;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Scope("view")
public class ImportadorDatosCaja {
    private transient UploadedFile archivo;
	private Concepto conceptoSeleccionado;
	private Date fecha;
    private List<Concepto> listaConceptos;
	
	@Autowired
	LogicaCargaMasiva logicaCargaMasiva;

    @PostConstruct
    public void init() {
        listaConceptos = logicaCargaMasiva.obtenerConceptos();
    }

    public void subir() {
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(archivo.getInputstream());
            XSSFSheet ws = wb.getSheetAt(0);
            int numRow = 0;
            XSSFRow row;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Integer rutParteNumerica = 0;
            while ((row = ws.getRow(numRow++)) != null) {
            	try {
            		row.getCell(0).getNumericCellValue();	
				} catch (Exception e) {
					continue;
				}
                
            	Dinero d = new Dinero();
                try {
                	rutParteNumerica = obtenerRut(row.getCell(1).getStringCellValue());
                    Personal p  = new Personal();
                    p.setRut(rutParteNumerica);
                    d.setReceptor(p);
				} catch (Exception e) {
					e.printStackTrace();
				}
                d.setMonto((int)row.getCell(4).getNumericCellValue());
                String fechaActual = sdf.format(new Date());
                String fechaCarga = sdf.format(fecha);
                d.setFechareal(sdf.parse(fechaActual));
                d.setFechaactivo(sdf.parse(fechaCarga));
                d.setConcepto(conceptoSeleccionado);
                try {              	
                	logicaCargaMasiva.guardarDinero(d);
                } catch (PersistenceException e) {
                }
           }
                
            FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Carga masiva Ingresada");           
        } catch (IOException e) {
            e.printStackTrace();
            FacesUtil.mostrarMensajeError("Operación fallida", "El archivo no es válido");
        } catch (Exception e) {
            e.printStackTrace();
            FacesUtil.mostrarMensajeError("Operación fallida", "El archivo no es válido");
        }
      
    }
    
    public Integer obtenerRut(String rut){
    	String cadena = rut.replace(".", "").replace(",", "").trim();
    	String[] arregloCadena = cadena.split("-");
    	return Integer.parseInt(arregloCadena[0]);
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Concepto getConceptoSeleccionado() {
		return conceptoSeleccionado;
	}

	public void setConceptoSeleccionado(Concepto conceptoSeleccionado) {
		this.conceptoSeleccionado = conceptoSeleccionado;
	}

    public List<Concepto> getListaConceptos() {
        return listaConceptos;
    }

    public void setListaConceptos(List<Concepto> listaConceptos) {
        this.listaConceptos = listaConceptos;
    }
}
