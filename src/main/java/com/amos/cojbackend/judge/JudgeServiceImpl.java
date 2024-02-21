package com.amos.cojbackend.judge;

import cn.hutool.json.JSONUtil;
import com.amos.cojbackend.common.ErrorCode;
import com.amos.cojbackend.exception.BusinessException;
import com.amos.cojbackend.judge.codesandbox.CodeSandBox;
import com.amos.cojbackend.judge.codesandbox.CodeSandBoxFactory;
import com.amos.cojbackend.judge.codesandbox.CodeSandBoxProxy;
import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeResponse;
import com.amos.cojbackend.judge.strategy.JudgeContext;
import com.amos.cojbackend.model.dto.question.JudgeCase;
import com.amos.cojbackend.model.dto.question.JudgeInfo;
import com.amos.cojbackend.model.entity.Question;
import com.amos.cojbackend.model.entity.QuestionSubmit;
import com.amos.cojbackend.model.enums.QuestionSubmitStatusEnum;
import com.amos.cojbackend.service.QuestionService;
import com.amos.cojbackend.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionService questionService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1)传入题目的提交d,获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目信息不存在");
        }
        // 如果不为等待状态，不进行判题
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中，请稍后再试");
        }
        // 更新状态为判题中
        questionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmit);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新题目提交状态失败");
        }
        //调用沙箱，获取到执行结果
        CodeSandBox codeSandBox = CodeSandBoxFactory.getCodeSandBox(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.exectueCode(executeCodeRequest);
        if (executeCodeResponse == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "代码沙箱执行失败");
        }
        //根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        // 选择判题策略
        JudgeInfo judgeInfo = judgeManager.doJudgeInfo(judgeContext);
        // 修改数据库中的判题结果
        questionSubmit.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmit);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新题目提交状态失败");
        }
        // todo 不理解为什么还要查询一次
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionSubmitId);
        System.out.println("题目提交结果：" + questionSubmitResult);
        return questionSubmitResult;
    }
}
