/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.presentacion.cargamasiva;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.cargamasiva.LogicaCargaMasiva;
import workcenter.negocio.personal.LogicaLiquidaciones;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.others.LiquidacionPdf;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author claudio
 */
@Component
@Scope("view")
public class ImportadorDatosLiquidaciones {
    private transient UploadedFile archivo;
    private String rutEmpleador;
    private static final long INCENTIVO_TVP = 72l;
    private static final long RESPONSABILIDAD_TVP = 68l;
    private static final long ASISTENCIA_TVP = 69l;
    private static final long SEGURIDAD_TVP = 70l;
    private static final long INCENTIVO_SEGURIDAD = 40l;
    private static final long INCENTIVO_PRODUCTIVIDAD = 54l;
    private static final long SEGURO_METLIFE = 59l;
    private static final long ACUERDO_MARCO = 43l;
    private static final long MOVILIZACION = 35l;
    private static final long COLACION = 36l;
    private static final long AGUINALDO = 106l;


    private BonoDescuento incentivoTVP;
    private BonoDescuento responsabilidadTVP;
    private BonoDescuento asistenciaTVP;
    private BonoDescuento seguridadTVP;
    private BonoDescuento incentivoSeguridad;
    private BonoDescuento incentivoProductividad;
    private BonoDescuento acuerdoMarco;
    private BonoDescuento metlife;
    private BonoDescuento movilizacion;
    private BonoDescuento colacion;
    private BonoDescuento aguinaldo;

    @Autowired
    private LogicaLiquidaciones logicaLiquidaciones;
    @Autowired
    private LogicaCargaMasiva logicaCargaMasiva;
    @Autowired
    private Constantes constantes;
    @Autowired
    private LiquidacionPdf liquidacionPdf;
    @Autowired
    private LogicaPersonal logicaPersonal;
    @Autowired
    private LogicaEmpresas logicaEmpresas;

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
                metlife = b;
            else if (b.getId() == ACUERDO_MARCO)
                acuerdoMarco = b;
            else if (b.getId() == MOVILIZACION)
                movilizacion = b;
            else if (b.getId() == COLACION)
                colacion = b;
            else if (b.getId() == AGUINALDO)
                aguinaldo = b;
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
            List<Remuneracion> remuneraciones = new ArrayList<>();
            while ((row = ws.getRow(numRow++)) != null) {
                numColum = 0;
                Date fecha2;
                try {
                    fecha2 = row.getCell(numColum++).getDateCellValue();
                    if (fecha2 == null) continue;
                } catch (Exception e) {
                    continue;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha2);

                Variable utm = logicaLiquidaciones.obtenerValorUtm(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
//                String fecha = row.getCell(numColum++).getStringCellValue();
//                if (!fecha.matches("[a-z]{3}-[0-9]{2}")) {
//                    continue;
//                }
                Remuneracion remuneracion = new Remuneracion();
                Empresa e = logicaEmpresas.obtenerEmpresa(Long.valueOf(rutEmpleador));
                remuneracion.setRutEmpleador(rutEmpleador+"-"+e.getDigitoverificador());
                remuneracion.setEmpleador(e.getNombre());
                remuneracion.setRemuneracionBonoDescuentoList(new ArrayList<BonoDescuentoRemuneracion>());

//                remuneracion.setFechaLiquidacion(conversorFecha(fecha));
                remuneracion.setFechaLiquidacion(fecha2);

                Personal personal = logicaPersonal.obtener(Long.valueOf(row.getCell(numColum++).getStringCellValue().split("-")[0].replaceAll("\\.", "").replaceAll(",", "")));
                remuneracion.setIdPersonal(personal);
                ContratoPersonal cp = logicaLiquidaciones.obtenerDatosContrato(remuneracion.getIdPersonal());

                numColum += 2;

                remuneracion.setDiasTrabajados((int) row.getCell(numColum++).getNumericCellValue());
                remuneracion.setSueldoBase((int) row.getCell(numColum++).getNumericCellValue());
                remuneracion.setGratificacion((int) row.getCell(numColum++).getNumericCellValue());

                BonoDescuentoRemuneracion bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setBono(true);
                bonoDescuentoRemuneracion.setIdMaestroGuia(remuneracion);
                bonoDescuentoRemuneracion.setDescripcion(incentivoTVP.getDescripcion());
                bonoDescuentoRemuneracion.setIdBonoDescuento(incentivoTVP.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long) row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(incentivoTVP.getImponible());
                if (bonoDescuentoRemuneracion.getMonto().intValue() > 0)
                    remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);
                
                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setBono(true);
                bonoDescuentoRemuneracion.setIdMaestroGuia(remuneracion);
                bonoDescuentoRemuneracion.setDescripcion(aguinaldo.getDescripcion());
                bonoDescuentoRemuneracion.setIdBonoDescuento(aguinaldo.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long) row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(aguinaldo.getImponible());
                if (bonoDescuentoRemuneracion.getMonto().intValue() > 0)
                    remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);

                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setBono(true);
                bonoDescuentoRemuneracion.setIdMaestroGuia(remuneracion);
                bonoDescuentoRemuneracion.setDescripcion(asistenciaTVP.getDescripcion());
                bonoDescuentoRemuneracion.setIdBonoDescuento(asistenciaTVP.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long) row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(asistenciaTVP.getImponible());
                if (bonoDescuentoRemuneracion.getMonto().intValue() > 0)
                    remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);

                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setBono(true);
                bonoDescuentoRemuneracion.setIdMaestroGuia(remuneracion);
                bonoDescuentoRemuneracion.setDescripcion(seguridadTVP.getDescripcion());
                bonoDescuentoRemuneracion.setIdBonoDescuento(seguridadTVP.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long) row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(seguridadTVP.getImponible());
                if (bonoDescuentoRemuneracion.getMonto().intValue() > 0)
                    remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);

                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setBono(true);
                bonoDescuentoRemuneracion.setIdMaestroGuia(remuneracion);
                bonoDescuentoRemuneracion.setDescripcion(responsabilidadTVP.getDescripcion());
                bonoDescuentoRemuneracion.setIdBonoDescuento(responsabilidadTVP.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long) row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(responsabilidadTVP.getImponible());
                if (bonoDescuentoRemuneracion.getMonto().intValue() > 0)
                    remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);

                remuneracion.setSemanaCorrida((int) row.getCell(numColum++).getNumericCellValue());

                long is = (long) row.getCell(numColum++).getNumericCellValue();
                is += (long) row.getCell(numColum++).getNumericCellValue();

                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setBono(true);
                bonoDescuentoRemuneracion.setIdMaestroGuia(remuneracion);
                bonoDescuentoRemuneracion.setDescripcion(incentivoSeguridad.getDescripcion());
                bonoDescuentoRemuneracion.setIdBonoDescuento(incentivoSeguridad.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf(is));
                bonoDescuentoRemuneracion.setImponible(incentivoSeguridad.getImponible());
                if (bonoDescuentoRemuneracion.getMonto().intValue() > 0)
                    remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);

                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setBono(true);
                bonoDescuentoRemuneracion.setIdMaestroGuia(remuneracion);
                bonoDescuentoRemuneracion.setDescripcion(incentivoProductividad.getDescripcion());
                bonoDescuentoRemuneracion.setIdBonoDescuento(incentivoProductividad.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long) row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(incentivoProductividad.getImponible());
                if (bonoDescuentoRemuneracion.getMonto().intValue() > 0)
                    remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);

                remuneracion.setTotalImponible((int) row.getCell(numColum++).getNumericCellValue());

                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setBono(true);
                bonoDescuentoRemuneracion.setIdMaestroGuia(remuneracion);
                bonoDescuentoRemuneracion.setDescripcion(colacion.getDescripcion());
                bonoDescuentoRemuneracion.setIdBonoDescuento(colacion.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long) row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(colacion.getImponible());
                if (bonoDescuentoRemuneracion.getMonto().intValue() > 0)
                    remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);

                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setBono(true);
                bonoDescuentoRemuneracion.setIdMaestroGuia(remuneracion);
                bonoDescuentoRemuneracion.setDescripcion(movilizacion.getDescripcion());
                bonoDescuentoRemuneracion.setIdBonoDescuento(movilizacion.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long) row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(movilizacion.getImponible());
                if (bonoDescuentoRemuneracion.getMonto().intValue() > 0)
                    remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);

                int cargas = (int) row.getCell(numColum++).getNumericCellValue();
                remuneracion.setViatico((int) row.getCell(numColum++).getNumericCellValue());
                remuneracion.setTotalHaberes((int) row.getCell(numColum++).getNumericCellValue());
                remuneracion.setDectoAFP((int) row.getCell(numColum++).getNumericCellValue());
                remuneracion.setDctoPrevision((int) row.getCell(numColum++).getNumericCellValue());

                numColum += 2;

                remuneracion.setRentaAfecta(remuneracion.getTotalImponible() - (remuneracion.getDctoPrevision() + remuneracion.getDectoAFP()));

                // seguro cesantia
                Double seguroEmpresa = (remuneracion.getTotalImponible() * constantes.getAportePorcentajeEmpleador()) / 100;
                Double seguroTrabajador = row.getCell(numColum++).getNumericCellValue();
                numColum += 1;

                if (cp == null || cp.getVencimiento() != null) {
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
                remuneracion.setImpUnico(calcularImpuestoUnico(remuneracion.getRentaAfecta(), Integer.parseInt(utm.getValor())));

                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setBono(false);
                bonoDescuentoRemuneracion.setIdMaestroGuia(remuneracion);
                bonoDescuentoRemuneracion.setDescripcion(acuerdoMarco.getDescripcion());
                bonoDescuentoRemuneracion.setIdBonoDescuento(acuerdoMarco.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long) row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(acuerdoMarco.getImponible());
                remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);

                bonoDescuentoRemuneracion = new BonoDescuentoRemuneracion();
                bonoDescuentoRemuneracion.setBono(false);
                bonoDescuentoRemuneracion.setIdMaestroGuia(remuneracion);
                bonoDescuentoRemuneracion.setDescripcion(metlife.getDescripcion());
                bonoDescuentoRemuneracion.setIdBonoDescuento(metlife.getId());
                bonoDescuentoRemuneracion.setMonto(BigInteger.valueOf((long) row.getCell(numColum++).getNumericCellValue()));
                bonoDescuentoRemuneracion.setImponible(metlife.getImponible());
                remuneracion.getRemuneracionBonoDescuentoList().add(bonoDescuentoRemuneracion);

                remuneracion.setTotalDctos((int) row.getCell(numColum++).getNumericCellValue());
                remuneracion.setAlcanceLiquido((int) row.getCell(numColum++).getNumericCellValue());
                remuneracion.setAnticipoSueldo((int) row.getCell(numColum++).getNumericCellValue());
                remuneracion.setLiqPagar((int) row.getCell(numColum++).getNumericCellValue());

                remuneracion.setFechaIngreso(new Date());
                liquidacionPdf.generarLiquidacion(remuneracion, cargas);
                remuneraciones.add(remuneracion);

            }
            logicaLiquidaciones.guardarDatosLiquidacion(remuneraciones);
            FacesUtil.mostrarMensajeInformativo("Operación exitosa", "Archivo cargado sin problemas");
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            FacesUtil.mostrarMensajeError("Operación fallida", "El archivo no es válido (Linea: " + numRow + ", Columna: " + numColum + ") ");
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Operación fallida", "El archivo no es válido (Linea: " + numRow + ", Columna: " + numColum + ") ");
            e.printStackTrace();
        }
    }

    private Date conversorFecha(String fechaStr) throws ParseException {
        String mes = fechaStr.split("-")[0];
        String anio = fechaStr.split("-")[1];

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        StringBuilder fecha = new StringBuilder();
        switch (mes.toLowerCase()) {
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

    private Double calcularImpuestoUnico(int rentaAfecta, int utm) throws Exception {

        try {
            double rentaAfectaUtm = (double) rentaAfecta / (double) utm;
            List<ValorImpuestoUnico> valorImpuestoUnicos = logicaLiquidaciones.obtenerValoresVigentesImpUnico();

            for (ValorImpuestoUnico viu : valorImpuestoUnicos) {
                if ((viu.getCotaMin() == null || rentaAfectaUtm > viu.getCotaMin().floatValue()) &&
                        (viu.getCotaMax() == null || rentaAfectaUtm <= viu.getCotaMax().floatValue())) {
                    //return Double.valueOf((rentaAfectaUtm * viu.getFactor().doubleValue() - viu.getSubstraendo().doubleValue()) * utm);
                    return Math.round(Double.valueOf((rentaAfectaUtm * viu.getFactor().doubleValue() - viu.getSubstraendo().doubleValue()) * utm) * Math.pow(10, 0)) / Math.pow(10, 0);
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return -1.0;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public String getRutEmpleador() {
        return rutEmpleador;
    }

    public void setRutEmpleador(String rutEmpleador) {
        this.rutEmpleador = rutEmpleador;
    }
}
