package workcenter.util.others;

import com.itextpdf.text.Document;
import com.itextpdf.text.*;
import com.itextpdf.text.log.LoggerFactory;
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
import workcenter.util.components.FacesUtil;
import workcenter.util.components.SesionCliente;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;

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

//            Titulo del pdf
            Font fuenteTitulo = FontFactory.getFont("arial", 16, Font.BOLD, BaseColor.BLACK);
            Font fuenteCuerpo = FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK);
            Font fuenteCuerpoNegrita = FontFactory.getFont("arial", 11, Font.BOLD, BaseColor.BLACK);
            SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM-dd");
            String variableConcepto;

            //Intanciamos un objeto tabla con 2 columnas
            PdfPTable tabla = new PdfPTable(2);
            //titulo
            Paragraph texto;
            if (!d.getConcepto().getSalida()){
                texto = new Paragraph("Comprobante de Ingreso N°"+d.getId()+"\n\n\n",fuenteTitulo);
                variableConcepto = "Recibido";
            }else{
                texto = new Paragraph("Comprobante de Egreso N°"+d.getId()+"\n\n\n",fuenteTitulo);
                variableConcepto = "Entregado";
            }

            PdfPCell celda0 = new PdfPCell();
            celda0.setColspan(2);
            celda0.setPhrase(texto);
            celda0.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda0.setBorder(Rectangle.BOTTOM);
            tabla.addCell(celda0);

//           --------------------------------- fila 1 detalle
            String textoConcepto = "Concepto : "+d.getConcepto().getEtiqueta();
            if (d.getConcepto().getId().intValue() == constantes.getASIGNACION_CONDUCTORES()){
                textoConcepto = textoConcepto.concat(" Orden Carga: " + d.getOrdendecarga());
            }
            texto = new Paragraph(textoConcepto,fuenteCuerpo);
            PdfPCell celda1 = new PdfPCell(texto);
            celda1.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
            celda1.setColspan(2);
            tabla.addCell(celda1);

//           --------------------------------- fila 2
            PdfPCell celda3 = new PdfPCell(new Paragraph("Fecha : "+sdfa.format(d.getFechaactivo()),fuenteCuerpo));
            celda3.setBorder(Rectangle.LEFT);
            tabla.addCell(celda3);

            Paragraph monto = new Paragraph("Monto "+variableConcepto+" :$"+d.getMonto(),fuenteCuerpo);
            monto.setAlignment(Element.ALIGN_RIGHT);
            PdfPCell celda4 = new PdfPCell(monto);
            celda4.setBorder(Rectangle.RIGHT);
            tabla.addCell(celda4);

//           --------------------------------- fila 3

            PdfPCell celda7 = new PdfPCell( !d.getConcepto().getSalida() ?
                    new Paragraph(variableConcepto +" a : ",fuenteCuerpo)
                    :new Paragraph(variableConcepto+" de : ",fuenteCuerpo));
            celda7.setBorder(Rectangle.LEFT);
            tabla.addCell(celda7);

            PdfPCell celda8 = new PdfPCell(new Paragraph(""));
            celda8.setBorder(Rectangle.RIGHT);
            tabla.addCell(celda8);

//            ---------------------------------fila 4
            PdfPCell celda9 = new PdfPCell(new Paragraph("Rut : "+d.getReceptor().getRut()
                    +"-"+d.getReceptor().getDigitoverificador(),fuenteCuerpo));
            celda9.setBorder(Rectangle.LEFT);
            tabla.addCell(celda9);

            PdfPCell celda10 = new PdfPCell(new Paragraph(""));
            celda10.setBorder(Rectangle.RIGHT);
            tabla.addCell(celda10);

//           --------------------------------- fila 5
            PdfPCell celda11 = new PdfPCell(new Paragraph("Nombre : "+d.getReceptor().getNombreCompleto(),fuenteCuerpo));
            celda11.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
            tabla.addCell(celda11);

            PdfPCell celda12 = new PdfPCell(new Paragraph("\n"));
            celda12.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
            tabla.addCell(celda12);

//          -----------------------------------fila 7

            PdfPCell celda13 = new PdfPCell(new Paragraph("Dinero "+variableConcepto+" por : "+
                    sesionCliente.getUsuario().getNombres()+" "+
                    sesionCliente.getUsuario().getApellidos()+"\n",fuenteCuerpo));
            celda13.setBorder(Rectangle.NO_BORDER);
            celda13.setColspan(2);
            tabla.addCell(celda13);

//          ---------------------------------  fila 9
            PdfPCell celda16 = new PdfPCell(new Paragraph("\n\n"+"Comentario : "+d.getComentario(),fuenteCuerpo));
            celda16.setBorder(Rectangle.NO_BORDER);
            celda16.setColspan(2);
            tabla.addCell(celda16);

//          ------------------------------------fila 10
            PdfPCell celda17 = new PdfPCell(new Paragraph("\n\n\n\n"+"_____________________________________________________"));
            celda17.setBorder(Rectangle.NO_BORDER);
            celda17.setColspan(2);
            celda17.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(celda17);

            PdfPCell celda18 = new PdfPCell(new Paragraph("Firma y Nombre de quien  recepciona"+"\n\n\n\n"));
            celda18.setBorder(Rectangle.NO_BORDER);
            celda18.setColspan(2);
            celda18.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(celda18);
            pdf.add(tabla);
            pdf.add(tabla);


            pdf.close();
        } catch (DocumentException de) {
            System.err.println("No se pudo crear fichero");
            de.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}