package workcenter.util.converters;

import org.springframework.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.servlet.jsp.tagext.TagSupport;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Esta clase nos permitirá definir un estandar de números en el sistema. El
 * estandar definido es "." para separar miles y "," para indicar parte decimal
 *
 * @author Claudio Olivares
 */
@FacesConverter(value = "numberConverter")
public class NumberConverter extends TagSupport implements Converter {

    private String currencySymbol;
    private Boolean groupingUsed;
    private Integer minFractionDigits;
    private Integer maxFractionDigits;
    private String type;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        String strNumber = value;
        if (StringUtils.countOccurrencesOf(strNumber, ",") > 1) {
            FacesMessage msg = new FacesMessage("Solo debe haber un carácter que indique el comienzo de la parte decimal \",\"");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
        strNumber = strNumber.replaceAll("\\.", "");
        strNumber = strNumber.replaceAll(",", ".");
        if (currencySymbol != null && !currencySymbol.isEmpty()) {
            strNumber = strNumber.replace(currencySymbol, "");
        }
        BigDecimal retorno = null;
        try {
            retorno = new BigDecimal(strNumber);
        } catch (NumberFormatException e) {
            FacesMessage msg = new FacesMessage("Número ingresado no es válido: " + strNumber);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }

        if (minFractionDigits != null) {
            retorno = retorno.setScale(minFractionDigits, RoundingMode.HALF_UP);
        }

        if (maxFractionDigits != null) {
            retorno = retorno.setScale(maxFractionDigits, RoundingMode.HALF_UP);
        }

        Class<?> clazz = component.getValueExpression("value").getType(context.getELContext());
        if (BigDecimal.class.equals(clazz)) {
            return retorno;
        } else if (Double.class.equals(clazz)) {
            return Double.valueOf(retorno.doubleValue());
        } else if (Float.class.equals(clazz)) {
            return Float.valueOf(retorno.floatValue());
        } else if (Integer.class.equals(clazz)) {
            return Integer.valueOf(retorno.setScale(0, RoundingMode.HALF_DOWN).intValue());
        } else if (BigInteger.class.equals(clazz)) {
            return BigInteger.valueOf(retorno.setScale(0, RoundingMode.HALF_DOWN).intValue());
        } else if (Long.class.equals(clazz)) {
            return Long.valueOf(retorno.setScale(0, RoundingMode.HALF_DOWN).longValue());
        } else if (String.class.equals(clazz)) {
            return retorno.toString();
        } else {
            FacesMessage msg = new FacesMessage("Clase no soportada por NumberConverter");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }

        if (!Boolean.TRUE.equals(groupingUsed) && minFractionDigits == null && maxFractionDigits == null) {
            return String.valueOf(value).replaceAll("\\.", ",");
        }

        if (minFractionDigits != null && maxFractionDigits != null && minFractionDigits > maxFractionDigits) {
            throw new UnsupportedOperationException("La cantidad mínima de decimales, debe ser menos que la cantidad máxima");
        }

        if (value instanceof BigDecimal) {
            BigDecimal retorno = (BigDecimal) value;
            return bigDecimalToString(retorno);
        } else if (value instanceof Double) {
            Double retorno = (Double) value;
            if (Double.isNaN(retorno)) {
                retorno = (double) 0;
            }
            return bigDecimalToString(BigDecimal.valueOf(retorno));
        } else if (value instanceof Float) {
            Float retorno = (Float) value;
            return bigDecimalToString(BigDecimal.valueOf(retorno));
        } else if (value instanceof Integer) {
            Integer retorno = (Integer) value;
            return bigDecimalToString(BigDecimal.valueOf(retorno));
        } else if (value instanceof BigInteger) {
            BigInteger retorno = (BigInteger) value;
            return bigDecimalToString(new BigDecimal(retorno));
        } else if (value instanceof Long) {
            Long retorno = (Long) value;
            return bigDecimalToString(BigDecimal.valueOf(retorno));
        } else if (value instanceof String) {
            return (String) value;
        } else {
            FacesMessage msg = new FacesMessage("Clase no soportada por NumberConverter");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }
    }

    private String bigDecimalToString(BigDecimal bigDecimal) {
        BigDecimal retorno = null;
        if (minFractionDigits != null) {
            retorno = bigDecimal.setScale(minFractionDigits, RoundingMode.HALF_UP);
        }

        if (maxFractionDigits != null) {
            retorno = bigDecimal.setScale(maxFractionDigits, RoundingMode.HALF_UP);
        }

        if (retorno == null) {
            retorno = bigDecimal;
        }
        
        if ("currency".equals(type)) {
            groupingUsed = Boolean.TRUE;
        } else if ("percent".equals(type)) {
            groupingUsed = Boolean.FALSE;
        }
        
        StringBuilder sb = new StringBuilder();

        if (Boolean.TRUE.equals(groupingUsed)) {
            String numeroStr = String.valueOf(retorno == null ? BigDecimal.ZERO : retorno);
            int index = numeroStr.indexOf(".");
            String parteDecimal = ""; 
            if (index != -1) {
                parteDecimal = ",".concat(numeroStr.split("\\.")[1]);
                numeroStr = numeroStr.split("\\.")[0];
            }   
            int cont = 1;
            for (int i = numeroStr.length() - 1; i >= 0; i--) {
                sb.insert(0, numeroStr.charAt(i));
                if (cont % 3 == 0 && i > 0) {
                    sb.insert(0, '.');
                }   
                cont = (cont + 1) % 3;
            }
            sb.append(parteDecimal).toString();
        } else {
            sb.append(String.valueOf(retorno).replaceAll("\\.", ","));
        }
        
        if (currencySymbol != null && !currencySymbol.isEmpty()) {
            sb.insert(0, currencySymbol);
        }
        return sb.toString();
    }

    public Boolean getGroupingUsed() {
        return groupingUsed;
    }

    public void setGroupingUsed(Boolean groupingUsed) {
        this.groupingUsed = groupingUsed;
    }

    public Integer getMinFractionDigits() {
        return minFractionDigits;
    }

    public void setMinFractionDigits(Integer minFractionDigits) {
        this.minFractionDigits = minFractionDigits;
    }

    public Integer getMaxFractionDigits() {
        return maxFractionDigits;
    }

    public void setMaxFractionDigits(Integer maxFractionDigits) {
        this.maxFractionDigits = maxFractionDigits;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}