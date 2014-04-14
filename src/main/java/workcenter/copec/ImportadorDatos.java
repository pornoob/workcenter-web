package workcenter.copec;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.scheduling.annotation.Scheduled;
import workcenter.entidades.RendimientoCopec;

/**
 * @author colivares
 */
public class ImportadorDatos {

    private static final String loginUrl = "https://www.copec.cl/pkmslogin.form";
    private static final String rendimientoDiarioUrl = "https://www.copec.cl/wseext/CopecWebPortalTaeTct/ExcelServlet";
    private static Date ultimaActualizacion = null;

    @Scheduled(cron = "1 0 0 * * *")
    public void obtener() throws IOException, ParseException {
        HttpClient cliente = new HttpClient();
        PostMethod post = new PostMethod(loginUrl);
        GetMethod get = new GetMethod(rendimientoDiarioUrl);

        NameValuePair[] parametros = {
            new NameValuePair("username", "77293410"), new NameValuePair("password", "28301"), new NameValuePair("login-form-type", "pwd")
        };
        post.addParameters(parametros);
        cliente.executeMethod(post);
        System.out.println(post.getResponseBodyAsString());

        parametros = new NameValuePair[]{
            new NameValuePair("tipoArchivo", "rendimientos"), new NameValuePair("nameFile", "InformeRendimientosDiario"), new NameValuePair("tipo", "0"), new NameValuePair("fc_ini", "01-03-2014"), new NameValuePair("fc_fin", "27-03-2014")
        };
        get.setQueryString(parametros);
        cliente.executeMethod(get);
        HSSFWorkbook wb = new HSSFWorkbook(get.getResponseBodyAsStream());
        HSSFSheet ws = wb.getSheet("Hoja1");
        int numRow = 0;
        HSSFRow row;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        while ((row = ws.getRow(numRow++)) != null) {
            RendimientoCopec rc = new RendimientoCopec();
            rc.setDepartamento(Integer.valueOf(row.getCell(0).getStringCellValue()));
            rc.setPatente(row.getCell(1).getStringCellValue());
            rc.setVehiculo(Integer.valueOf(row.getCell(2).getStringCellValue()));
            rc.setTarjeta(row.getCell(3).getStringCellValue());
            rc.setFecha(sdf.parse(row.getCell(4).getStringCellValue()+" "+row.getCell(5).getStringCellValue()));
            rc.setEstacionDeServicio(row.getCell(6).getStringCellValue());
            rc.setGuiaDespacho(Integer.valueOf(row.getCell(7).getStringCellValue()));
            rc.setPrecio(Integer.valueOf(row.getCell(8).getStringCellValue()));
            rc.setLitros(Float.valueOf(row.getCell(9).getStringCellValue()));
            rc.setMonto(Integer.valueOf(row.getCell(10).getStringCellValue()));
            rc.setOdometro(Integer.valueOf(row.getCell(11).getStringCellValue()));
            rc.setKmLt(Float.valueOf(row.getCell(12).getStringCellValue()));
            System.out.println("RC: "+rc.getTarjeta());
        }
    }
}
