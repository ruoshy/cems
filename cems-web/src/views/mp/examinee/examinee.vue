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

const tabPanel = reactive({
  current: 'exam',
  panel: ['exam', 'examinnee'],
  exam: {
    search: '',
    jump: (exam_id) => {
      tabPanel.current = 'examinnee'
      updateExaminee(undefined, exam_id)
    }
  },
  examinnee: {
    jump: () => {
      tabPanel.current = 'exam'

    }
  }
})

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
        title: '名称',
        key: 'name',
        align: 'center'
      },
      {
        title: '报名开始',
        key: 'start_date',
        align: 'center'
      },
      {
        title: '报名截止',
        key: 'end_date',
        align: 'center'
      },
      {
        title: '是否截止',
        key: 'enabled',
        align: 'center'
      },
      {
        title: '备注',
        key: 'mark',
        align: 'center'
      },
      {
        title: '动作',
        key: 'actions',
        align: 'center',
        render(row, index) {
          return [
            h(
                NButton,
                {
                  style: {},
                  size: 'small',
                  color: '#468DE9',
                  onClick: () => {
                    tableData.current = index
                    tabPanel.exam.jump(row.id)
                  }
                },
                {default: () => '查看详细'}
            )
          ]
        }
      },
    ]
  },
  examinee: {
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
        title: '考场',
        key: 'exam_room',
        align: 'center'
      },
      {
        title: '座位号',
        key: 'seat',
        align: 'center'
      },
      {
        title: '动作',
        key: 'actions',
        align: 'center',
        render(row, index) {
          return [
            h(
                NButton,
                {
                  style: {},
                  size: 'small',
                  color: '#468DE9',
                  onClick: () => {
                    examineeTableData.current = index
                    examinee_modal.update.open(row)
                  }
                },
                {default: () => '详细'}
            )
          ]
        }
      },
    ]
  }
})

const tableData = reactive({
  data: [],
  current: 0
})

const examineeTableData = reactive({
  data: [],
  current: 0
})

const year_data = reactive({
  data: [
    {
      label: "全部",
      value: 'all',
    },
    {
      label: "2021",
      value: '2021',
    },
    {
      label: '2020',
      value: '2020'
    },
    {
      label: '2019',
      value: '2019'
    },
    {
      label: '2018',
      value: '2018'
    },
    {
      label: '2017',
      value: '2017'
    }
  ],
  value: 'all'
})

const examinee_modal = reactive({
  update: {
    show: false,
    confirm: () => {

    },
    cancel: () => {
      examinee_modal.update.show = false
    },
    open: (row) => {
      examinee_modal.clear()
      let item = examinee_modal.data
      item.examinee = {}
      item.exam_room = {}
      item.examinee.name = row.examinee.name
      item.grade = row.grade
      item.seat = row.seat
      item.admission = row.admission
      if (row.exam_room)
        item.exam_room.name = row.exam_room.name
      examinee_modal.update.show = true
    }
  },
  clear: () => {
    examinee_modal.data = {}
  },
  data: {}
})

function updateExam(page) {
  if (page)
    pag.exam.current = page
  manage.getExamList({page: pag.exam.current, size: pag.exam.size}).then(res => {
    if (res.data.state === 200) {
      tableData.data = res.data.data.list
      let data = []
      let index = 1
      res.data.data.list.forEach(item => {
        let temp = item
        temp.start_date = moment(temp.start_date).format('YYYY-MM-DD HH:mm:ss')
        temp.end_date = moment(temp.end_date).format('YYYY-MM-DD HH:mm:ss')
        temp.enabled = moment(temp.end_date).format('x') > new Date().getTime() ? '否' : '是'
        temp.key = (pag.exam.current - 1) * pag.exam.size + index++
        data.push(temp)
      })
      pag.exam.data = data
      pag.exam.count = res.data.data.pages
    }
  })
}

function updateExaminee(page, exam_id) {
  if (page)
    pag.exam.current = page
  manage.getSignUpList({page: pag.examinee.current, size: pag.examinee.size, exam_id: exam_id}).then(res => {
    if (res.data.state === 200) {
      examineeTableData.data = res.data.data.list
      let data = []
      let index = 1
      res.data.data.list.forEach(item => {
        let temp = item
        temp.key = (pag.examinee.current - 1) * pag.examinee.size + index++
        temp.name = item.examinee.name
        data.push(temp)
      })
      pag.examinee.data = data
      pag.examinee.count = res.data.data.pages
    }
  })
}

// 生成准考证，分配考场、座位号
function distribute() {

}

// 上传成绩
function uploadGrade() {

}


function init() {
  updateExam()
}

init()
</script>
<template>
  <n-card>
    <n-tabs type="line" :value="tabPanel.current">
      <n-tab-pane :name="tabPanel.panel[0]">
        <n-space vertical :size="[0,20]">
          <n-space>
            <n-space>
              <n-select v-model:value="year_data.value" style="float: left;width: 200px;"
                        :options="year_data.data" placeholder="年份"/>
              <n-input v-model:value="tabPanel.exam.search" style="float: left;width: 200px;margin-left: 20px;"
                       type="text" placeholder="请输入要搜索的名称"/>
              <n-button style="margin-left: 10px;" @click="">搜 索</n-button>
            </n-space>
          </n-space>
          <n-data-table :columns="pag.exam.columns" :data="pag.exam.data"/>
          <n-pagination v-model:page="pag.exam.current" :page-count="pag.exam.count" :on-update:page="updateRoom"/>
        </n-space>
      </n-tab-pane>
      <n-tab-pane :name="tabPanel.panel[1]">
        <n-space vertical :size="[0,20]">
          <n-space justify="space-between">
            <n-space>
              <n-button @click="tabPanel.examinnee.jump()">返回</n-button>
              <n-input v-model:value="tabPanel.exam.search" style="float: left;width: 200px;margin-left: 20px;"
                       type="text" placeholder="请输入要搜索的名称"/>
              <n-button style="margin-left: 10px;" @click="">搜 索</n-button>
            </n-space>
            <n-space>
              <span style="font-size: 16px;font-weight: bold">{{ tableData.data[tableData.current].name }}</span>
            </n-space>
            <n-space>
              <n-button @click="">分配</n-button>
              <n-button @click="">上传成绩</n-button>
            </n-space>
          </n-space>
          <n-data-table :columns="pag.examinee.columns" :data="pag.examinee.data"/>
          <n-pagination v-model:page="pag.examinee.current" :page-count="pag.examinee.count"
                        :on-update:page="updateExaminee"/>
        </n-space>
      </n-tab-pane>
    </n-tabs>
  </n-card>

  <n-modal :show="examinee_modal.update.show" transform-origin="center" :on-update:show="examinee_modal.update.cancel">
    <n-card class="modal-content" style="width: 520px;height: 470px;" :bordered="true" size="small">
      <div class="modal-head">
        <p class="title">详细</p>
      </div>
      <div class="modal-body">
        <n-space vertical :size="[0,10]">
          <li>
            <label style="vertical-align: top">照片:</label>
            <n-image width="100" src="https://07akioni.oss-cn-beijing.aliyuncs.com/07akioni.jpeg"
            />
          </li>
          <li>
            <label>姓名:</label>
            <n-input v-model:value="examinee_modal.data.examinee.name" placeholder="请输入姓名"/>
          </li>
          <li>
            <label>准考证:</label>
            <n-input v-model:value="examinee_modal.data.admission" placeholder="请输入准考证号"/>
          </li>
          <li>
            <label>考场:</label>
            <n-input v-model:value="examinee_modal.data.exam_room.name" placeholder="请输入考场"/>
          </li>
          <li>
            <label>座位号:</label>
            <n-input v-model:value="examinee_modal.data.seat" placeholder="请输入座位号"/>
          </li>
          <li>
            <label>成绩:</label>
            <n-input v-model:value="examinee_modal.data.grade" placeholder="请输入成绩"/>
          </li>
        </n-space>
      </div>
      <template #footer>
        <n-space justify="end">
          <n-button @click="examinee_modal.update.cancel">取 消</n-button>
          <n-button @click="examinee_modal.update.confirm" type="info">确 定</n-button>
        </n-space>
      </template>
    </n-card>
  </n-modal>
</template>

<style>
.n-tabs .n-tabs-nav {
  display: none !important;
}

.n-tab-pane {
  padding: 0 !important;
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
