package workcenter.presentacion.cargamasiva;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import workcenter.entidades.BonoDescuento;
import workcenter.entidades.BonoDescuentoPersonal;
import workcenter.entidades.Personal;
import workcenter.negocio.cargamasiva.LogicaCargaMasiva;
import workcenter.util.components.FacesUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by claudio on 27-02-16.
 */
@Component
@Scope("view")
public class ImportadorDatosBonoDescuento {
    private transient UploadedFile archivo;

    private List<BonoDescuento> bonoDescuentoList;
    private Date fechaDesde;
    private Date fechaHasta;
    private BonoDescuento bonoDescuentoSeleccionado;

    @Autowired
    private LogicaCargaMasiva logicaCargaMasiva;

    @PostConstruct
    public void inicio() {
        bonoDescuentoList = logicaCargaMasiva.obtenerBonosDescuentos();
    }

    @Transactional(readOnly = false)
    public void subir() {
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(archivo.getInputstream());
            XSSFSheet ws = wb.getSheetAt(0);
            int numRow = 0;
            XSSFRow row;
            Long rutParteNumerica = 0l;
            while ((row = ws.getRow(numRow++)) != null) {
                try {
                    row.getCell(0).getNumericCellValue();
                } catch (Exception e) {
                    continue;
                }

                BonoDescuentoPersonal d = new BonoDescuentoPersonal();
                try {
                    rutParteNumerica = obtenerRut(row.getCell(2).getStringCellValue());
                    Personal p = new Personal();
                    p.setRut(rutParteNumerica);

                    d.setIdPersonal(p);
                    d.setFechadesde(fechaDesde);
                    d.setFechahasta(fechaHasta);
                    d.setIdBonodescuento(bonoDescuentoSeleccionado);
                    d.setMonto(
                        BigInteger.valueOf((long) row.getCell(3).getNumericCellValue())
                    );
                    d.setFecha(new Date());
                    logicaCargaMasiva.guardarBonoDescuento(d);
                } catch (Exception e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "row " + numRow + " : " + row.getCell(2) != null ? row.getCell(2).getStringCellValue() : "Empty");
                }
            }

            if (numRow > 1) {
                FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Carga masiva Ingresada");
            } else {
                FacesUtil.mostrarMensajeError("Operación fallida", "Carga masiva no presenta datos");
            }
        } catch (IOException e) {
            e.printStackTrace();
            FacesUtil.mostrarMensajeError("Operación fallida", "El archivo no es válido");
        } catch (Exception e) {
            e.printStackTrace();
            FacesUtil.mostrarMensajeError("Operación fallida", "El archivo no es válido");
        }

    }

    public Long obtenerRut(String rut) {
        String cadena = rut.replace(".", "").replace(",", "").trim();
        String[] arregloCadena = cadena.split("-");
        return Long.parseLong(arregloCadena[0]);
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public List<BonoDescuento> getBonoDescuentoList() {
        return bonoDescuentoList;
    }

    public void setBonoDescuentoList(List<BonoDescuento> bonoDescuentoList) {
        this.bonoDescuentoList = bonoDescuentoList;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public BonoDescuento getBonoDescuentoSeleccionado() {
        return bonoDescuentoSeleccionado;
    }

    public void setBonoDescuentoSeleccionado(BonoDescuento bonoDescuentoSeleccionado) {
        this.bonoDescuentoSeleccionado = bonoDescuentoSeleccionado;
    }
}
