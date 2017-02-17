/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.util.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author claudio
 */
public class ValorActualizadoDTO implements Serializable {
    private Integer imponible;
    private Integer rentaAfecta;
    private Integer rentaNoAfecta;
    private Integer impuestoUnico;
    private Integer imponibleActualizado;
    private Integer rentaAfectaActualizada;
    private Integer impuestoUnicoActualizado;
    private List<Integer> mesesTrabajados;

    public ValorActualizadoDTO() {
        imponible = 0;
        rentaAfecta = 0;
        rentaNoAfecta = 0;
        impuestoUnico = 0;
        imponibleActualizado = 0;
        rentaAfectaActualizada = 0;
        impuestoUnicoActualizado = 0;
        mesesTrabajados = new ArrayList<>();
    }

    public Integer getImponible() {
        return imponible;
    }

    public void setImponible(Integer imponible) {
        this.imponible = imponible;
    }

    public Integer getRentaAfecta() {
        return rentaAfecta;
    }

    public void setRentaAfecta(Integer rentaAfecta) {
        this.rentaAfecta = rentaAfecta;
    }

    public Integer getImpuestoUnico() {
        return impuestoUnico;
    }

    public void setImpuestoUnico(Integer impuestoUnico) {
        this.impuestoUnico = impuestoUnico;
    }

    public Integer getImponibleActualizado() {
        return imponibleActualizado;
    }

    public void setImponibleActualizado(Integer imponibleActualizado) {
        this.imponibleActualizado = imponibleActualizado;
    }

    public Integer getRentaAfectaActualizada() {
        return rentaAfectaActualizada;
    }

    public void setRentaAfectaActualizada(Integer rentaAfectaActualizada) {
        this.rentaAfectaActualizada = rentaAfectaActualizada;
    }

    public Integer getImpuestoUnicoActualizado() {
        return impuestoUnicoActualizado;
    }

    public void setImpuestoUnicoActualizado(Integer impuestoUnicoActualizado) {
        this.impuestoUnicoActualizado = impuestoUnicoActualizado;
    }

    public List<Integer> getMesesTrabajados() {
        return mesesTrabajados;
    }

    public void setMesesTrabajados(List<Integer> mesesTrabajados) {
        this.mesesTrabajados = mesesTrabajados;
    }

    public Integer getRentaNoAfecta() {
        return rentaNoAfecta;
    }

    public void setRentaNoAfecta(Integer rentaNoAfecta) {
        this.rentaNoAfecta = rentaNoAfecta;
    }
}
