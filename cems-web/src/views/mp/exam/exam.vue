<script setup>
import {
  NCard,
  NButton,
  useMessage,
  NSelect,
  NSpace,
  NDataTable,
  NSwitch,
  NInput,
  NModal,
  NDatePicker,
  NPagination,
} from "naive-ui"
import {h, defineComponent, ref, reactive} from 'vue'
import manage from "/src/api/manage"
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
        title: '开放报名',
        key: 'lock',
        align: 'center',
        render(row, index) {
          return h(
              NSwitch,
              {
                value: row.option,
                onClick: () => {
                  let item = pag.exam.data[index]
                  pag.exam.data[index].option = !item.option
                  manage.updateExam({
                    id: item.id,
                    option: item.option
                  }).then(res => {
                    let json = res.data
                    if (json.state === 200) {
                    }
                  })
                }
              },
          )
        }
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
                    console.log('index_' + index)
                    modal.update.open(row)
                  }
                },
                {default: () => '编辑'}
            ),
            h(
                NButton,
                {
                  style: 'margin-left:20px',
                  size: 'small',
                  color: '#d00',
                  onClick: () => {
                  }
                },
                {default: () => '删除'}
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
const subject_data = reactive({
  data: [],
  value: undefined
})

const year_data = [
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
]

const modal = reactive({
  add: {
    show: false,
    confirm: () => {
      if (!modal.data.name) {
        window.$message.warning('请输入编号')
        return
      }
      if (!modal.data.date_picker) {
        window.$message.warning('请选择报名起止时间')
        return;
      }
      let data = {}
      data.subject_id = subject_data.value
      data.name = modal.data.name
      data.start_date = modal.data.date_picker[0]
      data.end_date = modal.data.date_picker[1]
      manage.addExam(data).then(res => {
        let json = res.data
        if (json.state === 200) {
          window.$message.success('添加成功')
          modal.add.cancel()
          updateExam()
        }
      })
    },
    cancel: () => {
      modal.add.show = false
    },
    open: () => {
      modal.clear()
      modal.add.show = true
    }
  },
  update: {
    show: false,
    confirm: () => {
      let currentData = tableData.data[tableData.current]
      let data = {}
      if (!modal.data.name) {
        window.$message.warning('请输入编号')
        return
      } else {
        if (currentData.name !== modal.data.name)
          data.name = modal.data.name
      }
      if (!modal.data.date_picker) {
        window.$message.warning('请选择报名起止时间')
        return;
      } else {
        if (new Date(currentData.start_date).getTime() !== modal.data.date_picker[0])
          data.start_date = modal.data.date_picker[0]
        if (new Date(currentData.end_date).getTime() !== modal.data.date_picker[1])
          data.end_date = modal.data.date_picker[1]
      }
      if (modal.data.mark)
        data.mark = modal.data.mark
      data.id = currentData.id
      manage.updateExam(data).then(res => {
        let json = res.data
        if (json.state === 200) {
          window.$message.success('更新成功')
          modal.update.cancel()
          updateExam()
        }
      })
    },
    cancel: () => {
      modal.update.show = false
    },
    open: (row) => {
      modal.clear()
      console.log(row.id)
      modal.data.name = row.name
      modal.data.date_picker = [new Date(row.start_date).getTime(), new Date(row.end_date).getTime()]
      modal.data.mark = row.mark
      modal.update.show = true
    }
  },
  clear: () => {
    modal.data = {}
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

function init() {
  updateExam()
  // 获取科目信息
  manage.getSubjectList({page: pag.exam.current, size: 100}).then(res => {
    let json = res.data
    if (json.state === 200) {
      let list = json.data.list
      list.forEach(item => {
        subject_data.data.push({
          label: item.name,
          value: item.id
        })
      })
    }
  })
}

init()
</script>

<template>
  <n-card>
    <n-space vertical :size="[0,20]">
      <n-space>
        <n-button color="#468DE9" @click="modal.add.open">添加</n-button>
        <div style="display: inline-block;width: 100%">
          <n-select v-model:value="value" style="float: left;width: 200px;margin-left: 20px;"
                    :options="year_data" placeholder="年份"/>
          <n-input v-model:value="value" style="float: left;width: 200px;margin-left: 20px;"
                   type="text" placeholder="请输入要搜索的名称"/>
          <n-button style="margin-left: 10px;" @click="modal.add.open">搜 索</n-button>
        </div>
      </n-space>
      <n-data-table :columns="pag.exam.columns" :data="pag.exam.data"/>
      <n-pagination :page="pag.exam.current" :page-count="pag.exam.count" :on-update:page="updateRoom"/>
    </n-space>
  </n-card>

  <n-modal :show="modal.add.show" transform-origin="center" :on-update:show="modal.add.cancel">
    <n-card class="modal-content" style="width: 520px;height: 300px;" :bordered="true" size="small">
      <div class="modal-head">
        <p class="title">添加</p>
      </div>
      <div class="modal-body">
        <n-space vertical :size="[0,10]">
          <n-space>
            <label>名称：</label>
            <n-input v-model:value="modal.data.name" type="text" placeholder="请输入编号" style="width: 200px"/>
          </n-space>
          <n-space>
            <label>科目：</label>
            <n-select v-model:value="subject_data.value" style="float: left;width: 200px;"
                      :options="subject_data.data" placeholder="选择科目"/>
          </n-space>
          <n-space>
            <label>报名起止：</label>
            <n-date-picker v-model:value="modal.data.date_picker"
                           start-placeholder="开始日期"
                           end-placeholder="结束日期"
                           type="datetimerange" clearable style="width: 370px"/>
          </n-space>
          <n-space>
            <label>备注：</label>
            <n-input type="text" v-model:value="modal.data.mark" placeholder="可选" style="width: 370px"/>
          </n-space>
        </n-space>
      </div>
      <template #footer>
        <n-space justify="end">
          <n-button @click="modal.add.cancel">取 消</n-button>
          <n-button @click="modal.add.confirm" type="info">确 定</n-button>
        </n-space>
      </template>
    </n-card>
  </n-modal>
  <n-modal :show="modal.update.show" transform-origin="center" :on-update:show="modal.update.cancel">
    <n-card class="modal-content" style="width: 520px;height: 270px;" :bordered="true" size="small">
      <div class="modal-head">
        <p class="title">添加</p>
      </div>
      <div class="modal-body">
        <n-space vertical :size="[0,10]">
          <n-space>
            <label>名称：</label>
            <n-input v-model:value="modal.data.name" type="text" placeholder="请输入编号" style="width: 370px"/>
          </n-space>
          <n-space>
            <label>报名起止：</label>
            <n-date-picker v-model:value="modal.data.date_picker"
                           start-placeholder="开始日期"
                           end-placeholder="结束日期"
                           type="datetimerange" clearable style="width: 370px"/>
          </n-space>
          <n-space>
            <label>备注：</label>
            <n-input type="text" v-model:value="modal.data.mark" placeholder="可选" style="width: 370px"/>
          </n-space>
        </n-space>
      </div>
      <template #footer>
        <n-space justify="end">
          <n-button @click="modal.update.cancel">取 消</n-button>
          <n-button @click="modal.update.confirm" type="info">确 定</n-button>
        </n-space>
      </template>
    </n-card>
  </n-modal>

  <!--  <n-modal :show="modal3.add.show" transform-origin="center" :on-update:show="modal3.add.show">-->
  <!--    <n-card class="modal-content" style="width: 400px;height: 150px;" :bordered="true" size="small">-->
  <!--      <div class="modal-head">-->
  <!--        <p class="title">删除</p>-->
  <!--      </div>-->
  <!--      <div class="modal-body">-->
  <!--        <label style="width: 150px;">确定删除该条记录</label>-->
  <!--      </div>-->
  <!--      <template #footer>-->
  <!--        <n-space justify="end">-->
  <!--          <n-button @click="closeAddModal3()">取 消</n-button>-->
  <!--          <n-button @click="" type="info">确 定</n-button>-->
  <!--        </n-space>-->
  <!--      </template>-->
  <!--    </n-card>-->
  <!--  </n-modal>-->
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
