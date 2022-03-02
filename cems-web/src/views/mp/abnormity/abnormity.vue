<script setup>
import {
  NCard,
  NButton,
  NImage,
  useMessage,
  NSelect,
  NSpace,
  NDataTable,
  NSwitch,
  NInput,
  NModal,
  NDatePicker,
  NPagination,
  NTabs,
  NTabPane
} from "naive-ui"
import manage from "/src/api/manage";
import {ref, reactive, h} from "vue";
import moment from 'moment'

const pag = reactive({
  exam: {
    current: 1,
    count: 0,
    size: 8,
    data: [],
    columns: [
      {
        title: '序号',
        key: 'key',
        align: 'center'
      },
      {
        title: '考试',
        key: 'exam_name',
        align: 'center'
      },
      {
        title: '姓名',
        key: 'name',
        align: 'center'
      },
      {
        title: '准考证号',
        key: 'admission',
        align: 'center'
      },
      {
        title: '类型',
        key: 'type',
        align: 'center'
      },
      {
        title: '详细',
        key: 'detail',
        align: 'center'
      }
    ]
  },
})

const tableData = reactive({
  data: [],
  current: 0
})
const fun = reactive({
  search: ''
})

function updateAbnormity(page) {
  if (page)
    pag.exam.current = page
  // manage.getExamList({page: pag.exam.current, size: pag.exam.size}).then(res => {
  //   if (res.data.state === 200) {
  //     tableData.data = res.data.data.list
  //     let data = []
  //     let index = 1
  //     res.data.data.list.forEach(item => {
  //       let temp = item
  //       temp.start_date = moment(temp.start_date).format('YYYY-MM-DD HH:mm:ss')
  //       temp.end_date = moment(temp.end_date).format('YYYY-MM-DD HH:mm:ss')
  //       temp.enabled = moment(temp.end_date).format('x') > new Date().getTime() ? '否' : '是'
  //       temp.key = (pag.exam.current - 1) * pag.exam.size + index++
  //       data.push(temp)
  //     })
  //     pag.exam.data = data
  //     pag.exam.count = res.data.data.pages
  //   }
  // })
}

function init() {
  updateAbnormity()
}

init()
</script>
<template>
  <n-card>
    <n-space vertical :size="[0,20]">
      <n-space>
        <n-space>
          <n-input v-model:value="fun.search" style="float: left;width: 200px;"
                   type="text" placeholder="请输入要搜索的名称"/>
          <n-button style="margin-left: 10px;" @click="">搜 索</n-button>
        </n-space>
      </n-space>
      <n-data-table :columns="pag.exam.columns" :data="pag.exam.data"/>
      <n-pagination v-model:page="pag.exam.current" :page-count="pag.exam.count" :on-update:page="updateRoom"/>
    </n-space>
  </n-card>
</template>

<style>
.n-tabs .n-tabs-nav {
  display: none !important;
}
</style>
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
