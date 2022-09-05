package com.boris1993.timesyncntp.utils;

import com.boris1993.timesyncntp.LocalizationResource;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RrhGenerator {
    private static final List<String> SKIPPING_FIELDS = Arrays.asList("BUNDLE_ID", "BUNDLE_NAME");

    public static void main(String[] args) {
        final Class<LocalizationResource> localizationResourceClass = LocalizationResource.class;

        final List<Field> fields = Arrays.stream(localizationResourceClass.getDeclaredFields())
                .filter(field -> !SKIPPING_FIELDS.contains(field.getName()))
                .collect(Collectors.toList());

        final String packageName = localizationResourceClass.getPackage().getName();

        try (FileWriter fileWriter = new FileWriter(Paths.get(".").toAbsolutePath() + "/resource/Localization.rrh")) {
            fileWriter.write("package " + packageName + ";");
            fileWriter.write("\n\n");

            for (Field field : fields) {
                final String fieldName = field.getName();
                final String fieldValue = field.get(localizationResourceClass).toString();
                final String resourceBundleItem = fieldName + "#0=" + fieldValue + ";";

                System.out.println("Adding " + resourceBundleItem);
                fileWriter.write(resourceBundleItem);
                fileWriter.write("\n");
            }
        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
