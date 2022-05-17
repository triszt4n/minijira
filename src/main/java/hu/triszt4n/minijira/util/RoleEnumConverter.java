package hu.triszt4n.minijira.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleEnumConverter implements AttributeConverter<RoleEnum, String> {

    @Override
    public String convertToDatabaseColumn(RoleEnum category) {
        if (category == null) {
            return null;
        }
        return category.getRole();
    }

    @Override
    public RoleEnum convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(RoleEnum.values())
                .filter(c -> c.getRole().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}