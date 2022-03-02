import request from "../util/request";
import qs from 'qs'

export default {
    signUp(data) {
        return request({
            url: '/api/e/signUp',
            method: 'post',
            data: data
        })
    },
    pay(data) {
        return request({
            url: '/api/e/pay',
            method: 'post',
            data: data
        })
    },
}
