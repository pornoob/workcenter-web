package workcenter.util.others;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.*;
import workcenter.negocio.LogicaEmpresas;
import workcenter.negocio.personal.LogicaPersonal;
import workcenter.util.components.Constantes;
import workcenter.util.components.Formato;
import workcenter.util.components.SesionCliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by claudio on 25-07-15.
 */
@Component
@Scope("request")
public class RenderPdf implements Serializable {
    @Autowired
    private Constantes constantes;

    @Autowired
    private SesionCliente sesionCliente;

    @Autowired
    private Formato formato;

    @Autowired
    private LogicaEmpresas logicaEmpresas;

    @Autowired
    private LogicaPersonal logicaPersonal;

    public boolean generarLiquidacion(Remuneracion liquidacion) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

        String fecha = sdf.format(liquidacion.getFechaLiquidacion());
        String path = constantes.getPathArchivos() + "/tmp/" + sesionCliente.getUsuario().getRut()+"/liquidaciones/"+fecha;

        // nos aseguramos que cree la carpeta
        new File(path).mkdirs();
        path += "/" + liquidacion.getIdPersonal().getRut() + ".pdf";

        // variables a ocupar para generar el pdf
        Document pdf = new Document();
        PdfPTable tabla;
        PdfPCell celda;
        FileOutputStream fileOutputStream;
        PdfWriter pdfWriter;

        try {
            fileOutputStream = new FileOutputStream(path);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            return false;
        }

        // formateamos rut empleador y empleado
        String rutEmpleado = formato.numeroAgrupado(liquidacion.getIdPersonal().getRut()) + "-" + liquidacion.getIdPersonal().getDigitoverificador();
        Empresa e = logicaEmpresas.obtenerEmpresa(Integer.parseInt(liquidacion.getRutEmpleador().split("-")[0]));
        String rutEmpresa = formato.numeroAgrupado(e.getRut()) + "-" + e.getDigitoverificador();
        ContratoPersonal contrato = logicaPersonal.obtenerContratoActual(liquidacion.getIdPersonal());
        Prevision salud = null;
        Prevision afp = null;

        for (PrevisionContrato p : contrato.getPrevisiones()) {
            if ("salud".equalsIgnoreCase(p.getPrevision().getTipo()))
                salud = p.getPrevision();
            else
                afp = p.getPrevision();
        }

        // cambiamos el patron de fecha al que necesitamos en la liquidación
        sdf.applyPattern("dd/MM/yyyy");

        // empezamos a escribir el pdf
        try {
            pdfWriter = PdfWriter.getInstance(pdf, fileOutputStream);
            pdfWriter.setInitialLeading(20);

            pdf.open();

            // fuentes
            Font fuenteEncabezado = FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLACK);
            Font fuenteTitulo = FontFactory.getFont("arial", 16, Font.BOLD, BaseColor.BLACK);
            Font fuenteCuerpo = FontFactory.getFont("arial", 9, Font.NORMAL, BaseColor.BLACK);
            Font fuenteCuerpoNegrita = FontFactory.getFont("arial", 9, Font.BOLD, BaseColor.BLACK);
            Font fuenteCuerpoAzul = FontFactory.getFont("arial", 9, Font.NORMAL, BaseColor.BLUE);

            // encabezado
            Paragraph texto = new Paragraph(
                liquidacion.getEmpleador() + " - " + rutEmpresa,
                fuenteEncabezado
            );
            texto.setAlignment(Element.ALIGN_LEFT);
            pdf.add(texto);

            texto = new Paragraph(
                "",
                fuenteEncabezado
            );
            texto.setAlignment(Element.ALIGN_LEFT);
            pdf.add(texto);

            // titulo
            texto = new Paragraph(
                "Liquidación de Sueldo",
                fuenteTitulo
            );
            texto.setAlignment(Element.ALIGN_CENTER);
            pdf.add(texto);

            texto = new Paragraph(
                "\n",
                fuenteTitulo
            );
            texto.setAlignment(Element.ALIGN_CENTER);
            pdf.add(texto);

            tabla = new PdfPTable(3); // 3 columnas
            celda = new PdfPCell();
            celda.setBorder(Rectangle.NO_BORDER);

            /* ---------------------------------------------------------------- */

            celda.setPhrase(new Phrase("Nombre Empleado: " + liquidacion.getIdPersonal().getNombreCompleto(), fuenteCuerpo));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("Rut: " + rutEmpleado, fuenteCuerpo));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("Fecha: " + sdf.format(liquidacion.getFechaLiquidacion()), fuenteCuerpo));
            tabla.addCell(celda);

            /* ---------------------------------------------------------------- */

            celda.setPhrase(new Phrase("Días trabajados: " + liquidacion.getDiasTrabajados(), fuenteCuerpo));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("", fuenteCuerpo));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("", fuenteCuerpo));
            tabla.addCell(celda);

            /* ---------------------------------------------------------------- */

            pdf.add(tabla);
            tabla = new PdfPTable(2);

            celda.setPhrase(new Phrase("Sueldo base: " + formato.numeroAgrupado(liquidacion.getSueldoBase()), fuenteCuerpo));
            tabla.addCell(celda);

            if (liquidacion.getDectoAFP().intValue() != 0) {
                celda.setPhrase(new Phrase("AFP " + afp.getNombre() + ": " + formato.numeroAgrupado(liquidacion.getDectoAFP()), fuenteCuerpo));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            /* ---------------------------------------------------------------- */

            if (liquidacion.getGratificacion().intValue() != 0) {
                celda.setPhrase(new Phrase("Gratificación : " + formato.numeroAgrupado(liquidacion.getGratificacion()), fuenteCuerpo));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }

            if (liquidacion.getDctoPrevision().intValue() != 0) {
                celda.setPhrase(new Phrase("Salud " + salud.getNombre() + ": " + formato.numeroAgrupado(liquidacion.getDctoPrevision()), fuenteCuerpo));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            /* ---------------------------------------------------------------- */

            celda.setPhrase(new Phrase("", fuenteCuerpo));
            tabla.addCell(celda);

            if (liquidacion.getAporteTrabajador().intValue() != 0) {
                celda.setPhrase(new Phrase("Seguro cesantía (aporte trabajador): " + formato.numeroAgrupado(liquidacion.getAporteTrabajador().intValue()), fuenteCuerpo));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            /* ---------------------------------------------------------------- */

            celda.setPhrase(new Phrase("", fuenteCuerpo));
            tabla.addCell(celda);

            if (liquidacion.getAporteEmpresa().intValue() != 0) {
                celda.setPhrase(new Phrase("Seguro cesantía (aporte empresa): " + formato.numeroAgrupado(liquidacion.getAporteEmpresa().intValue()), fuenteCuerpo));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            /* ---------------------------------------------------------------- */

            if (liquidacion.getHorasExtras().intValue() != 0) {
                celda.setPhrase(new Phrase("Horas extras: " + formato.numeroAgrupado(liquidacion.getHorasExtras()), fuenteCuerpo));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }

            if (liquidacion.getRentaAfecta().intValue() != 0) {
                celda.setPhrase(new Phrase("Renta afecta: " + formato.numeroAgrupado(liquidacion.getRentaAfecta()), fuenteCuerpoNegrita));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            /* ---------------------------------------------------------------- */

            celda.setPhrase(new Phrase("", fuenteCuerpo));
            tabla.addCell(celda);

            if (liquidacion.getImpUnico().intValue() != 0) {
                celda.setPhrase(new Phrase("Impuesto único: " + formato.numeroAgrupado(liquidacion.getImpUnico()), fuenteCuerpo));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            /* ---------------------------------------------------------------- */

            // falta bonos
//            for (BonoDescuentoRemuneracion bdr : liquidacion.getRemuneracionBonoDescuentoList()) {
//            }

            celda.setPhrase(new Phrase("Total imponible: " + formato.numeroAgrupado(liquidacion.getTotalImponible()), fuenteCuerpoNegrita));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("Descuento por viático anticipado: " + formato.numeroAgrupado(liquidacion.getAnticipoViatico()), fuenteCuerpo));
            tabla.addCell(celda);
            /* ---------------------------------------------------------------- */

            celda.setPhrase(new Phrase("", fuenteCuerpo));
            tabla.addCell(celda);

            if (liquidacion.getTotalDctos() != null && liquidacion.getTotalDctos().intValue() != 0) {
                celda.setPhrase(new Phrase("Total descuentos: " + formato.numeroAgrupado(liquidacion.getTotalDctos()), fuenteCuerpoNegrita));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            /* ---------------------------------------------------------------- */

            celda.setPhrase(new Phrase("", fuenteCuerpo));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("Alcance líquido: " + formato.numeroAgrupado(liquidacion.getTotalDctos()), fuenteCuerpoNegrita));
            tabla.addCell(celda);
            /* ---------------------------------------------------------------- */

            celda.setPhrase(new Phrase("Total haberes: " + formato.numeroAgrupado(liquidacion.getTotalHaberes()), fuenteCuerpoNegrita));
            tabla.addCell(celda);

            if (liquidacion.getAnticipoSueldo() != null && liquidacion.getAnticipoSueldo().intValue() != 0) {
                celda.setPhrase(new Phrase("- Anticipo de sueldo: " + formato.numeroAgrupado(liquidacion.getAnticipoSueldo()), fuenteCuerpo));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            /* ---------------------------------------------------------------- */

            celda.setPhrase(new Phrase("", fuenteCuerpo));
            tabla.addCell(celda);

            if (liquidacion.getPagoPrestamo() != null && liquidacion.getPagoPrestamo().intValue() != 0) {
                celda.setPhrase(new Phrase("- Pago de prestamos: " + formato.numeroAgrupado(liquidacion.getPagoPrestamo()), fuenteCuerpo));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            /* ---------------------------------------------------------------- */
            celda.setPhrase(new Phrase("", fuenteCuerpo));
            tabla.addCell(celda);

            if (liquidacion.getLiqPagar() != null && liquidacion.getLiqPagar().intValue() != 0) {
                celda.setPhrase(new Phrase("= Líquido a pagar: " + formato.numeroAgrupado(liquidacion.getLiqPagar()), fuenteCuerpoNegrita));
                tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            /* ---------------------------------------------------------------- */

            celda.setPhrase(new Phrase("Certifico que he recibido a mi entera satisfacción "
                    + "el saldo líquido indicado en la presente liquidación y no tengo "
                    + "cargo ni cobro alguno posterior que hacer, por ninguno de los conceptos "
                    + "comprendidos en ella. Autorizo descuento de asignación de caja.", fuenteCuerpo));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("______________________", fuenteCuerpo));
            tabla.addCell(celda);

            pdf.add(tabla);
            pdf.close();
        } catch (DocumentException de) {
            System.err.println("No se pudo crear fichero");
            de.printStackTrace();
            return false;
        }

        return true;
    }
}
