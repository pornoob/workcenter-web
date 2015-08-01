package workcenter.util.others;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Empresa;
import workcenter.entidades.Remuneracion;
import workcenter.negocio.LogicaEmpresas;
import workcenter.util.components.Constantes;
import workcenter.util.components.Formato;
import workcenter.util.components.SesionCliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

/**
 * Created by claudio on 25-07-15.
 */
@Component
@Scope("request")
public class RenderPdf {
    @Autowired
    Constantes constantes;

    @Autowired
    SesionCliente sesionCliente;

    @Autowired
    Formato formato;

    @Autowired
    LogicaEmpresas logicaEmpresas;

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
        Empresa e = logicaEmpresas.obtenerEmpresa(Integer.parseInt(liquidacion.getRutEmpleador()));
        String rutEmpresa = formato.numeroAgrupado(e.getRut()) + "-" + e.getDigitoverificador();

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

            // titulo
            texto = new Paragraph(
                "Liquidación de Sueldo",
                fuenteTitulo
            );
            texto.setAlignment(Element.ALIGN_CENTER);
            pdf.add(texto);

            tabla = new PdfPTable(3); // 3 columnas
            celda = new PdfPCell();
            celda.setBorder(Rectangle.NO_BORDER);

            celda.setPhrase(new Phrase("Nombre Empleado: " + liquidacion.getIdPersonal().getNombreCompleto(), fuenteCuerpo));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("Rut: " + rutEmpleado, fuenteCuerpo));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("Fecha: " + sdf.format(liquidacion.getFechaLiquidacion()), fuenteCuerpo));
            tabla.addCell(celda);

            pdf.add(tabla);
            pdf.close();
        } catch (DocumentException de) {
            de.printStackTrace();
            return false;
        }

        return true;
    }
}
