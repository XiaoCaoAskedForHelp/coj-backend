package com.amos.cojbackend.judge.codesandbox.impl;

import com.amos.cojbackend.judge.codesandbox.CodeSandBox;
import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeResponse;
import com.amos.cojbackend.model.dto.question.JudgeInfo;
import com.amos.cojbackend.model.enums.JudgeInfoMessageEnum;
import com.amos.cojbackend.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse exectueCode(ExecuteCodeRequest exceteCodeRequest) {
        List<String> inputList = exceteCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
