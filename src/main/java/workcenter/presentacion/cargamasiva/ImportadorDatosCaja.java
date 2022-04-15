package workcenter.presentacion.cargamasiva;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import workcenter.entidades.Concepto;
import workcenter.entidades.Dinero;
import workcenter.entidades.Personal;
import workcenter.entidades.Vuelta;
import workcenter.negocio.cargamasiva.LogicaCargaMasiva;
import workcenter.util.components.FacesUtil;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void subir() throws Exception {
        XSSFWorkbook wb = null;
        wb = new XSSFWorkbook(archivo.getInputstream());
        XSSFSheet ws = wb.getSheetAt(0);
        int numRow = 0;
        XSSFRow row;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long rutParteNumerica = 0l;
        while ((row = ws.getRow(numRow++)) != null) {
            try {
                row.getCell(0).getNumericCellValue();
            } catch (Exception e) {
                continue;
            }

            Dinero d = new Dinero();
            try {
                rutParteNumerica = obtenerRut(row.getCell(2).getStringCellValue());
                Personal p = new Personal();
                p.setRut(rutParteNumerica);
                d.setReceptor(p);
                d.setMonto((int) row.getCell(3).getNumericCellValue());
                if (row.getCell(4) != null) {
                    int idVuelta = (int) row.getCell(4).getNumericCellValue();
                    Vuelta vuelta = new Vuelta();
                    vuelta.setId(idVuelta);
                    d.setOrdendecarga(vuelta);
                }
                if (row.getCell(5) != null && row.getCell(5).getDateCellValue() != null) {
                    d.setFechaactivo(row.getCell(5).getDateCellValue());
                } else {
                    String fechaCarga = sdf.format(fecha);
                    d.setFechaactivo(sdf.parse(fechaCarga));
                }
                String fechaActual = sdf.format(new Date());
                d.setFechareal(sdf.parse(fechaActual));
                d.setConcepto(conceptoSeleccionado);
                logicaCargaMasiva.guardarDinero(d);
            } catch (PersistenceException e) {
                String value = "";
                try {
                    value = row.getCell(2).getStringCellValue();
                } catch (Exception ignored) {
                    value = String.valueOf(row.getCell(2).getNumericCellValue());
                }
                throw new Exception("row " + numRow + " : " + value);
            }
        }

        FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Carga masiva Ingresada");
    }

    public Long obtenerRut(String rut) {
        String cadena = rut.replace(".", "").replace(",", "").trim();
        String[] arregloCadena = cadena.split("-");
        return Long.valueOf(arregloCadena[0]);
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
