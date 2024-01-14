package com.demo.invite;

import com.example.invite.dto.TempMemberDTO;
import com.example.invite.service.TempMemberService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
@RequiredArgsConstructor
public class BeanValidationTest {

    private TempMemberService service;

    @Test
    void beanValidation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        TempMemberDTO dto = new TempMemberDTO();
        dto.setName(" ");

        Set<ConstraintViolation<TempMemberDTO>> violations = validator.validate(dto);
        for(ConstraintViolation<TempMemberDTO> violation : violations){
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage());
        }
        if(service == null){
            System.out.println("hi");
        }else {
            System.out.println("Illegal");
            service.check(dto);
        }
    }
}
