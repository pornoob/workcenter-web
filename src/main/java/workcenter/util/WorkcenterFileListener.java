package workcenter.util;

import org.primefaces.event.FileUploadEvent;
import workcenter.util.pojo.Descargable;

/**
 *
 * @author claudio
 */
public interface WorkcenterFileListener {
    public void subir(FileUploadEvent fue);
    public void enlazar(Descargable descargable);
}
