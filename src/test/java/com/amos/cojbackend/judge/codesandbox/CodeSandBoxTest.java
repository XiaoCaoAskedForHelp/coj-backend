package com.amos.cojbackend.judge.codesandbox;

import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeResponse;
import com.amos.cojbackend.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CodeSandBoxTest {
    @Value("${codesandbox.type:example}")
    private String type;

    @Test
    void exectueCode() {
        CodeSandBox codeSandBox = CodeSandBoxFactory.getCodeSandBox(type);
        String code = "Hello World";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("Hello World", "Hello World");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.exectueCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    @Test
    void exectueCodeByProxy() {
        CodeSandBoxProxy codeSandBoxProxy = new CodeSandBoxProxy(CodeSandBoxFactory.getCodeSandBox(type));
        String code = "Hello World";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("Hello World", "Hello World");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBoxProxy.exectueCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

}