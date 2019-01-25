/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.presentacion.cargamasiva;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Remuneracion;
import workcenter.negocio.personal.LogicaLiquidaciones;

/**
 * @author claudio
 */
@Component
@Scope("view")
public class ImportadorDatosLiquidaciones {
    private Integer anio;
    private Integer mes;
    private Boolean isConfigured;

    private final LogicaLiquidaciones logicaLiquidaciones;

    @Autowired
    public ImportadorDatosLiquidaciones(LogicaLiquidaciones logicaLiquidaciones) {
        this.logicaLiquidaciones = logicaLiquidaciones;
        this.isConfigured = Boolean.FALSE;
    }

    public void subir(FileUploadEvent fue) {
        Long rut = Long.parseLong(fue.getFile().getFileName().replaceAll("\\.", "").split("-")[0]);
        Remuneracion remuneracion = logicaLiquidaciones.obtenerLiquidacion(anio, mes, rut);
        if (remuneracion != null) {
            remuneracion.setArchivo(fue.getFile().getContents());
            logicaLiquidaciones.guardarDatosLiquidacion(remuneracion);
        }
    }

    public void configurar() {
        isConfigured = Boolean.TRUE;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Boolean getIsConfigured() {
        return isConfigured;
    }

    public void setIsConfigured(Boolean configured) {
        isConfigured = configured;
    }
}
