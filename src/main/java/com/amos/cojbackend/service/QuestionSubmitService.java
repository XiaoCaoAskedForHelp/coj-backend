package com.amos.cojbackend.service;

import com.amos.cojbackend.model.dto.question.QuestionQueryRequest;
import com.amos.cojbackend.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.amos.cojbackend.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.amos.cojbackend.model.entity.Question;
import com.amos.cojbackend.model.entity.QuestionSubmit;
import com.amos.cojbackend.model.entity.User;
import com.amos.cojbackend.model.vo.QuestionSubmitVO;
import com.amos.cojbackend.model.vo.QuestionVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author amosc
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-02-14 17:47:15
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目提交封装
     *
     * @param questionSubmit
     * @param request
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, HttpServletRequest request);

    /**
     * 分页获取题目提交封装
     *
     * @param questionSubmitPage
     * @param request
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request);
}
