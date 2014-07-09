package workcenter.negocio;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import workcenter.dao.DocumentoDao;
import workcenter.entidades.AsociacionDocumento;
import workcenter.entidades.Documento;
import workcenter.entidades.MpaPlanPrograma;

/**
 *
 * @author colivares
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LogicaDocumentos {

    @Autowired
    DocumentoDao documentoDao;

    @Transactional(readOnly = false)
    public void guardarDocumento(Documento d) {
        documentoDao.guardar(d);
    }

    @Transactional(readOnly = true)
    public Documento obtenerPorCodigo(String filtroArchivo) {
        return documentoDao.obtenerDocumentoPorCodigo(filtroArchivo);
    }

    @Transactional(readOnly = false)
    public void asociarDocumentos(List<Documento> docs, Object entidad) {
        Class clase = entidad.getClass();
        System.err.println("LLEGA: "+docs);
        System.err.println("CON: "+entidad);
        for (Documento d : docs) {
            AsociacionDocumento ad = new AsociacionDocumento();
            ad.setIdDocumento(d);
            ad.setNombreTabla(((Table) clase.getAnnotation(Table.class)).name());
            for (Field f : clase.getDeclaredFields()) {
                try {
                    Annotation annotationId = f.getAnnotation(javax.persistence.Id.class);
                    if (annotationId == null) {
                        annotationId = f.getAnnotation(javax.persistence.EmbeddedId.class);
                    }
                    if (annotationId == null) {
                        continue;
                    }
                    String nombreGetter = "get" + f.getName().substring(0, 1).toUpperCase() + (f.getName().length() > 1 ? f.getName().substring(1) : "");
                    Method getterMethod = clase.getMethod(nombreGetter, null);
                    Object objectId = getterMethod.invoke(entidad, null);
                    ad.setIdEnTabla(Integer.valueOf(objectId.toString()));
                    break;
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(LogicaDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(LogicaDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(LogicaDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(LogicaDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(LogicaDocumentos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.err.println("AD: "+ad);
            documentoDao.guardarAsociacion(ad);
            System.err.println("AD2: "+ad);
        }
    }

    @Transactional(readOnly = true)
    public List<Documento> obtenerDocumentosAsociados(Object entidad) {
        if (entidad == null) {
            return new ArrayList<Documento>();
        }
        Class clase = entidad.getClass();
        String nombreTabla = ((Table) clase.getAnnotation(Table.class)).name();
        Object objectId = null;
        for (Field f : clase.getDeclaredFields()) {
            try {
                Annotation annotationId = f.getAnnotation(javax.persistence.Id.class);
                if (annotationId == null) {
                    annotationId = f.getAnnotation(javax.persistence.EmbeddedId.class);
                }
                if (annotationId == null) {
                    continue;
                }
                String nombreGetter = "get" + f.getName().substring(0, 1).toUpperCase() + (f.getName().length() > 1 ? f.getName().substring(1) : "");
                Method getterMethod = clase.getMethod(nombreGetter, null);
                objectId = getterMethod.invoke(entidad, null);
                break;
            } catch (IllegalAccessException ex) {
                Logger.getLogger(LogicaDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(LogicaDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(LogicaDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(LogicaDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(LogicaDocumentos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return documentoDao.obtenerDocumentos(nombreTabla, objectId.toString());
    }

    @Transactional(readOnly = false)
    public void asociarDocumento(Documento d, Object entidad) {
        List<Documento> docs = new ArrayList<Documento>();
        docs.add(d);
        asociarDocumentos(docs, entidad);
    }
}
