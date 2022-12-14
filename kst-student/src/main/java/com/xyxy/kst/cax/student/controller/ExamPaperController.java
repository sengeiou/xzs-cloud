package com.xyxy.kst.cax.student.controller;

import com.xyxy.kst.cax.entity.ExamPaper;
import com.xyxy.kst.cax.result.Result;
import com.xyxy.kst.cax.student.service.ExamPaperService;
import com.xyxy.kst.cax.utils.JsonUtil;
import com.xyxy.kst.cax.viewmodel.admin.exammodel.ExamPaperTitleItemVM;
import com.xyxy.kst.cax.viewmodel.admin.exammodel.ExamVM;
import com.xyxy.kst.cax.viewmodel.admin.question.QuestionEditRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author 曹阿鑫
 * @Date 2022/7/7/19:14
 */
@RestController
@RequestMapping("/api/student")
public class ExamPaperController {

    @Autowired
    private ExamPaperService examPaperService;

    /**
     * 查询固定试卷和时段试卷
     * @return
     */
    @PostMapping("/dashboard/index/{id}")
    public Result selectExamPaper(@PathVariable Integer id){
        Result result = examPaperService.selectExamPaper(id);
        return result;
    }


    @PostMapping("/exam/paper/select/{id}")
    public Result selectExamPaperById(@PathVariable Integer id){
        ExamPaper examPaper = examPaperService.getById(id);
        ExamVM examVM = JsonUtil.toJsonObject(JsonUtil.toJsonStr(examPaper), ExamVM.class);
        List<ExamPaperTitleItemVM> examPaperTitleItemVMS = JsonUtil.toJsonListObject(examPaper.getTextContent(), ExamPaperTitleItemVM.class);
        int i = 1;
        for (ExamPaperTitleItemVM examPaperTitleItemVM : examPaperTitleItemVMS){
            List<QuestionEditRequestVM> questionItems = examPaperTitleItemVM.getQuestionItems();
            for (QuestionEditRequestVM questionEditRequestVM : questionItems){
                questionEditRequestVM.setItemOrder(i++);
            }
        }
        examVM.setTitleItems(examPaperTitleItemVMS);
        return Result.ok(examVM);
    }

    @PostMapping("/exam/paper/pageList")
    public Result selectExamPaperPage(@RequestBody ExamVM examVM){
        Map<String, Object> map = examPaperService.selectExamPaperPage(examVM);
        return Result.ok(map);
    }

}
