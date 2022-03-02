import request from "/src/util/request"
import qs from "qs";

export default {
    getTest() {
        return request({
            url: '/api/m/test',
            method: 'get'
        })
    },
    getSubjectList(data) {
        return request({
            url: '/api/m/subject',
            method: 'get',
            params: data
        })
    },
    addSubject(data) {
        return request({
            url: '/api/m/subject/add',
            method: 'post',
            data: data
        })
    },
    updateSubject(data) {
        return request({
            url: '/api/m/subject/update',
            method: 'post',
            data: data
        })
    },
    deleteArrangeTime(data) {
        return request({
            url: '/api/m/subject/arrangeTime/delete',
            method: 'post',
            data: data
        })
    },
    getStaffList(data) {
        return request({
            url: '/api/m/staff',
            method: 'get',
            params: data
        })
    },
    addStaff(data) {
        return request({
            url: '/api/m/staff/add',
            method: 'post',
            data: qs.stringify(data)
        })
    },
    updateStaff(data) {
        return request({
            url: '/api/m/staff/update',
            method: 'post',
            data: data
        })
    },
    getExamRoomList(data) {
        return request({
            url: '/api/m/examRoom',
            method: 'get',
            params: data
        })
    },
    addExamRoom(data) {
        return request({
            url: '/api/m/examRoom/add',
            method: 'post',
            data: data
        })
    },
    updateExamRoom(data) {
        return request({
            url: '/api/m/examRoom/update',
            method: 'post',
            data: data
        })
    },
    getExamList(data) {
        return request({
            url: '/api/m/exam',
            method: 'get',
            params: data
        })
    },
    addExam(data) {
        return request({
            url: '/api/m/exam/add',
            method: 'post',
            data: data
        })
    },
    updateExam(data) {
        return request({
            url: '/api/m/exam/update',
            method: 'post',
            data: data
        })
    },
    addMonitor(data) {
        return request({
            url: '/api/m/monitor/add',
            method: 'post',
            data: data
        })
    },
    getArrangeList(data) {
        return request({
            url: '/api/m/arrange',
            method: 'get',
            params: data
        })
    },
    addArrange(data) {
        return request({
            url: '/api/m/arrange/add',
            method: 'post',
            data: data
        })
    },
    getInvigilateList(data) {
        return request({
            url: '/api/m/invigilate',
            method: 'get',
            params: data
        })
    },
    addInvigilate(data) {
        return request({
            url: '/api/m/invigilate/add',
            method: 'post',
            data: qs.stringify(data)
        })
    },
    updateInvigilate(data) {
        return request({
            url: '/api/m/invigilate/update',
            method: 'post',
            data: data
        })
    },
    getSignUpList(data) {
        return request({
            url: '/api/m/signUp',
            method: 'get',
            params: data
        })
    },
    getExaminee(data) {
        return request({
            url: '/api/m/examinee',
            method: 'get',
            params: data
        })
    },
}
