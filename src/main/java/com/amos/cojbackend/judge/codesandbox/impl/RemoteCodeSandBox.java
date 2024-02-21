package com.amos.cojbackend.judge.codesandbox.impl;

import com.amos.cojbackend.judge.codesandbox.CodeSandBox;
import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.amos.cojbackend.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse exectueCode(ExecuteCodeRequest exceteCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
