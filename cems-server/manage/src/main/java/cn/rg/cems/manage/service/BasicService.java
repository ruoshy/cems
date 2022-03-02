package cn.rg.cems.manage.service;

import cn.rg.cems.common.entity.*;
import cn.rg.cems.manage.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BasicService {

    @Resource
    private AccountMapper accountMapper;
    @Resource
    private InvigilateMapper invigilateMapper;
    @Resource
    private StaffMapper staffMapper;
    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private ArrangeTimeMapper arrangeTimeMapper;
    @Resource
    private ExamRoomMapper examRoomMapper;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private ArrangeMapper arrangeMapper;
    @Resource
    private MonitorMapper monitorMapper;
    @Resource
    private SignUpMapper signUpMapper;
    @Resource
    private ExamineeMapper examineeMapper;

    /**
     * 添加账号信息
     *
     * @param account 账号信息
     * @return 账号id
     */
    public Integer addAccount(Account account) {
        accountMapper.addItem(account);
        return account.getId();
    }

    /**
     * 更新账号信息
     *
     * @param account 账号信息
     * @return 受影响行数
     */
    public Integer updateAccount(Account account) {
        return accountMapper.update(account);
    }


    /**
     * 添加考务信息
     *
     * @param staff 考务信息
     * @return 考务id
     */
    public Integer addStaff(Staff staff) {
        staffMapper.addItem(staff);
        return staff.getId();
    }

    /**
     * 更新考务信息
     *
     * @param staff 考务信息
     * @return 受影响行数
     */
    public Integer updateStaff(Staff staff) {
        return staffMapper.update(staff);
    }

    /**
     * 获取考务信息
     *
     * @return 考务信息列表
     */
    public List<Staff> getStaffList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return staffMapper.findAll();
    }

    /**
     * 添加监考信息
     *
     * @param invigilate 监考信息
     * @return 考务id
     */
    public Integer addInvigilate(Invigilate invigilate) {
        invigilateMapper.addItem(invigilate);
        return invigilate.getId();
    }

    /**
     * 更新监考信息
     *
     * @param invigilate 监考信息
     * @return 受影响行数
     */
    public Integer updateInvigilate(Invigilate invigilate) {
        return invigilateMapper.update(invigilate);
    }

    /**
     * 获取监考信息列表
     *
     * @return 监考信息列表
     */
    public List<Invigilate> getInvigilateList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return invigilateMapper.findAll();
    }

    /**
     * 添加科目信息
     *
     * @param subject 科目信息
     * @return 科目id
     */
    public Integer addSubject(Subject subject) {
        subjectMapper.add(subject);
        return subject.getId();
    }

    /**
     * 更新科目信息
     *
     * @param subject 科目信息
     * @return 受影响行数
     */
    public Integer updateSubject(Subject subject) {
        if (subject.getName() != null || subject.getDuration() != null)
            return subjectMapper.update(subject);
        return 0;
    }

    /**
     * 获取科目信息
     *
     * @param page 页
     * @param size 页大小
     * @return 科目信息列表
     */
    public List<Subject> getSubjectList(Subject subject,Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return subjectMapper.findAll(subject);
    }

    /**
     * 添加科目-考试时间预设信息
     *
     * @param arrangeTimes 科目-考试时间预设信息
     * @param subjectId    科目编号
     * @return 受影响行数
     */
    public Integer addArrangeTime(List<ArrangeTime> arrangeTimes, Integer subjectId) {
        return arrangeTimeMapper.add(arrangeTimes, subjectId);
    }

    /**
     * 更新科目-考试时间预设信息
     *
     * @param arrangeTime 科目-考试时间预设信息
     * @return 受影响行数
     */
    public Integer updateArrangeTime(ArrangeTime arrangeTime) {
        return arrangeTimeMapper.update(arrangeTime);
    }

    /**
     * 删除科目-考试时间预设
     *
     * @param arrangeTime 科目-考试时间预设信息
     * @return 受影响行数
     */
    public Integer deleteArrangeTime(ArrangeTime arrangeTime) {
        return arrangeTimeMapper.delete(arrangeTime);
    }

    /**
     * 获取科目信息
     *
     * @param page 页
     * @param size 页大小
     * @return 受影响行数
     */
    public List<ExamRoom> getExamRoomList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return examRoomMapper.findAll();
    }

    /**
     * 添加教室信息
     *
     * @param examRoom 教室信息
     * @return 受影响行数
     */
    public Integer addExamRoom(ExamRoom examRoom) {
        return examRoomMapper.add(examRoom);
    }

    /**
     * 更新教室信息
     *
     * @param examRoom 教室信息
     * @return 受影响行数
     */
    public Integer updateExamRoom(ExamRoom examRoom) {
        return examRoomMapper.update(examRoom);
    }

    /**
     * 获取考试信息
     *
     * @param page 页
     * @param size 页大小
     * @return 受影响行数
     */
    public List<Exam> getExamList(Exam exam, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return examMapper.findAll(exam);
    }

    /**
     * 添加考试信息
     *
     * @param exam 考试信息
     * @return 受影响行数
     */
    public Integer addExam(Exam exam) {
        return examMapper.add(exam);
    }

    /**
     * 更新考试信息
     *
     * @param exam 考试信息
     * @return 受影响行数
     */
    public Integer updateExam(Exam exam) {
        return examMapper.update(exam);
    }


    /**
     * 添加监考信息
     *
     * @param monitor 监考信息
     * @return 受影响行数
     */
    public Integer addMonitor(Monitor monitor) {
        return monitorMapper.add(monitor);
    }

    /**
     * 添加考试安排信息
     *
     * @param arrange 考试安排信息
     * @return 受影响行数
     */
    public Integer addArrange(Arrange arrange) {
        return arrangeMapper.add(arrange);
    }

    /**
     * 查询考试安排信息
     *
     * @param arrange 考试安排信息
     * @return 考试安排信息列表
     */
    public List<Arrange> getArrangeList(Arrange arrange, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        return arrangeMapper.findByExamId(arrange);
    }

    /**
     * 查询报名信息
     *
     * @param signUp 报名信息
     * @param page   页
     * @param size   页大小
     * @return
     */
    public List<SignUp> getSignUpList(SignUp signUp, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        if (signUp.getExamId() != null)
            return signUpMapper.findByExamId(signUp);
        return signUpMapper.findAll();
    }

    public Examinee getExaminee(Integer accountId) {
        return examineeMapper.findByAccountId(accountId);
    }
}
