package cn.rg.cems.manage.controller;

import cn.rg.cems.common.entity.*;
import cn.rg.cems.manage.service.BasicService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {

    @Resource
    private BasicService basicService;

    @RequestMapping("/test")
    public String test() {
        return "test123456";
    }

    @RequestMapping(value = "/staff")
    public PageInfo<Staff> getStaffList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "8") Integer size) {
        List<Staff> list = basicService.getStaffList(page, size);
        return new PageInfo<>(list);
    }

    @RequestMapping(value = "/staff/add", method = RequestMethod.POST)
    public Account addStaffAccount(Account account, Staff staff) {
        // TODO 验证账号数据
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        account.setRoleId(2);
        Integer accountId = basicService.addAccount(account);
        staff.setAccountId(accountId);
        basicService.addStaff(staff);
        return null;
    }

    @RequestMapping(value = "/staff/update", method = RequestMethod.POST)
    public Invigilate updateStaff(@RequestBody Staff staff) {
        if (staff.getId() != null && staff.getName() != null)
            basicService.updateStaff(staff);
        if (staff.getAccount() != null && staff.getAccount().getId() != null) {
            Account account = staff.getAccount();
            if (account.getPassword() != null)
                account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
            basicService.updateAccount(account);
        }
        return null;
    }

    @RequestMapping(value = "/invigilate")
    public PageInfo<Invigilate> getInvigilatesList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                   @RequestParam(required = false, defaultValue = "8") Integer size) {
        List<Invigilate> list = basicService.getInvigilateList(page, size);
        return new PageInfo<>(list);
    }

    @RequestMapping(value = "/invigilate/add", method = RequestMethod.POST)
    public Account addInvigilateAccount(Account account, Invigilate invigilate) {
        // TODO 验证账号数据
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        account.setRoleId(4);
        Integer accountId = basicService.addAccount(account);
        invigilate.setAccountId(accountId);
        basicService.addInvigilate(invigilate);
        return null;
    }

    @RequestMapping(value = "/invigilate/update", method = RequestMethod.POST)
    public Invigilate updateInvigilates(@RequestBody Invigilate invigilate) {
        if (invigilate.getId() != null && invigilate.getName() != null)
            basicService.updateInvigilate(invigilate);
        if (invigilate.getAccount() != null && invigilate.getAccount().getId() != null) {
            Account account = invigilate.getAccount();
            if (account.getPassword() != null)
                account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
            basicService.updateAccount(account);
        }
        return null;
    }


    @RequestMapping(value = "/subject")
    public PageInfo<Subject> getSubjectList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                            @RequestParam(required = false, defaultValue = "8") Integer size,
                                            @RequestParam(required = false) String name) {
        Subject subject = new Subject();
        subject.setName(name);
        List<Subject> subjects = basicService.getSubjectList(subject, page, size);
        return new PageInfo<>(subjects);
    }

    @RequestMapping(value = "/subject/add", method = RequestMethod.POST)
    public Subject addSubject(@RequestBody Subject subject) {
        Integer subjectId = basicService.addSubject(subject);
        basicService.addArrangeTime(subject.getArrangeTimes(), subjectId);
        return null;
    }

    @RequestMapping(value = "/subject/update", method = RequestMethod.POST)
    public Subject updateSubject(@RequestBody Subject subject) {
        System.out.println(JSON.toJSONString(subject));
        basicService.updateSubject(subject);
        if (subject.getArrangeTimes() != null) {
            List<ArrangeTime> temps = new ArrayList<>();
            for (ArrangeTime arrangeTime : subject.getArrangeTimes())
                if (arrangeTime.getId() != null)
                    basicService.updateArrangeTime(arrangeTime);
                else
                    temps.add(arrangeTime);
            if (temps.size() != 0)
                basicService.addArrangeTime(temps, subject.getId());
        }
        return null;
    }

    @RequestMapping(value = "/subject/arrangeTime/delete", method = RequestMethod.POST)
    public Subject deleteArrangeTime(@RequestBody List<ArrangeTime> arrangeTimes) {
        for (ArrangeTime arrangeTime : arrangeTimes)
            basicService.deleteArrangeTime(arrangeTime);
        return null;
    }

    @RequestMapping(value = "/examRoom")
    public PageInfo<ExamRoom> getExamRoomList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                              @RequestParam(required = false, defaultValue = "8") Integer size) {
        List<ExamRoom> examRooms = basicService.getExamRoomList(page, size);
        return new PageInfo<>(examRooms);
    }

    @RequestMapping(value = "/examRoom/add", method = RequestMethod.POST)
    public ExamRoom addExamRoom(@RequestBody ExamRoom examRoom) {
        System.out.println(JSON.toJSONString(examRoom));
        basicService.addExamRoom(examRoom);
        return null;
    }

    @RequestMapping(value = "/examRoom/update", method = RequestMethod.POST)
    public ExamRoom updateExamRoom(@RequestBody ExamRoom examRoom) {
        basicService.updateExamRoom(examRoom);
        return null;
    }

    @RequestMapping(value = "/exam")
    public PageInfo<Exam> getExamList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                      @RequestParam(required = false, defaultValue = "8") Integer size,
                                      @RequestParam(value = "subject_id", required = false) Integer subjectId) {
        List<Exam> exams;
        Exam exam = new Exam();
        if (subjectId != null)
            exam.setSubjectId(subjectId);
        exams = basicService.getExamList(exam, page, size);
        return new PageInfo<>(exams);
    }

    @RequestMapping(value = "/exam/add", method = RequestMethod.POST)
    public Exam addExam(@RequestBody Exam exam) {
        System.out.println(JSON.toJSONString(exam));
        basicService.addExam(exam);
        return null;
    }

    @RequestMapping(value = "/exam/update", method = RequestMethod.POST)
    public Exam updateExam(@RequestBody Exam exam) {
        basicService.updateExam(exam);
        return null;
    }

    @RequestMapping(value = "/arrange")
    public PageInfo<Arrange> getArranngeList(@RequestParam("exam_id") Integer examId,
                                             @RequestParam(required = false, defaultValue = "1") Integer page,
                                             @RequestParam(required = false, defaultValue = "8") Integer size) {
        Arrange arrange = new Arrange();
        arrange.setExamId(examId);
        List<Arrange> arranges = basicService.getArrangeList(arrange, page, size);
        return new PageInfo<>(arranges);
    }

    @RequestMapping(value = "/arrange/add", method = RequestMethod.POST)
    public Exam addArrange(@RequestBody Arrange arrange) {
        basicService.addArrange(arrange);
        return null;
    }

    @RequestMapping(value = "/monitor/add", method = RequestMethod.POST)
    public Monitor addMonitorList(@RequestBody Monitor monitor) {
        basicService.addMonitor(monitor);
        return null;
    }

    @RequestMapping(value = "/signUp")
    public PageInfo<SignUp> getsignUpList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(required = false, defaultValue = "8") Integer size,
                                          @RequestParam("exam_id") Integer examId) {
        SignUp signUp = new SignUp();
        signUp.setExamId(examId);
        List<SignUp> signUpList = basicService.getSignUpList(signUp, page, size);
        return new PageInfo<>(signUpList);
    }

    @RequestMapping("/examinee")
    public Examinee getExaminee(@RequestParam("account_id") Integer accountId) {
        return basicService.getExaminee(accountId);
    }

}
