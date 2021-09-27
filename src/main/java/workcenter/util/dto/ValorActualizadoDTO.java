/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workcenter.util.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Integer rentaNoAfectaActualizada;
    private Integer impuestoUnicoActualizado;
    private List<Integer> mesesTrabajados;
    private Map<Mes, Integer> rentasAfectas;

    public ValorActualizadoDTO() {
        imponible = 0;
        rentaAfecta = 0;
        rentaNoAfecta = 0;
        impuestoUnico = 0;
        imponibleActualizado = 0;
        rentaAfectaActualizada = 0;
        rentaNoAfectaActualizada = 0;
        impuestoUnicoActualizado = 0;
        mesesTrabajados = new ArrayList<>();
        rentasAfectas = new HashMap<>();
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

    public Integer getRentaNoAfectaActualizada() {
        return rentaNoAfectaActualizada;
    }

    public void setRentaNoAfectaActualizada(Integer rentaNoAfectaActualizada) {
        this.rentaNoAfectaActualizada = rentaNoAfectaActualizada;
    }

    public Map<Mes, Integer> getRentasAfectas() {
        return rentasAfectas;
    }

    public void setRentasAfectas(Map<Mes, Integer> rentasAfectas) {
        this.rentasAfectas = rentasAfectas;
    }
}
