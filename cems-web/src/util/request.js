import axios from 'axios'
import router from '/src/router/index'
import store from '/src/store/index'

const service = axios.create()
// service.interceptors.request.use(
//     config => {
//         return config
//     },
//     err => {
//         return Promise.reject(err)
//     }
// )
service.interceptors.response.use(
    response => {
        if (response.data.state) {
            switch (response.data.state) {
                case 101:
                    window.$message.error(response.data.message)
                    store.commit('updateUserState', {isLogin: false})
                    router.replace({name: 'login'})
                    break
            }
        }
        return response
    }
)

export default service
