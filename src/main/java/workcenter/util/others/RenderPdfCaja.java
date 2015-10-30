package workcenter.util.others;

import com.itextpdf.text.Document;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Dinero;
import workcenter.util.components.Constantes;
import workcenter.util.components.SesionCliente;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by renholders on 30-10-2015.
 */
@Component
@Scope("request")
public class RenderPdfCaja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private Constantes constantes;

    @Autowired
    private SesionCliente sesionCliente;

    public byte[] generarImpresionCaja(Dinero d) {

        Document pdf = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(pdf,byteArrayOutputStream);
            pdf.open();
            PdfPTable tabla = new PdfPTable(1);
            PdfPCell celda1 = new PdfPCell(new Paragraph(d.getReceptor().getNombreCompleto()));
            tabla.addCell(celda1);
            pdf.add(tabla);
            pdf.close();
        } catch (DocumentException de) {
            System.err.println("No se pudo crear fichero");
            de.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}