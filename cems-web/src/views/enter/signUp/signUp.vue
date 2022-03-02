<script setup>
import {NCard, NButton, NInput, NSpace, NSelect, NImage, NCheckbox, NUpload} from "naive-ui"
import {useRouter} from 'vue-router'
import {reactive} from "vue";
import manage from "/src/api/manage"
import enter from "/src/api/enter"

const router = useRouter()

console.log(router.currentRoute.value.params)
const exam = reactive({
  id: router.currentRoute.value.params.exam_id,
  name: router.currentRoute.value.params.name
})

const examinee = reactive({
  account_id: window.$store.state.user.id,
  name: '',
  identity_type: '',
  identity_card: ''
})

const subject = reactive({
  id: router.currentRoute.value.params.subject_id,
  name: router.currentRoute.value.params.name,
  exams: []
})

function signUpSubmit() {
  console.log(examinee, exam)
  if (examinee.id && exam.id) {
    let signUp = {
      exam_id: exam.id,
      examinee_id: examinee.id
    }
    enter.signUp(signUp).then(res => {
      let json = res.data
      if (json.state === 200) {
        router.push({
          name: 'pay',
          params: {
            exam_id: signUp.exam_id,
            examinee_id: examinee.id,
            order_id: json.data.id,
            money: json.data.money,
            date: json.data.date
          }
        })
      }
    })
  }
}

function getExaminee() {
  manage.getExaminee({account_id: examinee.account_id}).then(res => {
    let json = res.data
    if (json.state === 200) {
      examinee.id = json.data.id
      examinee.name = json.data.name
      examinee.identity_type = json.data.identity_type
      examinee.identity_card = json.data.identity_card
    }
  })
}

function init() {
  getExaminee()
}

init()
</script>

<template>
  <n-space style="width: 500px;margin: 10px auto">
    <n-space vertical>
      <n-image
          width="150"
          height="200"
          src="#"
      />
    </n-space>
    <n-space vertical class="detail">
      <li>
        <label>姓名：</label>
        <span>{{ examinee.name }}</span>
      </li>
      <li>
        <label>证件类型：</label>
        <span>{{ examinee.identity_type }}</span>
      </li>
      <li>
        <label>证件号：</label>
        <span>{{ examinee.identity_card }}</span>
      </li>
      <li>
        <label>科目：</label>
        <span>{{ exam.name }}</span>
      </li>
      <li>
        <label>费用：</label>
        <span>120</span>
      </li>
      <n-button @click="signUpSubmit" style="margin-top: 20px;width: 200px;">报名</n-button>
    </n-space>
  </n-space>
</template>

<style scoped>
li {
  list-style: none;
}

.detail {
  margin-left: 20px;
}

.detail label {
  font-size: 20px;
  color: #666;
}

.detail span {
  font-size: 18px;
}
</style>
