<script setup>
import {
  NCard,
  NTag,
  NButton,
  NPagination,
  useMessage,
  NDataTable,
  NIcon,
  NMention,
  NInput,
  useDialog,
  NModal,
  NTimePicker,
  NSpace,
  NSwitch,
  NDynamicTags
} from "naive-ui"
import {h, defineComponent, ref, reactive} from 'vue'
import manage from "../../../api/manage";

const pag = reactive({
  subject: {
    current: 1,
    count: 0,
    name: '',
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
        title: '预设',
        key: 'arrangeTimes',
        align: 'center',
        render(row) {
          return h(NSpace, row.arrange_times.join(';'))
        }
      },
      {
        title: '时长',
        key: 'duration',
        align: 'center'
      },
      {
        title: '动作',
        key: 'action',
        align: 'center',
        render(row, index) {
          return h(NSpace,
              {
                justify: 'center'
              },
              [
                h(NButton,
                    {
                      size: 'small',
                      color: '#468DE9',
                      onClick: () => {
                        tableData.current = index
                        modal.update.open(row)
                      }
                    },
                    '编辑'
                ),
                h(NButton, {
                      size: 'small',
                      color: '#d00',
                      onClick: () => {
                      }
                    },
                    '删除'
                )
              ]
          )
        }
      }
    ]
  }
})

const tableData = reactive({
  data: [],
  current: 0
})

const modal = reactive({
  add: {
    show: false,
    confirm: () => {
      if (!modal.data.name) {
        window.$message.warning('请输入名称')
        return
      }
      if (!modal.data.duration) {
        window.$message.warning('请输入时长')
        return
      }
      if (!modal.data.arrange_times) {
        window.$message.warning('请添加预设')
        return
      }
      let data = {}
      data.name = modal.data.name
      data.duration = modal.data.duration
      if (modal.data.arrange_times.length > 0) {
        data.arrange_times = []
        modal.data.arrange_times.forEach(item => {
          data.arrange_times.push({
            time: item
          })
        })
      }
      manage.addSubject(data).then(res => {
        let json = res.data
        if (json.state === 200) {
          window.$message.success('添加成功')
          modal.add.cancel()
          updateSubject()
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
    confirm: async () => {
      if (!modal.data.name) {
        window.$message.warning('请输入名称')
        return
      }
      if (!modal.data.duration) {
        window.$message.warning('请输入时长')
        return
      }
      if (!modal.data.arrange_times) {
        window.$message.warning('请添加预设')
        return
      }
      let data = {}
      let currentData = tableData.data[tableData.current]
      if (modal.data.name !== currentData.name)
        data.name = modal.data.name
      if (modal.data.duration !== currentData.duration)
        data.duration = modal.data.duration
      data.id = currentData.id
      let flag = true
      // 删除
      if (modal.data.arrange_times_del.length !== 0) {
        let del_data = []
        modal.data.arrange_times_del.forEach(item => {
          let index = currentData.arrange_times.findIndex((at) => {
            return at.time === item
          })
          del_data.push({
            id: currentData.arrange_times[index].id
          })
        })
        await manage.deleteArrangeTime(del_data).then(res => {
          let json = res.data
          if (json.state === 200) {
          } else {
            flag = false
          }
        })
      }
      // 添加
      if (modal.data.arrange_times_add.length !== 0) {
        data.arrange_times = []
        modal.data.arrange_times_add.forEach(item => {
          data.arrange_times.push({
            time: item
          })
        })
      }

      await manage.updateSubject(data).then(res => {
        let json = res.data
        if (json.state === 200) {
        } else {
          flag = false
        }
      })
      if (flag) {
        window.$message.success('更新成功')
        modal.update.cancel()
        updateSubject()
      }
    },
    cancel: () => {
      modal.update.show = false
    },
    open: (row) => {
      modal.clear()
      modal.data.name = row.name
      modal.data.duration = row.duration
      modal.data.arrange_times = row.arrange_times
      modal.update.show = true
    }
  },
  clear: () => {
    modal.data = {
      name: '',
      duration: '',
      arrange_times: [],
      arrange_times_add: [],
      arrange_times_del: []
    }
  },
  data: {}
})

function updateSubject(page) {
  if (page)
    pag.subject.current = page
  manage.getSubjectList({
    page: pag.subject.current,
    size: pag.subject.size,
    name: pag.subject.name
  }).then(res => {
    if (res.data.state === 200) {
      tableData.data = res.data.data.list
      let data = []
      let index = 1
      res.data.data.list.forEach(item => {
        data.push({
          key: (pag.subject.current - 1) * pag.subject.size + index++,
          name: item.name,
          duration: item.duration,
          arrange_times: item.arrange_times.map((at) => at.time),
        })
      })
      pag.subject.data = data
      pag.subject.count = res.data.data.pages
    }
  })
}

function init() {
  updateSubject(1)
}

function tagsFlush(values) {
  let data_arrange_times = tableData.data[tableData.current].arrange_times
  if (values.length > modal.data.arrange_times.length) {
    let temp = values[values.length - 1];
    if (modal.data.arrange_times_add.indexOf(temp) === -1 && !data_arrange_times.find((value) => value.time === temp))
      modal.data.arrange_times_add.push(temp)
  } else {
    let m_arrange_times = modal.data.arrange_times
    for (let i = 0; i < m_arrange_times.length; i++) {
      if (values.indexOf(m_arrange_times[i]) === -1) {
        // 被删除值 m_arrange_times[i]
        modal.data.arrange_times_add.splice(modal.data.arrange_times_add.indexOf(m_arrange_times[i]), 1)
        if (modal.data.arrange_times_del.indexOf(m_arrange_times[i]) === -1
            && data_arrange_times.find((value) => value.time === m_arrange_times[i]) !== undefined)
          modal.data.arrange_times_del.push(m_arrange_times[i])
        break
      }
    }
  }
  modal.data.arrange_times = values
  console.log('add', modal.data.arrange_times_add)
  console.log('del', modal.data.arrange_times_del)
}

init()

function search() {
  updateSubject(1)
}
</script>
<template>
  <n-card>
    <n-space vertical :size="[0,20]">
      <n-space>
        <n-button color="#468DE9" @click="modal.add.open">添加</n-button>
        <n-input type="text" placeholder="请输入要搜索的名称" v-model:value="pag.subject.name"
                 style="width: 200px;margin-left: 20px"/>
        <n-button @click="search">搜索</n-button>
      </n-space>
      <n-data-table :columns="pag.subject.columns" :data="pag.subject.data"/>
      <n-pagination :page="pag.subject.current" :page-count="pag.subject.count" :on-update:page="updateSubject"/>
    </n-space>
  </n-card>

  <n-modal :show="modal.update.show" transform-origin="center" :on-update:show="modal.update.cancel">
    <n-card class="modal-content" style="width: 400px;min-height: 280px;" :bordered="true" size="small">
      <div class="modal-head">
        <p class="title">编辑</p>
      </div>
      <div class="modal-body">
        <n-space vertical :size="[0,10]">
          <li>
            <label>名称：</label>
            <n-input v-model:value="modal.data.name" type="text" placeholder="请输入科目名称"/>
          </li>
          <li>
            <label>时长：</label>
            <n-input v-model:value="modal.data.duration" type="text" placeholder="请输入考试时间"/>
          </li>
          <li>
            <label>预设：</label>
            <n-dynamic-tags v-model:value="modal.data.arrange_times" :on-update:value="tagsFlush"
                            style="margin-top: 10px"/>
          </li>
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
  <n-modal :show="modal.add.show" transform-origin="center" :on-update:show="modal.add.cancel">
    <n-card class="modal-content" style="width: 400px;height: 330px;" :bordered="true" size="small">
      <div class="modal-head">
        <p class="title">添加</p>
      </div>
      <div class="modal-body">
        <n-space vertical :size="[0,10]">
          <li>
            <label>名称：</label>
            <n-input v-model:value="modal.data.name" type="text" placeholder="请输入科目名称"/>
          </li>
          <li>
            <label>时长：</label>
            <n-input v-model:value="modal.data.duration" type="text" placeholder="请输入考试时间"/>
          </li>
          <li>
            <label>预设：</label>
            <n-dynamic-tags v-model:value="modal.data.arrange_times" :on-update:value="tagsFlush"
                            style="margin-top: 10px"/>
          </li>
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
  <!--  <n-modal :show="modal3.add.show" transform-origin="center" :on-update:show="modal3.add.show">-->
  <!--    <n-card class="modal-content" style="width: 500px;height: 250px;" :bordered="true" size="small">-->
  <!--      <div class="modal-head">-->
  <!--        <p class="title">删除</p>-->
  <!--      </div>-->
  <!--      <div class="modal-body">-->
  <!--        <label style="width: 150px;">确定删除该条记录?</label><br><br>-->
  <!--        <label style="width: 400px;color: #ff0016">计算机等级考试一级 （9：00-11：00；13：00-15：00）</label>-->

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
.modal-content {
  border-radius: 5px;
  padding: 0;
}

li {
  list-style: none
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

.modal-body label {
  width: 60px;
  display: inline-block;
}

.modal-body .n-input {
  width: 230px;
}
</style>
