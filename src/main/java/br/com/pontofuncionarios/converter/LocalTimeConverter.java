package br.com.pontofuncionarios.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.faces.convert.Converter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


public class LocalTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        return ((LocalTime) value).format(DateTimeFormatter.ofPattern("HH:mm"));
    }

}

