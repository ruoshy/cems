import {createStore} from 'vuex'
import createPersistedState from 'vuex-persistedstate'

export default createStore({
    state() {
        return {
            user: {
                id: 0,
                name: 'gg',
                role: 'admin', // staff/admin
                isLogin: true
            },
            menu: {
                current: 'home',
                items: [
                    {key: 'home', name: '首页', icon: 'icon-home'},
                    {key: 'subject', name: '科目管理', icon: 'icon-yunyingguanli'},
                    {key: 'staff', name: '考务管理', icon: 'icon-fanshe'},
                    {key: 'room', name: '考场管理', icon: 'icon-chengshi'},
                    {key: 'exam', name: '考试管理', icon: 'icon-icon_bangzhuwendang'},
                ]
            }
        }
    },
    mutations: {
        updateMenuSelected(state, name) {
            state.menu.current = name
        },
        updateMenuItems(state, items) {
            console.log(items)
            state.menu.items = items
            if (items.length !== 0)
                state.menu.current = items[0].key
        },
        updateUserState(state, data) {
            state.user.isLogin = data.isLogin
            if (data.id) {
                state.user.id = data.id
                state.user.role = data.role.split('_')[1]
            }
        }
    },
    plugins: [createPersistedState()]
});
