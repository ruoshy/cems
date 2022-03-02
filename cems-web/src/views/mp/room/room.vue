<script setup>
import {
  NCard,
  NTag,
  NButton,
  useMessage,
  NSelect,
  NSpace,
  NDataTable,
  NSwitch,
  NInput,
  NModal,
  NPagination,
} from "naive-ui"
import {h, defineComponent, ref, reactive} from 'vue'
import manage from "/src/api/manage"

const pag = reactive({
  room: {
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
        title: '编号',
        key: 'name',
        align: 'center'
      },
      {
        title: '容量',
        key: 'examinee_capacity',
        align: 'center'
      },
      {
        title: '监考分配',
        key: 'monitor_capacity',
        align: 'center'
      },
      {
        title: '可用',
        key: 'lock',
        align: 'center',
        render(row, index) {
          return h(
              NSwitch,
              {
                value: row.lock,
                onClick: () => {
                  let item = pag.room.data[index]
                  pag.room.data[index].lock = !item.lock
                  manage.updateExamRoom({
                    id: item.id,
                    lock: item.lock
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

const floor_data = [
  {
    label: "全部",
    value: 'all',
  },
  {
    label: "A楼",
    value: 'A',
  },
  {
    label: 'B楼',
    value: 'B'
  },
  {
    label: 'C楼',
    value: 'C'
  },
  {
    label: 'D楼',
    value: 'D'
  },
  {
    label: 'E楼',
    value: 'E'
  }
]

function updateRoom(page) {
  if (page)
    pag.room.current = page
  manage.getExamRoomList({page: pag.room.current, size: pag.room.size}).then(res => {
    if (res.data.state === 200) {
      tableData.data = res.data.data.list
      let data = []
      let index = 1
      res.data.data.list.forEach(item => {
        data.push({
          id: item.id,
          key: (pag.room.current - 1) * pag.room.size + index++,
          name: item.name,
          lock: item.lock,
          examinee_capacity: item.examinee_capacity,
          monitor_capacity: item.monitor_capacity,
          mark: item.mark
        })
      })
      pag.room.data = data
      pag.room.count = res.data.data.pages
    }
  })
}

const modal = reactive({
  add: {
    show: false,
    confirm: () => {
      if (!modal.data.name) {
        window.$message.warning('请输入编号')
        return
      }
      if (!modal.data.examinee_capacity) {
        window.$message.warning('请输入容量')
        return;
      }
      if (!modal.data.monitor_capacity) {
        window.$message.warning('请输入监考分配')
        return;
      }
      manage.addExamRoom(modal.data).then(res => {
        let json = res.data
        if (json.state === 200) {
          window.$message.success('添加成功')
          modal.add.cancel()
          updateRoom()
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
      }
      if (!modal.data.examinee_capacity) {
        window.$message.warning('请输入容量')
        return;
      }
      if (!modal.data.monitor_capacity) {
        window.$message.warning('请输入监考分配')
        return;
      }
      data.name = modal.data.name
      data.examinee_capacity = modal.data.examinee_capacity
      data.monitor_capacity = modal.data.monitor_capacity
      data.mark = modal.data.mark
      data.id = currentData.id
      manage.updateExamRoom(data).then(res => {
        let json = res.data
        if (json.state === 200) {
          window.$message.success('更新成功')
          modal.update.cancel()
          updateRoom()
        }
      })
    },
    cancel: () => {
      modal.update.show = false
    },
    open: (row) => {
      modal.clear()
      console.log(row)
      modal.data.name = row.name
      modal.data.examinee_capacity = row.examinee_capacity
      modal.data.monitor_capacity = row.monitor_capacity
      modal.data.mark = row.mark
      modal.update.show = true
    }
  },
  clear: () => {
    modal.data = {}
  },
  data: {}
})

function init() {
  updateRoom()
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
                    :options="floor_data" placeholder="请选择楼号"/>
          <n-input v-model:value="value" style="float: left;width: 200px;margin-left: 20px;"
                   type="text" placeholder="请输入要搜索的名称"/>
          <n-button style="margin-left: 10px;" @click="modal.add.open">搜 索</n-button>
        </div>
      </n-space>
      <n-data-table :columns="pag.room.columns" :data="pag.room.data"/>
      <n-pagination v-model:page="pag.room.current" :page-count="pag.room.count" :on-update:page="updateRoom"/>
    </n-space>
  </n-card>


  <n-modal :show="modal.update.show" transform-origin="center" :on-update:show="modal.update.cancel">
    <n-card class="modal-content" style="width: 400px;height: 330px;" :bordered="true" size="small">
      <div class="modal-head">
        <p class="title">编辑</p>
      </div>
      <div class="modal-body">
        <n-space vertical :size="[0,10]">
          <li>
            <label>编号：</label>
            <n-input v-model:value="modal.data.name" type="text" placeholder="请输入编号"/>
          </li>
          <li>
            <label>容量：</label>
            <n-input type="text" v-model:value="modal.data.examinee_capacity" placeholder="请输入容量"/>
          </li>
          <li>
            <label>监考分配：</label>
            <n-input type="text" v-model:value="modal.data.monitor_capacity" placeholder="请输入分配容量"/>
          </li>
          <li>
            <label>备注：</label>
            <n-input type="text" v-model:value="modal.data.mark" placeholder="可选"/>
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
            <label>编号：</label>
            <n-input v-model:value="modal.data.name" type="text" placeholder="请输入编号"/>
          </li>
          <li>
            <label>容量：</label>
            <n-input type="text" v-model:value="modal.data.examinee_capacity" placeholder="请输入容量"/>
          </li>
          <li>
            <label>监考分配：</label>
            <n-input type="text" v-model:value="modal.data.monitor_capacity" placeholder="请输入分配容量"/>
          </li>
          <li>
            <label>备注：</label>
            <n-input type="text" v-model:value="modal.data.mark" placeholder="可选"/>
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
