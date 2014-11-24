package workcenter.presentacion.registros.fatiga.somnolencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.MfsPregunta;
import workcenter.entidades.Personal;
import workcenter.entidades.Usuario;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.negocio.registros.LogicaFatigaSomnolencia;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.pojo.FilterOption;
import workcenter.util.pojo.Md5;

import java.io.Serializable;
import java.util.*;

/**
 * Created by claudio on 14-11-14.
 */
@Component
@Scope("flow")
public class MantenedorFatigaSomnolencia implements Serializable {
    @Autowired
    private LogicaFatigaSomnolencia logicaFatigaSomnolencia;

    @Autowired
    private LogicaPersonal logicaPersonal;

    @Autowired
    private Constantes constantes;

    private Personal conductor;
    private String rut;
    private String clave;
    private String confirmacionClave;
    private List<MfsPregunta> preguntasSeccion1;
    private List<MfsPregunta> preguntasSeccion2;
    private Map<MfsPregunta, Integer> respuestas;
    private List<FilterOption> escala;
    private List<Integer> indicesFilas;
    private List<Integer> indicesColumnas;
    private Map<Integer, Map<Integer, String>> matriz;
    private List<FilterOption> opcionesMatriz;

    public void inicio() {
        respuestas = new HashMap<MfsPregunta, Integer>();
        escala = new ArrayList<FilterOption>();
        escala.add(new FilterOption(1, "Nada"));
        escala.add(new FilterOption(2, "Levemente"));
        escala.add(new FilterOption(3, "Medianamente"));
        escala.add(new FilterOption(4, "Bastante"));
        escala.add(new FilterOption(5, "Extremadamente"));
    }

    public String irEncuesta() {
        if (esPrimeraEncuesta()) {
            if (!clave.equals(confirmacionClave)) {
                FacesUtil.mostrarMensajeError("Operación Fallida", "Las contraseñas no coinciden");
                return null;
            } else {
                Usuario u = new Usuario();
                u.setHabilitado(false);
                u.setPassword(Md5.hash(clave));
                u.setRut(conductor.getRut());
                u.setPersonal(conductor);
                conductor.setUsuario(u);
                logicaPersonal.guardar(conductor);
            }
        } else {
            if (!conductor.getUsuario().getPassword().equals(Md5.hash(clave))) {
                FacesUtil.mostrarMensajeError("Operación Fallida", "La contraseña no es válida");
                return null;
            }
        }
        preguntasSeccion1 = logicaFatigaSomnolencia.obtenerPreguntasSeccion1();
        this.randomizar(preguntasSeccion1);
        return "flowEncuesta";
    }

    public String irPaso1() {
        return "flowEncuesta";
    }

    public String irPaso2() {
        preguntasSeccion2 = logicaFatigaSomnolencia.obtenerPreguntasSeccion2();
        return "flowEncuestaPaso2";
    }

    public String irPaso3() {
        int filas = 5, columnas = 7;
        int maximoEnes = 6;

        String[] caracteres = {"M", "N"};
        Random random = constantes.getRandom();
        int enesCreadas = 0;
        int randomEnes = random.nextInt(maximoEnes - 1) + 1;

        matriz = new HashMap<Integer, Map<Integer, String>>();
        indicesFilas = new ArrayList<Integer>();
        indicesColumnas = new ArrayList<Integer>();

        for (int i = 0; i < columnas; i++) indicesColumnas.add(i);

        for (int i = 0; i < filas; i++) {
            indicesFilas.add(i);
            matriz.put(i, new HashMap<Integer, String>());
            for (int j = 0; j < columnas; j++) {
                int r = random.nextInt(2);
                if (r == 1) enesCreadas++;
                if (enesCreadas > randomEnes) matriz.get(i).put(j, caracteres[0]);
                else matriz.get(i).put(j, caracteres[r]);
            }
        }

        return "flowEncuestaPaso3";
    }

    private void randomizar(List<MfsPregunta> preguntas) {
        int cantPreguntas = 0;
        Map<Integer, Integer> repeticiones = new HashMap<Integer, Integer>();
        for (MfsPregunta p : preguntas) {
            if (p.getNumero() > cantPreguntas) cantPreguntas = p.getNumero();
            if (repeticiones.get(p.getNumero()) == null) repeticiones.put(p.getNumero(), 1);
            else repeticiones.put(p.getNumero(), (repeticiones.get(p.getNumero())).intValue() + 1);
        }

        Iterator iterator = repeticiones.entrySet().iterator();
        Random randomizer = constantes.getRandom();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (((Integer) entry.getValue()).intValue() < 2) continue;
            ;
            int random = randomizer.nextInt(((Integer) entry.getValue()).intValue());
            int contador = 0;
            for (MfsPregunta p : preguntas) {
                if (p.getNumero().intValue() == ((Integer) entry.getKey()).intValue()) {
                    if (contador != random) contador++;
                    else {
                        preguntas.remove(p);
                        break;
                    }
                }
            }
        }
    }

    public void obtenerConductor() {
        rut = rut.replaceAll("[^0-9]", "");
        conductor = logicaPersonal.obtener(Integer.valueOf(rut));
    }

    public boolean esPrimeraEncuesta() {
        return conductor == null || conductor.getUsuario() == null;
    }

    public Personal getConductor() {
        return conductor;
    }

    public void setConductor(Personal conductor) {
        this.conductor = conductor;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getConfirmacionClave() {
        return confirmacionClave;
    }

    public void setConfirmacionClave(String confirmacionClave) {
        this.confirmacionClave = confirmacionClave;
    }

    public List<MfsPregunta> getPreguntasSeccion1() {
        return preguntasSeccion1;
    }

    public void setPreguntasSeccion1(List<MfsPregunta> preguntasSeccion1) {
        this.preguntasSeccion1 = preguntasSeccion1;
    }

    public List<MfsPregunta> getPreguntasSeccion2() {
        return preguntasSeccion2;
    }

    public void setPreguntasSeccion2(List<MfsPregunta> preguntasSeccion2) {
        this.preguntasSeccion2 = preguntasSeccion2;
    }

    public Map<MfsPregunta, Integer> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(Map<MfsPregunta, Integer> respuestas) {
        this.respuestas = respuestas;
    }

    public List<FilterOption> getEscala() {
        return escala;
    }

    public void setEscala(List<FilterOption> escala) {
        this.escala = escala;
    }

    public Map<Integer, Map<Integer, String>> getMatriz() {
        return matriz;
    }

    public void setMatriz(Map<Integer, Map<Integer, String>> matriz) {
        this.matriz = matriz;
    }

    public List<Integer> getIndicesFilas() {
        return indicesFilas;
    }

    public void setIndicesFilas(List<Integer> indicesFilas) {
        this.indicesFilas = indicesFilas;
    }

    public List<Integer> getIndicesColumnas() {
        return indicesColumnas;
    }

    public void setIndicesColumnas(List<Integer> indicesColumnas) {
        this.indicesColumnas = indicesColumnas;
    }

    public List<FilterOption> getOpcionesMatriz() {
        return opcionesMatriz;
    }

    public void setOpcionesMatriz(List<FilterOption> opcionesMatriz) {
        this.opcionesMatriz = opcionesMatriz;
    }
}
