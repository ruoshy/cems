<script setup>
import {NCard, NButton, NInput, NSpace, NSelect} from "naive-ui"
import {useRouter} from 'vue-router'
import {reactive} from "vue";
import moment from 'moment'

import manage from "/src/api/manage"

const router = useRouter()

const subject = reactive({
  select: 0,
  list: []
})

const subjectList = reactive([
  {
    label: '-请选择-',
    value: 0
  }
])

function getExamList() {
  manage.getExamList({page: 1, size: 100, subject_id: subject.select}).then(res => {
    let json = res.data
    if (json.state === 200) {
      subject.list = json.data.list
      console.log(subject.exams)
    }
  })
}

function getSubject() {
  manage.getSubjectList({page: 1, size: 100}).then(res => {
    let json = res.data
    if (json.state === 200) {
      json.data.list.forEach(item => {
        subjectList.push({
          label: item.name,
          value: item.id
        })
      })
    }
  })
}

function selectSubject(val) {
  subject.select = val
  getExamList()
}

function init() {
  getSubject()
}

init()
</script>

<template>
  <div style="width: 100%;background: #04B0FB;height: 60px;">
    <n-space>
          <span
              style="line-height: 60px;margin: 0 20px;font-size: 20px;color: white;font-weight: bold">
            请选择考试科目:
          </span>
      <div style="display: inline-block;height: 60px;">
        <n-select v-model:value="subject.select" :options="subjectList" placeholder="选择报考科目"
                  style="width: 250px;margin-top: 10px"
                  :on-update:value="selectSubject"/>
      </div>
    </n-space>
  </div>
  <div style="padding: 20px 20px;background: #F6F7F9;">
    <n-space justify="space-around">
      <div>
        <span style="font-weight: bold;font-size: 20px">名称</span>
        <n-space vertical :size="[20,20]" style="margin-top: 20px">
          <li v-for="item in subject.list">
            {{ item.name }}
          </li>
        </n-space>
      </div>
      <div>
        <span style="font-weight: bold;font-size: 20px">报名有效时间</span>
        <n-space vertical :size="[20,20]" style="margin-top: 20px">
          <li v-for="item in subject.list">
            {{ moment(item.start_date).format('YYYY-MM-DD') }}-
            {{ moment(item.end_date).format('YYYY-MM-DD') }}
          </li>
        </n-space>
      </div>
      <div>
        <span style="font-weight: bold;font-size: 20px">报名</span>
        <n-space vertical :size="[20,20]" style="margin-top: 20px">
          <li v-for="item in subject.list">&nbsp;
            <router-link :to="{name:'signUp',params: {exam_id:item.id,name:item.name}}" v-if="item.option">报名
            </router-link>
          </li>
        </n-space>
      </div>
    </n-space>
  </div>
</template>

<style scoped>
li {
  list-style: none;
}
</style>
