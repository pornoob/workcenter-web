package workcenter.presentacion.registros.fatiga.somnolencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.negocio.registros.LogicaFatigaSomnolencia;
import workcenter.util.components.Constantes;
import workcenter.util.components.FacesUtil;
import workcenter.util.components.SesionCliente;
import workcenter.util.dto.Semana;
import workcenter.util.pojo.FilterOption;
import workcenter.util.pojo.Md5;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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

    @Autowired
    private SesionCliente sesionCliente;

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
    private Integer respuestaIngresadaMatriz;
    private Integer respuestaCorrectaMatriz;
    private boolean fatigado;
    private List<MfsPregunta> erroresSeccion1;
    private List<Integer> seccionesConError;
    private List<MfsEncuesta> encuestas;
    private Semana semana;
    private Integer prioridad;

    private final static int topeFatiga = 9;

    public void inicio() {
        respuestas = new HashMap<MfsPregunta, Integer>();
        escala = new ArrayList<FilterOption>();
        escala.add(new FilterOption(1, "Nada"));
        escala.add(new FilterOption(2, "Levemente"));
        escala.add(new FilterOption(3, "Medianamente"));
        escala.add(new FilterOption(4, "Bastante"));
        escala.add(new FilterOption(5, "Extremadamente"));
    }

    public String irIndex() {
        return "flowIndex";
    }

    public String irEjecutarEncuesta() {
        return "flowEncuesta";
    }

    public String irListadoEncuesta() {
        semana = semana != null ? semana : new Semana();
        setearFechasSemana(new Date());
        encuestas = logicaFatigaSomnolencia.obtenerEncuestas(semana.getDias()[0].getFecha(), semana.getDias()[6].getFecha());
        return "flowListado";
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
        return "flowEncuestaPaso1";
    }

    public String irPaso1() {
        return "flowEncuestaPaso1";
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
        int cantOpciones = 3;

        matriz = new HashMap<Integer, Map<Integer, String>>();
        indicesFilas = new ArrayList<Integer>();
        indicesColumnas = new ArrayList<Integer>();

        for (int i = 0; i < columnas; i++) indicesColumnas.add(i);

        for (int i = 0; i < filas; i++) {
            indicesFilas.add(i);
            matriz.put(i, new HashMap<Integer, String>());
            for (int j = 0; j < columnas; j++) {
                int r = random.nextInt(2);
                if (r == 1 && enesCreadas <= randomEnes) enesCreadas++;
                if (enesCreadas > randomEnes) matriz.get(i).put(j, caracteres[0]);
                else matriz.get(i).put(j, caracteres[r]);
            }
        }
        enesCreadas--;

        opcionesMatriz = new ArrayList<FilterOption>();
        opcionesMatriz.add(new FilterOption(enesCreadas, String.valueOf(enesCreadas)));
        for (int i=0; i<cantOpciones - 1;) {
            int resp = random.nextInt(maximoEnes) + 1;
            FilterOption fo = new FilterOption(resp, String.valueOf(resp));
            if (!opcionesMatriz.contains(fo)) {
                opcionesMatriz.add(fo);
                i++;
            }
        }
        Collections.sort(opcionesMatriz);
        respuestaCorrectaMatriz = enesCreadas;
        return "flowEncuestaPaso3";
    }

    public String irResultados() {
        Iterator it = respuestas.entrySet().iterator();
        fatigado = false;
        erroresSeccion1 = new ArrayList<MfsPregunta>();
        seccionesConError = new ArrayList<Integer>();

        int sumaEscala = 0;

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            MfsPregunta p = (MfsPregunta) entry.getKey();
            Integer r = Integer.valueOf(entry.getValue().toString());
            if (p.getSeccion().intValue() == 1 && p.getRespuesta().intValue() != r.intValue()) {
                fatigado = true;
                erroresSeccion1.add(p);
            }

            if (p.getSeccion().intValue() == 2) sumaEscala += r.intValue();
        }
        if (fatigado) seccionesConError.add(1);
        if (!fatigado && sumaEscala > topeFatiga) {
            fatigado = true;
            seccionesConError.add(2);
        } else if (sumaEscala > topeFatiga) {
            seccionesConError.add(2);
        }
        if (!fatigado && respuestaCorrectaMatriz.intValue() != respuestaIngresadaMatriz.intValue()) {
            fatigado = true;
            seccionesConError.add(3);
        } else if (respuestaCorrectaMatriz.intValue() != respuestaIngresadaMatriz.intValue()) {
            seccionesConError.add(3);
        }

        // guardamos en la bd el registro
        MfsEncuesta encuesta = new MfsEncuesta();
        encuesta.setEncargado(logicaPersonal.obtener(sesionCliente.getUsuario().getRut()));
        encuesta.setEncuestado(conductor);
        encuesta.setFecha(new Date());

        List<MfsRespuesta> respuestasEncuesta = new ArrayList<MfsRespuesta>();

        it = respuestas.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            MfsRespuesta r = new MfsRespuesta();
            r.setValorRespuesta(Integer.valueOf(entry.getValue().toString()));
            r.setIdPregunta((MfsPregunta) entry.getKey());
            r.setEncuesta(encuesta);
            respuestasEncuesta.add(r);
        }

        List<MfsTest> testsEncuesta = new ArrayList<MfsTest>();
        MfsTest test = new MfsTest();
        test.setIdEncuesta(encuesta);
        test.setValorEntregado(respuestaIngresadaMatriz);
        test.setValorEsperado(respuestaCorrectaMatriz);
        testsEncuesta.add(test);

        encuesta.setRespuestas(respuestasEncuesta);
        encuesta.setTests(testsEncuesta);
        logicaFatigaSomnolencia.guardar(encuesta);

        return "flowEncuestaResultados";
    }

    public void obtenerSemanaAnterior() {
        Calendar c = Calendar.getInstance();
        c.setTime(semana.getDias()[0].getFecha());
        c.add(Calendar.DAY_OF_MONTH, -7);
        setearFechasSemana(c.getTime());
        encuestas = logicaFatigaSomnolencia.obtenerEncuestas(semana.getDias()[0].getFecha(), semana.getDias()[6].getFecha());
    }

    public void obtenerSemanaSiguiente() {
        Calendar c = Calendar.getInstance();
        c.setTime(semana.getDias()[0].getFecha());
        c.add(Calendar.DAY_OF_MONTH, 7);
        setearFechasSemana(c.getTime());
        encuestas = logicaFatigaSomnolencia.obtenerEncuestas(semana.getDias()[0].getFecha(), semana.getDias()[6].getFecha());
    }

    public String semaforo(Date fecha, MfsEncuesta e) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        if (!sdf.format(fecha).equals(sdf.format(e.getFecha()))) return null;
        int sumaEscala = 0;
        for (MfsRespuesta r : logicaFatigaSomnolencia.obtenerRespuestas(e)) {
            if (r.getIdPregunta().getSeccion().intValue() == 1) {
                if (r.getIdPregunta().getRespuesta().intValue() != r.getValorRespuesta().intValue()) return "R";
            } else if (r.getIdPregunta().getSeccion().intValue() == 2) {
                sumaEscala += r.getValorRespuesta().intValue();
            }
        }
        if (sumaEscala > topeFatiga) return "R";
        for (MfsTest t : logicaFatigaSomnolencia.obtenerTests(e)) {
            if (t.getValorEntregado().intValue() != t.getValorEsperado().intValue()) return "R";
        }
        if (sumaEscala > 0) return "A";
        return "V";
    }


    public Integer obtenerPriority() {
        if (prioridad == null) prioridad = 0;
        return (prioridad++ % 2) + 1;
    }

    public boolean seccionConError(Integer seccion) {
        for (Integer i : seccionesConError) {
            if (i.intValue() == seccion.intValue()) return true;
        }
        return false;
    }

    public boolean esNo(Integer valor) {
        return valor.intValue() == 0;
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
//            System.err.println("P: "+entry.getKey());
//            System.err.println("R: "+entry.getValue());
            if (((Integer) entry.getValue()).intValue() == 1) continue;
            int random = randomizer.nextInt(((Integer) entry.getValue()).intValue());
//            System.err.println("RANDOM: "+random);
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

    private void setearFechasSemana(Date fecha) {
        List<Date> fechasSemana = new ArrayList<Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        fechasSemana.add(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        fechasSemana.add(c.getTime());

        for (int i = 0; i < semana.getDias().length; i++) {
            semana.getDias()[i].setFecha(fechasSemana.get(i));
        }
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

    public boolean isFatigado() {
        return fatigado;
    }

    public void setFatigado(boolean fatigado) {
        this.fatigado = fatigado;
    }

    public Integer getRespuestaIngresadaMatriz() {
        return respuestaIngresadaMatriz;
    }

    public void setRespuestaIngresadaMatriz(Integer respuestaIngresadaMatriz) {
        this.respuestaIngresadaMatriz = respuestaIngresadaMatriz;
    }

    public Integer getRespuestaCorrectaMatriz() {
        return respuestaCorrectaMatriz;
    }

    public void setRespuestaCorrectaMatriz(Integer respuestaCorrectaMatriz) {
        this.respuestaCorrectaMatriz = respuestaCorrectaMatriz;
    }

    public List<MfsPregunta> getErroresSeccion1() {
        return erroresSeccion1;
    }

    public void setErroresSeccion1(List<MfsPregunta> erroresSeccion1) {
        this.erroresSeccion1 = erroresSeccion1;
    }

    public List<Integer> getSeccionesConError() {
        return seccionesConError;
    }

    public void setSeccionesConError(List<Integer> seccionesConError) {
        this.seccionesConError = seccionesConError;
    }

    public Semana getSemana() {
        return semana;
    }

    public void setSemana(Semana semana) {
        this.semana = semana;
    }

    public List<MfsEncuesta> getEncuestas() {
        return encuestas;
    }

    public void setEncuestas(List<MfsEncuesta> encuestas) {
        this.encuestas = encuestas;
    }
}
