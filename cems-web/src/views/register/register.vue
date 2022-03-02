<script setup>
import {NCard, NButton, NInput, NSpace, NSelect} from "naive-ui"
import {useRouter} from 'vue-router'
import {ref, reactive} from "vue"
import account from "/src/api/account"

const router = useRouter()

const user = reactive({
  password: '123456',
  verify_password: '123456'
})
const examinee = reactive({
  name: 'abc',
  email: '124124124@qq.com',
  phone: '1241414124',
  identity_type: '中华人民共和国居民身份证',
  identity_card: '330382199810222820'
})
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

function register() {
  if (!user.password) {
    window.$message.warning('请输入密码')
    return
  }
  if (!user.verify_password) {
    window.$message.warning('请确认密码')
    return;
  }
  if (user.password !== user.verify_password)
    window.$message.warning('输入的密码不一致')
  if (!examinee.identity_type) {
    window.$message.warning('请选择证件类型')
    return;
  }
  if (!examinee.identity_card) {
    window.$message.warning('请输入证件号')
    return;
  }
  if (!examinee.name) {
    window.$message.warning('请输入姓名')
    return;
  }
  account.register({
    account: {
      email: examinee.email,
      phone: examinee.phone,
      password: user.password,
      role_id: 3,
    },
    name: examinee.name,
    identity_type: examinee.identity_type,
    identity_card: examinee.identity_card
  }).then(res => {
    let json = res.data
    if (json.state === 200) {
      window.$message.info('注册成功')
      router.push({name: 'index'})
    }
  })
}
</script>
<template>
  <n-space vertical>
    <li>
      <label>电子邮箱</label>
      <n-input v-model:value="examinee.email" placeholder="请输入电子邮箱"/>
    </li>
    <li>
      <label>手机号</label>
      <n-input v-model:value="examinee.phone" placeholder="请输入手机号"/>
    </li>
    <li>
      <label>密码</label>
      <n-input v-model:value="user.password" type="password"/>
    </li>
    <li>
      <label>密码确认</label>
      <n-input v-model:value="user.verify_password" type="password"/>
    </li>
    <li>
      <label>证件类型</label>
      <n-select v-model:value="examinee.identity_type"
                :options="identity_card_list" placeholder="证件类型"/>
    </li>
    <li>
      <label>证件号</label>
      <n-input v-model:value="examinee.identity_card"/>
    </li>
    <li>
      <label>姓名</label>
      <n-input v-model:value="examinee.name"/>
    </li>
    <n-button @click="register">提交</n-button>
  </n-space>
</template>

<style scoped>
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
