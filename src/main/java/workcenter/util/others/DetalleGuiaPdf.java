package workcenter.util.others;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import workcenter.entidades.Personal;
import workcenter.entidades.Vuelta;
import workcenter.util.components.Formato;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
@Scope("application")
public class DetalleGuiaPdf {

    @Autowired
    private Formato formato;

    public byte[] render(Personal persona, List<Vuelta> vueltas) {
        Document pdf = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(pdf,byteArrayOutputStream);
            pdfWriter.setInitialLeading(20);
            pdf.open();

            Font fuenteEncabezado = FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLACK);
            Font fuenteTitulo = FontFactory.getFont("arial", 16, Font.BOLD, BaseColor.BLACK);
            Font fuenteCuerpo = FontFactory.getFont("arial", 12, Font.NORMAL, BaseColor.BLACK);

            Image logo = Image.getInstance(new URL("http://192.168.0.106:8080/workcenter/resources/css/img/theme/logoSuperior.png"));
            logo.setAbsolutePosition(450f, 740f);
            logo.scaleAbsolute(75f, 75f);
            pdf.add(logo);

            String rutEmpleado = formato.numeroAgrupado(persona.getRut()) + "-" + persona.getDigitoverificador();

            Paragraph texto = new Paragraph(
                    "\nDetalle de Guias\n\n",
                    fuenteTitulo
            );
            texto.setAlignment(Element.ALIGN_CENTER);
            pdf.add(texto);

            PdfPTable tabla = new PdfPTable(3); // 3 columnas
            PdfPCell celda = new PdfPCell();


            pdf.close();
        } catch (DocumentException | IOException e) {
            return null;
        }
        return byteArrayOutputStream.toByteArray();
    }
}
