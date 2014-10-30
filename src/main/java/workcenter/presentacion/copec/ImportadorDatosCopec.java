package workcenter.presentacion.copec;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.RendimientoCopec;
import workcenter.negocio.equipos.LogicaProveedorPetroleo;
import workcenter.util.components.FacesUtil;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author colivares
 */
@Component
@Scope("view")
public class ImportadorDatosCopec {
    private transient UploadedFile archivo;
    private Date ultimaActualizacion;

    @Autowired
    private LogicaProveedorPetroleo logicaProveedorPetroleo;

    public void subir() {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(archivo.getInputstream());
            HSSFSheet ws = wb.getSheet("Hoja1");
            int numRow = 1;
            HSSFRow row;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            while ((row = ws.getRow(numRow++)) != null) {
                RendimientoCopec rc = new RendimientoCopec();
                rc.setDepartamento((int) row.getCell(0).getNumericCellValue());
                rc.setPatente(row.getCell(1).getStringCellValue());
                rc.setVehiculo((int) row.getCell(2).getNumericCellValue());
                rc.setTarjeta(row.getCell(3).getStringCellValue());
                try {
                    rc.setFecha(sdf.parse(row.getCell(4).getStringCellValue()+" "+row.getCell(5).getStringCellValue()));
                } catch (ParseException e) {
                    rc.setFecha(null);
                }

                rc.setEstacionDeServicio(row.getCell(6).getStringCellValue());
                rc.setGuiaDespacho((int) row.getCell(7).getNumericCellValue());
                try {
                    rc.setPrecio((int) Float.valueOf(row.getCell(8).getStringCellValue()).floatValue());
                } catch (IllegalStateException ise) {
                    rc.setPrecio((int) row.getCell(8).getNumericCellValue());
                }
                try {
                    rc.setLitros(Float.valueOf(row.getCell(9).getStringCellValue()));
                } catch (IllegalStateException ise) {
                    rc.setLitros((float) row.getCell(9).getNumericCellValue());
                }
                rc.setMonto((int) row.getCell(10).getNumericCellValue());
                rc.setOdometro((int) row.getCell(11).getNumericCellValue());
                try {
                    rc.setKmLt(Float.valueOf(row.getCell(12).getStringCellValue()));
                } catch (IllegalStateException ise) {
                    rc.setKmLt(Float.valueOf((float) row.getCell(12).getNumericCellValue()));
                } catch (NumberFormatException nfe) {
                    rc.setKmLt(null);
                }
                try {
                    logicaProveedorPetroleo.guardarRendimientoDiario(rc);
                    System.out.println("RC: " + rc);
                } catch (PersistenceException e) {
                }
            }
            FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Se han actualizado los datos");
        } catch (IOException e) {
            e.printStackTrace();
            FacesUtil.mostrarMensajeError("Operación fallida", "El archivo no es válido");
        } catch (Exception e) {
            e.printStackTrace();
            FacesUtil.mostrarMensajeError("Operación fallida", "El archivo no es válido");
        }
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public Date getUltimaActualizacion() {
        if (ultimaActualizacion == null)
            ultimaActualizacion = logicaProveedorPetroleo.obtenerUltimaActualizacion();
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(Date ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

//    https://www.copec.cl/wseext/CopecWebPortalTaeTct/ExcelServlet?tipoArchivo=rendimientos&nameFile=InformeRendimientosDiario&tipo=0&fc_ini=01-10-2014&fc_fin=01-10-2014
}
