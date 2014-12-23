package workcenter.util.pojo;

import java.io.Serializable;

/**
 *
 * @author colivares
 */
public class FilterOption implements Serializable, Comparable {
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

    @Override
    public int compareTo(Object o) {
        return this.value.compareTo(((FilterOption)o).getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilterOption)) return false;

        FilterOption that = (FilterOption) o;

        if (value == null || that.value == null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}