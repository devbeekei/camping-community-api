package com.ss.camper.store.ui.payload;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MultipartFileCountValidator implements ConstraintValidator<MultipartFileCountValid, List<MultipartFile>> {

    private static final String ERROR_MESSAGE = "Files too many.";

    private int min = 1;
    private int max = 0;

    @Override
    public void initialize(MultipartFileCountValid constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    private List<MultipartFile> filterMultipartFiles(final List<MultipartFile> values) {
        return values.stream().filter(f -> StringUtils.isNotBlank(f.getOriginalFilename())).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(final List<MultipartFile> values, final ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(ERROR_MESSAGE).addConstraintViolation();
        int fileCount = filterMultipartFiles(values).size();
        System.out.println("isValid : " + (fileCount >= min && (max <= 0 || fileCount <= max)));
        return fileCount >= min && (max <= 0 || fileCount <= max);
    }

}