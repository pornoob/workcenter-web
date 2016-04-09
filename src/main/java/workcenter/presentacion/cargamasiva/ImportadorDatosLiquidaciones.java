/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.presentacion.cargamasiva;

import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.BonoDescuento;
import workcenter.entidades.BonoDescuentoPersonal;
import workcenter.entidades.BonoDescuentoRemuneracion;
import workcenter.entidades.ContratoPersonal;
import workcenter.entidades.Personal;
import workcenter.entidades.Remuneracion;
import workcenter.negocio.cargamasiva.LogicaCargaMasiva;
import workcenter.negocio.personal.LogicaLiquidaciones;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;

/**
 *
 * @author claudio
 */
@Component
@Scope("view")
public class ImportadorDatosLiquidaciones {
    private transient UploadedFile archivo;
    private static final long INCENTIVO_TVP=72l;
    private static final long RESPONSABILIDAD_TVP=68l;
    private static final long ASISTENCIA_TVP=69l;
    private static final long SEGURIDAD_TVP=70l;
    private static final long INCENTIVO_SEGURIDAD=40l;
    private static final long INCENTIVO_PRODUCTIVIDAD=54l;
    private static final long SEGURO_METLIFE=59l;

    
    private BonoDescuento incentivoTVP;
    private BonoDescuento responsabilidadTVP;
    private BonoDescuento asistenciaTVP;
    private BonoDescuento seguridadTVP;
    private BonoDescuento incentivoSeguridad;
    private BonoDescuento incentivoProductividad;
       
    @Autowired
    private LogicaLiquidaciones logicaLiquidaciones;
    @Autowired
    private LogicaCargaMasiva logicaCargaMasiva;
    @Autowired
    private Constantes constantes;
    
    @PostConstruct
    public void inicio() {
        for (BonoDescuento b : logicaCargaMasiva.obtenerBonosDescuentos()) {
            if (b.getId() == INCENTIVO_TVP)
                incentivoTVP = b;
            else if (b.getId() == RESPONSABILIDAD_TVP)
                responsabilidadTVP = b;
            else if (b.getId() == ASISTENCIA_TVP)
                asistenciaTVP = b;
            else if (b.getId() == SEGURIDAD_TVP)
                seguridadTVP = b;
            else if (b.getId() == INCENTIVO_SEGURIDAD)
                incentivoSeguridad = b;
            else if (b.getId() == INCENTIVO_PRODUCTIVIDAD)
                incentivoProductividad = b;
            else if (b.getId() == SEGURO_METLIFE)
                incentivoProductividad = b;
        }
    }
    
    public void subir() {
        XSSFWorkbook wb = null;
        int numRow = 0;
        int numColum = 0;
        try {
            wb = new XSSFWorkbook(archivo.getInputstream());
            XSSFSheet ws = wb.getSheetAt(0);
            XSSFRow row;
            while ((row = ws.getRow(numRow++)) != null) {
                String fecha = row.getCell(numColum++).getStringCellValue();
                if (!fecha.matches("[a-z]{3}-[0-9]{2}")) {
                    continue;
                }
                Remuneracion remuneracion = new Remuneracion();
                remuneracion.setRemuneracionBonoDescuentoList(new ArrayList<BonoDescuentoRemuneracion>());
                
                remuneracion.setFechaLiquidacion(conversorFecha(fecha));
                
                Personal personal = new Personal(Integer.valueOf(row.getCell(numColum++).getStringCellValue().split("-")[0].replaceAll("\\.", "")));
                remuneracion.setIdPersonal(personal);
                ContratoPersonal cp = logicaLiquidaciones.obtenerDatosContrato(remuneracion.getIdPersonal());
                
                numColum += 2;
                
                remuneracion.setDiasTrabajados((int)row.getCell(numColum++).getNumericCellValue());
                remuneracion.setSueldoBase((int)row.getCell(numColum++).getNumericCellValue());
                remuneracion.setGratificacion((int)row.getCell(numColum++).getNumericCellValue());
                
                BonoDescuentoRemuneracion bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setIdBonoDescuento(incentivoTVP.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long)row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(incentivoTVP.getImponible());
                remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);
                
                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setIdBonoDescuento(asistenciaTVP.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long)row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(asistenciaTVP.getImponible());
                remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);
                
                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setIdBonoDescuento(seguridadTVP.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long)row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(seguridadTVP.getImponible());
                remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);
                
                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setIdBonoDescuento(responsabilidadTVP.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long)row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(responsabilidadTVP.getImponible());
                remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);
                
                remuneracion.setSemanaCorrida((int)row.getCell(numColum++).getNumericCellValue());
                
                long is = (long)row.getCell(numColum++).getNumericCellValue();
                is += (long)row.getCell(numColum++).getNumericCellValue();
                
                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setIdBonoDescuento(incentivoSeguridad.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf(is));
                bonoDescuentoRemuneracion.setImponible(incentivoSeguridad.getImponible());
                remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);
                
                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setIdBonoDescuento(incentivoProductividad.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf(is));
                bonoDescuentoRemuneracion.setImponible(incentivoProductividad.getImponible());
                remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);
                
                remuneracion.setTotalImponible((int)row.getCell(numColum++).getNumericCellValue());
                numColum += 2;
                remuneracion.setViatico((int)row.getCell(numColum++).getNumericCellValue());
                remuneracion.setTotalHaberes((int)row.getCell(numColum++).getNumericCellValue());
                remuneracion.setDectoAFP((int)row.getCell(numColum++).getNumericCellValue());
                remuneracion.setDctoPrevision((int)row.getCell(numColum++).getNumericCellValue());
                
                remuneracion.setRentaAfecta(remuneracion.getTotalImponible() - (remuneracion.getDctoPrevision() + remuneracion.getDectoAFP()));

                // seguro cesantia
                Double seguroEmpresa = (remuneracion.getTotalImponible() * constantes.getAportePorcentajeEmpleador()) / 100;
                Double seguroTrabajador = (remuneracion.getTotalImponible() * constantes.getAportePorcentajeTrabajador()) / 100;
                if (cp.getVencimiento() != null) {
                    Double noIndefinido = seguroEmpresa + seguroTrabajador;
                    remuneracion.setAporteEmpresa(noIndefinido);
                    remuneracion.setAporteTrabajador(0.0);
                } else {
                    remuneracion.setAporteEmpresa(seguroEmpresa);
                    remuneracion.setAporteTrabajador(seguroTrabajador);
                }
                remuneracion.setHorasExtras(0);
                remuneracion.setRentaAfecta(remuneracion.getRentaAfecta() - remuneracion.getAporteTrabajador().intValue());
                remuneracion.setEsGenerica(true);
            }
        } catch (IOException | ParseException | NumberFormatException e) {
            e.printStackTrace();
            FacesUtil.mostrarMensajeError("Operación fallida", "El archivo no es válido (Linea: "+numRow+", Columna: "+numColum+") ");
        }
    }
    
    private Date conversorFecha(String fechaStr) throws ParseException {
        String mes = fechaStr.split("-")[0];
        String anio = fechaStr.split("-")[1];
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        StringBuilder fecha = new StringBuilder();
        switch(mes.toLowerCase()) {
            case "ene":
                fecha.append("01/01/");
                break;
            case "feb":
                fecha.append("01/02/");
                break;
            case "mar":
                fecha.append("01/03/");
                break;
            case "abr":
                fecha.append("01/04/");
                break;
            case "may":
                fecha.append("01/05/");
                break;
            case "jun":
                fecha.append("01/06/");
                break;
            case "jul":
                fecha.append("01/07/");
                break;
            case "ago":
                fecha.append("01/08/");
                break;
            case "sep":
                fecha.append("01/09/");
                break;
            case "oct":
                fecha.append("01/10/");
                break;
            case "nov":
                fecha.append("01/11/");
                break;
            case "dic":
                fecha.append("01/12/");
                break;
        }
        Integer anioCompleto = 2000 + Integer.valueOf(anio);
        fecha.append(anioCompleto);
        
        return sdf.parse(fecha.toString());
    }
}
