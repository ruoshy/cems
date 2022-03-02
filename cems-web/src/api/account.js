import request from "../util/request";
import qs from 'qs'

export default {
    login(data) {
        return request({
            url: '/api/l/login',
            method: 'post',
            data: qs.stringify(data)
        })
    },
    register(data) {
        return request({
            url: '/api/l/register',
            method: 'post',
            data: data
        })
    }
}
