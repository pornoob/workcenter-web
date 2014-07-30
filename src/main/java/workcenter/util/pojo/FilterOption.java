package workcenter.util.pojo;

import java.io.Serializable;

/**
 *
 * @author colivares
 */
public class FilterOption implements Serializable {
    private Integer value;
    private String label;

    public FilterOption(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}