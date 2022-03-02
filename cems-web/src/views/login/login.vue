<script setup>
import {NCard, NButton, NInput, NSpace, useMessage, NSelect, NModal} from "naive-ui"
import {useRouter} from 'vue-router'
import {reactive, ref} from "vue"
import account from "../../api/account"
import {useStore} from "vuex";
import {adminMenu, staffMenu} from '/src/router/authMenu'

const router = useRouter()
const message = useMessage()
const store = useStore()
window.$message = message

const user_info = ref({
  username: '123456789',
  password: '123456'
})

function login() {
  account.login({
    u: user_info.value.username,
    p: user_info.value.password
  }).then(res => {
    let data = res.data
    if (data.state === 200) {
      let id = data.data.principal.id
      let role = data.data.auth[0].authority
      store.commit('updateUserState', {
        isLogin: true,
        id: id,
        role: role
      })
      if (role === 'ROLE_exam') {
        router.push({name: 'enter_index'})
        return
      }
      store.commit('updateMenuItems', role === 'ROLE_admin' ? adminMenu : staffMenu)
      router.push({name: data.data.auth[0].authority === 'ROLE_admin' ? 'home' : 'examinee'})
    } else {
      message.error(data.message)
    }
  })
}

const identity_card_list = [
  {
    label: '-请选择-',
    value: ''
  },
  {
    label: '中华人民共和国居民身份证',
    value: '中华人民共和国居民身份证',
  }
]

const modal = reactive({
  register: {
    show: false,
    confirm: () => {
      register()
    },
    cancel: () => {
      modal.register.show = false
    },
    open: () => {
      modal.clear()
      modal.data.password = ''
      modal.data.verify_password = ''
      modal.data.name = ''
      modal.data.email = ''
      modal.data.phone = ''
      modal.data.identity_type = ''
      modal.data.identity_card = ''
      modal.register.show = true
    }
  },
  clear: () => {
    modal.data = {}
  },
  data: {}
})

function register() {
  if (!modal.data.password) {
    window.$message.warning('请输入密码')
    return
  }
  if (!modal.data.verify_password) {
    window.$message.warning('请确认密码')
    return;
  }
  if (modal.data.password !== modal.data.verify_password)
    window.$message.warning('输入的密码不一致')
  if (!modal.data.identity_type) {
    window.$message.warning('请选择证件类型')
    return;
  }
  if (!modal.data.identity_card) {
    window.$message.warning('请输入证件号')
    return;
  }
  if (!modal.data.name) {
    window.$message.warning('请输入姓名')
    return;
  }
  account.register({
    account: {
      email: modal.data.email,
      phone: modal.data.phone,
      password: modal.data.password,
      role_id: 3,
    },
    name: modal.data.name,
    identity_type: modal.data.identity_type,
    identity_card: modal.data.identity_card
  }).then(res => {
    let json = res.data
    if (json.state === 200) {
      window.$message.info('注册成功')
      modal.register.cancel()
    }
  })
}
</script>

<template>
  <div id="bg">
    <n-card id="wind" title="欢迎登录">
      <n-space vertical wrap>
        <n-input type="text" placeholder="请输入账号或手机号" v-model:value="user_info.username"/>
        <n-input type="password" placeholder="请输入密码" v-model:value="user_info.password"/>
        <n-button type="primary" class="login-btn" color="#468DE9" @click="login">登录</n-button>
        <span @click="modal.register.open">考生注册</span>
      </n-space>
    </n-card>
  </div>
  <n-modal :show="modal.register.show" transform-origin="center" :on-update:show="modal.register.cancel">
    <n-card class="modal-content" style="width: 520px;height: 600px;" :bordered="true" size="small">
      <div class="modal-head">
        <p class="title">注册</p>
      </div>
      <div class="modal-body">
        <n-space vertical :size="[0,10]">
          <li>
            <label style="vertical-align: baseline">电子邮箱</label>
            <n-input v-model:value="modal.data.email" placeholder="请输入电子邮箱" style="width: 300px"/>
          </li>
          <li>
            <label>手机号</label>
            <n-input v-model:value="modal.data.phone" placeholder="请输入手机号" style="width: 300px"/>
          </li>
          <li>
            <label>密码</label>
            <n-input v-model:value="modal.data.password" placeholder="请输入密码" type="password" style="width: 300px"/>
          </li>
          <li>
            <label>密码确认</label>
            <n-input v-model:value="modal.data.verify_password" placeholder="请确认密码" type="password"
                     style="width: 300px"/>
          </li>
          <li>
            <label>证件类型</label>
            <n-select v-model:value="modal.data.identity_type"
                      :options="identity_card_list" placeholder="证件类型" style="width: 300px;display: inline-block"/>
          </li>
          <li>
            <label>证件号</label>
            <n-input v-model:value="modal.data.identity_card" placeholder="请输入证件号" style="width: 300px"/>
          </li>
          <li>
            <label>姓名</label>
            <n-input v-model:value="modal.data.name" placeholder="请输入姓名" style="width: 300px"/>
          </li>
        </n-space>
      </div>
      <template #footer>
        <n-space justify="end">
          <n-button @click="modal.register.cancel">取 消</n-button>
          <n-button @click="modal.register.confirm" type="info">确 定</n-button>
        </n-space>
      </template>
    </n-card>
  </n-modal>
</template>

<style scoped>
ul {
  list-style: none;
  padding: 0;
}

#bg {
  background-image: url("../../assets/img.png");
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  position: relative;
}

#wind {
  position: absolute;
  right: 160px;
  top: 20%;
  width: 360px;
  background: white;
  border-radius: 5px;
  box-shadow: 1px 1px 5px gainsboro;
}

#wind li {
  margin: 15px 0;
}

#wind li span {
  text-align: right;
  width: 70px;
  display: inline-block;
}

#wind .ivu-input-wrapper {
  width: 200px;
}

.login-btn {
  margin-top: 10px;
  margin-left: 20px;
  width: 260px;
}

.n-input {
  margin: 10px 0;
}

li {
  list-style: none
}

.modal-body label {
  width: 80px;
  display: inline-block;

}

.modal-body .n-input {
  width: 250px;
}

.modal-content {
  border-radius: 5px;
  padding: 0;
}

.modal-head {
  border-bottom: solid #eee 1px;
  margin-bottom: 10px;
}

.modal-head .title {
  font-size: 16px;
  margin: 3px 0;
}

.modal-body {
  padding-top: 10px;
  padding-left: 15px;
}
</style>
