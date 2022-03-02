import {createApp} from 'vue'
import App from './App'

import router from './router/index'
import store from './store/index'

router.beforeEach((to, from, next) => {
    if (to.name !== 'login' && !store.state.user.isLogin) {
        router.replace({name: 'login'})
    } else {
        if (to.meta.role === store.state.user.role || to.meta) {
            store.commit('updateMenuSelected', to.name)
            next()
        }
    }
})

createApp(App).use(router).use(store).mount('#app')
