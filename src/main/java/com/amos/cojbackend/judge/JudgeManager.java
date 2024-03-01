package com.amos.cojbackend.judge;

import com.amos.cojbackend.judge.strategy.DefaultJudgeStrategy;
import com.amos.cojbackend.judge.strategy.JavaLanguageJudgeStrategy;
import com.amos.cojbackend.judge.strategy.JudgeContext;
import com.amos.cojbackend.judge.strategy.JudgeStrategy;
import com.amos.cojbackend.judge.codesandbox.model.JudgeInfo;
import com.amos.cojbackend.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理简化调用
 */
@Service
public class JudgeManager {

    /**
     * 判题（算是一种将策略选择抽离的方式）
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudgeInfo(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudgeInfo(judgeContext);
    }
}
