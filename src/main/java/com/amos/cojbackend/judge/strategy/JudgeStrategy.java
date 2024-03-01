package com.amos.cojbackend.judge.strategy;

import com.amos.cojbackend.judge.codesandbox.model.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {
    JudgeInfo doJudgeInfo(JudgeContext judgeContext);
}
