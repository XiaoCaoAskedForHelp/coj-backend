package com.amos.cojbackend.judge.strategy;

import com.amos.cojbackend.model.dto.question.JudgeCase;
import com.amos.cojbackend.judge.codesandbox.model.JudgeInfo;
import com.amos.cojbackend.model.entity.Question;
import com.amos.cojbackend.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定于在策略中传递的参数）
 */
@Data
public class JudgeContext {
    JudgeInfo judgeInfo;

    private List<JudgeCase> judgeCaseList;

    private List<String> outputList;

    private Question question;

    private QuestionSubmit questionSubmit;

}
