<script setup>
import {
  NCard,
  NInput,
  NButton,
  NTag,
  NDataTable,
  NPagination,
  NSwitch,
  NSpace,
  NModal
} from "naive-ui"
import manage from "/src/api/manage";
import {reactive, h, ref} from "vue";

const pag = reactive({
  staff: {
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
        title: '手机号',
        key: 'phone',
        align: 'center'
      },
      {
        title: '邮箱',
        key: 'email',
        align: 'center'
      },
      {
        title: '锁定',
        key: 'lock',
        align: 'center',
        render(row, index) {
          return h(NSpace, {
            justify: 'center'
          }, [
            h(NSwitch, {
              justify: 'center',
              value: row.lock,
              onClick: () => {
                let item = pag.staff.data[index]
                console.log(!item.lock)
                pag.staff.data[index].lock = !item.lock
                manage.updateStaff({
                  account: {
                    id: item.account_id,
                    lock: pag.staff.data[index].lock
                  }
                }).then(res => {
                  let json = res.data
                  if (json.state === 200) {

                  }
                })
              }
            })
          ]);
        }
      },
      {
        title: '动作',
        key: 'actions',
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
                        modal.update.open(row)
                        tableData.current = index
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

const modal = reactive({
  add: {
    show: false,
    confirm: () => {
      if (!modal.data.phone) {
        window.$message.warning('请输入手机号')
        return
      }
      if (!modal.data.email) {
        window.$message.warning('请输入邮箱')
        return
      }
      if (!modal.data.name) {
        window.$message.warning('请输入名称')
        return
      }
      if (!modal.data.password) {
        window.$message.warning('请输入密码')
        return
      }
      manage.addStaff(modal.data).then(res => {
        let json = res.data
        if (json.state === 200) {
          window.$message.success('添加成功')
          modal.add.cancel()
          updateStaff()
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
      if (!modal.data.phone) {
        window.$message.warning('请输入手机号')
        return
      }
      if (!modal.data.email) {
        window.$message.warning('请输入邮箱')
        return
      }
      if (!modal.data.name) {
        window.$message.warning('请输入名称')
        return
      }
      let data = {
        account: {}
      }
      let currentData = tableData.data[tableData.current]
      if (modal.data.name !== currentData.name)
        data.name = modal.data.name
      if (modal.data.phone !== currentData.phone)
        data.account.phone = modal.data.phone
      if (modal.data.email !== currentData.email)
        data.account.email = modal.data.email
      data.id = currentData.id
      data.account.id = currentData.account_id
      if (modal.data.password !== '')
        data.account.password = modal.data.password
      manage.updateStaff(data).then(res => {
        let json = res.data
        if (json.state === 200) {
          window.$message.success('更新成功')
          modal.update.cancel()
          updateStaff()
        }
      })
    },
    cancel: () => {
      modal.update.show = false
    },
    open: (row) => {
      modal.clear()
      modal.data.name = row.name
      modal.data.phone = row.phone
      modal.data.email = row.email
      modal.update.show = true
    }
  },
  clear: () => {
    modal.data = {
      name: '',
      phone: '',
      email: '',
      password: ''
    }
  },
  data: {}
})
const tableData = reactive({
  data: [],
  current: 0
})

function updateStaff(page) {
  if (page)
    pag.staff.current = page
  manage.getStaffList({page: pag.staff.current, size: pag.staff.size}).then(res => {
    if (res.data.state === 200) {
      tableData.data = res.data.data.list
      let data = []
      let index = 1
      res.data.data.list.forEach(item => {
        data.push({
          id: item.id,
          account_id: item.account_id,
          key: (pag.staff.current - 1) * pag.staff.size + index++,
          name: item.name,
          phone: item.account.phone,
          email: item.account.email,
          password: item.account.password,
          lock: item.account.lock,
        })
      })
      console.log(data.length)
      pag.staff.data = data
      pag.staff.count = res.data.data.pages
    }
  })
}

function init() {
  updateStaff(1)
}

init()
</script>

<template>
  <n-card>
    <n-space vertical :size="[0,20]">
      <n-button color="#468DE9" @click="modal.add.open">添加</n-button>
      <n-data-table :columns="pag.staff.columns" :data="pag.staff.data"/>
      <n-pagination v-model:page="pag.staff.current" :page-count="pag.staff.count" :on-update:page="updateStaff"/>
    </n-space>
  </n-card>

  <n-modal :show="modal.add.show" transform-origin="center" :on-update:show="modal.add.cancel">
    <n-card class="modal-content" style="width: 400px;height: 330px;" :bordered="true" size="small">
      <div class="modal-head">
        <p class="title">添加</p>
      </div>
      <div class="modal-body">
        <n-space vertical :size="[0,15]">
          <li>
            <label>姓名:</label>
            <n-input v-model:value="modal.data.name" placeholder="请输入姓名"/>
          </li>
          <li>
            <label>手机号:</label>
            <n-input v-model:value="modal.data.phone" placeholder="请输入手机号"/>
          </li>
          <li>
            <label>邮箱:</label>
            <n-input v-model:value="modal.data.email" placeholder="请输入邮箱"/>
          </li>
          <li>
            <label>密码:</label>
            <n-input v-model:value="modal.data.password" placeholder="请输入密码"/>
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

  <n-modal :show="modal.update.show" transform-origin="center" :on-update:show="modal.update.cancel">
    <n-card class="modal-content" style="width: 400px;height: 330px;" :bordered="true" size="small">
      <div class="modal-head">
        <p class="title">编辑</p>
      </div>
      <div class="modal-body">
        <n-space vertical :size="[0,15]">
          <li>
            <label>姓名:</label>
            <n-input v-model:value="modal.data.name" placeholder="请输入姓名"/>
          </li>
          <li>
            <label>手机号:</label>
            <n-input v-model:value="modal.data.phone" placeholder="请输入手机号"/>
          </li>
          <li>
            <label>邮箱:</label>
            <n-input v-model:value="modal.data.email" placeholder="请输入邮箱"/>
          </li>
          <li>
            <label>密码:</label>
            <n-input v-model:value="modal.data.password" placeholder="请输入新密码"/>
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
</template>

<style scoped>
li {
  list-style: none;
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

.modal-body label {
  width: 60px;
  display: inline-block;
}

.modal-body .n-input {
  width: 200px;
}
</style>
