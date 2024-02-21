package com.amos.cojbackend.judge.codesandbox;

import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱
 */
public interface CodeSandBox {
    /**
     * 执行代码
     *
     * @param exceteCodeRequest
     * @return
     */
    ExecuteCodeResponse exectueCode(ExecuteCodeRequest exceteCodeRequest);
}
