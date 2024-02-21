package com.amos.cojbackend.judge.codesandbox.impl;

import com.amos.cojbackend.judge.codesandbox.CodeSandBox;
import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
public class ThridPartyCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse exectueCode(ExecuteCodeRequest exceteCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
