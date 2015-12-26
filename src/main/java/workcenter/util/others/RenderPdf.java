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

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;

/**
 * Created by claudio on 25-07-15.
 */
@Component
@Scope("request")
public class RenderPdf implements Serializable {

	private static final long serialVersionUID = 1L;

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

    public String generarLiquidacion(Remuneracion liquidacion, Integer asignacionFamiliarMonto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        
        //obtenemos url + contexto de la aplicación
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        String requestURL = request.getRequestURL().toString();
        String url = requestURL.split("/m")[0];
        
        String fecha = sdf.format(liquidacion.getFechaLiquidacion());
        String path = constantes.getPathArchivos() + "/tmp/" +liquidacion.getIdPersonal().getRut()+"/liquidaciones/"+fecha;
        // nos aseguramos que cree la carpeta
        new File(path).mkdirs();
        path += "/" + liquidacion.getIdPersonal().getRut() + ".pdf";

        // variables a ocupar para generar el pdf
        SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM");
        fecha = sdfa.format(liquidacion.getFechaLiquidacion());
        Document pdf = new Document();
        PdfPTable tabla;
        PdfPCell celda;
        FileOutputStream fileOutputStream;
        PdfWriter pdfWriter;

        try {
            fileOutputStream = new FileOutputStream(path);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            return "";
        }

        // formateamos rut empleador y empleado
        String rutEmpleado = formato.numeroAgrupado(liquidacion.getIdPersonal().getRut()) + "-" + liquidacion.getIdPersonal().getDigitoverificador();
        Empresa e = logicaEmpresas.obtenerEmpresa(Integer.parseInt(liquidacion.getRutEmpleador().split("-")[0]));
        String rutEmpresa = formato.numeroAgrupado(e.getRut()) + "-" + e.getDigitoverificador();
        ContratoPersonal contrato = logicaPersonal.obtenerContratoActual(liquidacion.getIdPersonal());
        ValorPrevisionPersonal valorSalud = logicaPersonal.obtenerValorPrevisionSaludActual(contrato);
        ValorPrevisionPersonal valorAfp = logicaPersonal.obtenerValorPrevisionAfpActual(contrato);
        Prevision salud = valorSalud.getPrevision();
        Prevision afp = valorAfp.getPrevision();

        // cambiamos el patron de fecha al que necesitamos en la liquidación
        sdf.applyPattern("dd/MM/yyyy");

        // empezamos a escribir el pdf
        try {
            pdfWriter = PdfWriter.getInstance(pdf, fileOutputStream);
            pdfWriter.setInitialLeading(20);

            pdf.open();
            
            //agregamos logo en el pdf
            try {
            	
                Image logo = Image.getInstance(new URL(url+"/resources/css/img/theme/logoSuperior.png"));
                logo.setAbsolutePosition(450f, 740f);
                logo.scaleAbsolute(75f, 75f);
                pdf.add(logo);
    		} catch (Exception ex1) {
    			ex1.printStackTrace();
    		}

            // fuentes
            Font fuenteEncabezado = FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLACK);
            Font fuenteTitulo = FontFactory.getFont("arial", 16, Font.BOLD, BaseColor.BLACK);
            Font fuenteCuerpo = FontFactory.getFont("arial", 12, Font.NORMAL, BaseColor.BLACK);
            Font fuenteCuerpoNegrita = FontFactory.getFont("arial", 11, Font.BOLD, BaseColor.BLACK);
            Font fuenteCuerpoAzul = FontFactory.getFont("arial", 11, Font.NORMAL, BaseColor.BLUE);

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
                "\nLiquidación de Sueldo\n",
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

            celda.setPhrase(new Phrase("Nombre Empleado: " + liquidacion.getIdPersonal().getNombreCompleto() + "\n\n", fuenteCuerpo));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("Rut: " + rutEmpleado, fuenteCuerpo));
            tabla.addCell(celda);

            //celda.setPhrase(new Phrase("Fecha: " + sdf.format(liquidacion.getFechaLiquidacion()), fuenteCuerpo));
            celda.setPhrase(new Phrase("Fecha: " +obtenerUltimoDíaMes(fecha) , fuenteCuerpo));
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

            celda.setPhrase(new Phrase("AFP " + afp.getNombre() + ": " + formato.numeroAgrupado(liquidacion.getDectoAFP()), fuenteCuerpo));
            tabla.addCell(celda);
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

                celda.setPhrase(new Phrase("Seguro cesantía (aporte trabajador): " + formato.numeroAgrupado(liquidacion.getAporteTrabajador().intValue()), fuenteCuerpo));
                tabla.addCell(celda);
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
                //celda.setPhrase(new Phrase("Impuesto único: " + formato.numeroAgrupado(Double.parseDouble(liquidacion.getImpUnico().toString().split(".")[0])), fuenteCuerpo));
            	celda.setPhrase(new Phrase("Impuesto único: " + liquidacion.getImpUnico(), fuenteCuerpo));
            	tabla.addCell(celda);
            } else {
                celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            /* ---------------------------------------------------------------- */

            // Bonos imponibles
            for (BonoDescuentoRemuneracion bdr : liquidacion.getRemuneracionBonoDescuentoList()) {
            	if (bdr.getBono()){
            		if (bdr.getImponible()){
            			celda.setPhrase(new Phrase(bdr.getDescripcion() + " : " + formato.numeroAgrupado(Integer.parseInt(bdr.getMonto().toString())), fuenteCuerpo));
                        tabla.addCell(celda);

                        celda.setPhrase(new Phrase("", fuenteCuerpo));
                        tabla.addCell(celda);
            		}
            	}
            }
            
            /* ---------------------------------------------------------------- */
            
            celda.setPhrase(new Phrase("Total imponible: " + formato.numeroAgrupado(liquidacion.getTotalImponible()), fuenteCuerpoNegrita));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("Descuento por viático anticipado: " + formato.numeroAgrupado(liquidacion.getAnticipoViatico()), fuenteCuerpo));
            tabla.addCell(celda);
            /* ---------------------------------------------------------------- */
            
            // Descuentos
            for (BonoDescuentoRemuneracion bdr : liquidacion.getRemuneracionBonoDescuentoList()) {
            	if (!bdr.getBono()){
            		if (!bdr.getImponible()){
            			celda.setPhrase(new Phrase("", fuenteCuerpo));
                        tabla.addCell(celda);
                        
            			celda.setPhrase(new Phrase(bdr.getDescripcion() + " : " + formato.numeroAgrupado(bdr.getMonto() == null ? 0 : bdr.getMonto().intValue()), fuenteCuerpo));
                        tabla.addCell(celda);
            		}
            	}
            }
            
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

            celda.setPhrase(new Phrase("Alcance líquido: " + formato.numeroAgrupado(liquidacion.getAlcanceLiquido()), fuenteCuerpoNegrita));
            tabla.addCell(celda);
            /* ---------------------------------------------------------------- */
            
            // Bonos No Imponibles
            for (BonoDescuentoRemuneracion bdr : liquidacion.getRemuneracionBonoDescuentoList()) {
            	if (bdr.getBono()){
            		if (!bdr.getImponible()){
            			celda.setPhrase(new Phrase(bdr.getDescripcion() + " : " + formato.numeroAgrupado(Integer.parseInt(bdr.getMonto().toString())), fuenteCuerpo));
                        tabla.addCell(celda);

                        celda.setPhrase(new Phrase("", fuenteCuerpo));
                        tabla.addCell(celda);
            		}
            	}
            }
            
            /* ---------------------------------------------------------------- */
            if (asignacionFamiliarMonto != null && asignacionFamiliarMonto.intValue() != 0){
            celda.setPhrase(new Phrase("Asignación Familiar: " + formato.numeroAgrupado(asignacionFamiliarMonto), fuenteCuerpo));
            tabla.addCell(celda);
            }else{
            	celda.setPhrase(new Phrase("", fuenteCuerpo));
                tabla.addCell(celda);
            }
            
            celda.setPhrase(new Phrase("", fuenteCuerpo));
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
            
            celda.setPhrase(new Phrase("\n\n\n\n\n", fuenteCuerpo));
            tabla.addCell(celda);
            celda.setPhrase(new Phrase("\n", fuenteCuerpo));
            tabla.addCell(celda);
            
            /* ---------------------------------------------------------------- */

            celda.setPhrase(new Phrase("Certifico que he recibido a mi entera satisfacción "
                    + "el saldo líquido indicado en la presente liquidación y no tengo "
                    + "cargo ni cobro alguno posterior que hacer, por ninguno de los conceptos "
                    + "comprendidos en ella. Autorizo descuento de asignación de caja.", fuenteCuerpo));
            tabla.addCell(celda);

            celda.setPhrase(new Phrase("\n\n\n______________________", fuenteCuerpo));
            tabla.addCell(celda);
            
            pdf.add(tabla);
            pdf.close();
            
            /* -----------------------documento generado a byte-------------------- */
            
            FileInputStream fileInputStream=null;
            File file = new File(path);            
            byte[] bFile = new byte[(int) file.length()];
            try {
        	    fileInputStream = new FileInputStream(file);
        	    fileInputStream.read(bFile);
   			    liquidacion.setArchivo(bFile);
   			    liquidacion.setExtension("pdf");
   			    liquidacion.setNombreArchivo(liquidacion.getIdPersonal().getRut() + ".pdf");
        	    fileInputStream.close();
        	    return path;
			} catch (Exception e2) {
				e2.printStackTrace();
				return "";
			}
            
        } catch (DocumentException de) {
            System.err.println("No se pudo crear fichero");
            de.printStackTrace();
            return "";
        }

    }
    
    public String obtenerUltimoDíaMes(String a){
    	String mes = a.split("-")[1];
    	String anio = a.split("-")[0];
    	switch(Integer.parseInt(mes)){
    	case 1:  // Enero
    	case 3:  // Marzo
    	case 5:  // Mayo
    	case 7:  // Julio
    	case 8:  // Agosto
    	case 10:  // Octubre
    	case 12: // Diciembre
    	return "31/"+mes+"/"+anio;
    	case 4:  // Abril
    	case 6:  // Junio
    	case 9:  // Septiembre
    	case 11: // Noviembre
    	return "30/"+mes+"/"+anio;
    	case 2:  // Febrero
    	if ( ((Integer.parseInt(anio)%100 == 0) && (Integer.parseInt(anio)%400 == 0)) ||
    	((Integer.parseInt(anio)%100 != 0) && (Integer.parseInt(anio)%  4 == 0))   )
    	return "29/"+mes+"/"+anio;  // Año Bisiesto
    	else
    	return "28"+mes+"/"+anio;
    	}
		return "00/00/0000";
    }
}
