<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.nick" placeholder="昵称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.username" placeholder="用户名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />

      <el-select v-model="listQuery.sort" style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        新增用户
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload">
        导出
      </el-button>
      <el-checkbox v-model="showReviewer" class="filter-item" style="margin-left:15px;" @change="tableKey=tableKey+1">
        审批人
      </el-checkbox>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column v-if="false" label="ID" prop="id" sortable="custom" align="center" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column label="用户名" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>

      <el-table-column label="昵称"  align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.nick }}</span>
        </template>
      </el-table-column>

      <el-table-column label="电话"  align="center" >
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>

      <el-table-column label="角色" align="center" width="300">
        <template slot-scope="scope">
          <el-tag style="margin: 2px;" v-for="role in scope.row.roleList" :key="role.id">{{role.roleName}}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="创建时间"  align="center" >
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column v-if="true" label="状态" class-name="status-col" width="100">
        <template slot-scope="{row}">
          <el-tag :type="row.lock | statusFilter">
            {{ row.lock === '1'? '已禁用':'已启用' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">

        <template slot-scope="scope">
          <el-tooltip content="编辑" placement="top">
            <el-button @click="handleUpdate(scope.row)" size="medium" type="primary" icon="el-icon-edit" circle plain></el-button>
          </el-tooltip>
          <el-tooltip content="修改角色" placement="top" v-if="!hasAdminRole(scope.row)">
            <el-button @click="handleUpdateUserRoles(scope.$index,scope.row)" size="medium" type="info" icon="el-icon-star-off" circle plain></el-button>
          </el-tooltip>

          <el-tooltip content="启用" placement="top" v-if="!hasAdminRole(scope.row) && scope.row.lock!='0'">
            <el-button @click="handleModifyStatus(scope.row,'0')" size="medium" type="success" icon="el-icon-plus" circle plain></el-button>
          </el-tooltip>

          <el-tooltip content="禁用" placement="top" v-if="!hasAdminRole(scope.row) && scope.row.lock!='1'">
            <el-button @click="handleModifyStatus(scope.row,'1')" size="medium" type="warning" icon="el-icon-minus" circle plain></el-button>
          </el-tooltip>

          <el-tooltip content="删除" placement="top" v-if="!hasAdminRole(scope.row)">
            <el-button @click="handleDelete(scope.$index,scope.row)" size="medium" type="danger" icon="el-icon-delete" circle plain></el-button>
          </el-tooltip>

        </template>

      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">

        <el-form-item label="用户名" prop="username">
          <el-input v-model="temp.username" />
        </el-form-item>

        <el-form-item label="姓名" prop="nick">
          <el-input v-model="temp.nick" />
        </el-form-item>

        <el-form-item label="邮箱" prop="mail">
          <el-input v-model="temp.mail" />
        </el-form-item>

        <el-form-item label="电话" prop="phone">
          <el-input v-model="temp.phone" />
        </el-form-item>

        <el-form-item label="头像" prop="avatar">
          <el-input v-model="temp.avatar" />
        </el-form-item>

        <el-form-item label="状态" prop="lock">
          <el-select v-model="temp.lock" class="filter-item" placeholder="请选择">
            <el-option v-for="item in lockOptions" :key="item.key" :label="item.display_name" :value="item.key" />
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确定
        </el-button>
      </div>
    </el-dialog>

    <!--弹出窗口：修改用户角色-->
    <el-dialog title="修改用户的角色" :visible.sync="editRolesDialogVisible" width="30%">
      <div>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-checkbox-group v-model="updateUserRolesData.rids" @change="handleCheckedRolesChange">
          <el-checkbox class="role-checkbox" v-for="role in roleOptions" :label="role.id" :key="role.id">
            {{role.roleValue}}
          </el-checkbox>
        </el-checkbox-group>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editRolesDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="checkUpdateUserRolesData">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { updateUserRoles, updateUserStatus, createUser, updateUser, fetchUserList, deleteUser } from '@/api/user'
import { getRoles } from '@/api/role'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination


const lockOptions = [
  { key: '0', display_name: '启用' },
  { key: '1', display_name: '禁用' }
]

export default {
  name: 'UserTable',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        0: 'success',
        1: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        nick: undefined,
        username: undefined,
        sort: '+id'
      },
      lockOptions,
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      showReviewer: false,
      temp: {
        id: undefined,
        username: '',
        nick: '',
        mail: '',
        avatar: '',
        phone: '',
        lock: '',
        roleList: {}
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '新增'
      },
      dialogPvVisible: false,
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        nick: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
      },
      downloadLoading: false,

      //role
      checkAll: false,
      editRolesDialogVisible: false,
      isIndeterminate: true,
      //所有角色(管理员除外)
      roleOptions:[],
      roleMap: new Map(),
      // 更新用户的角色的数据
      updateUserRolesData: {
        idx: null,
        uid: null,
        rids: []
      }
    }
  },
  created() {
    this.getList()
    this.initData()
  },
  methods: {
    initData(){
      //所有角色选项
      getRoles().then(res => {
        res.data.forEach(role => {
          if(role.roleValue!=='SuperAdmin'){//排除管理员
            this.roleOptions.push(role)
            this.roleMap.set(role.id, role.roleName)
          }
        })
      })
    },

    getList() {
      this.listLoading = true
      fetchUserList(this.listQuery).then(response => {

        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleModifyStatus(row, lock) {
      row.lock = lock
      this.temp = Object.assign({}, row)
      updateUser(this.temp).then(() => {
        this.$message({
          message: '操作成功',
          type: 'success'
        })

      });

    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        username: '',
        nick: '',
        mail: '',
        avatar: '',
        phone: '',
        lock: '1',
        roleList: {}
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          createUser(this.temp).then((res) => {
            this.temp.id = res.data.id
            this.list.unshift(this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '新增成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },

    hasAdminRole(row){
      if(row && row.roleList && row.roleList.length > 0){
        return row.roleList.some(role=>role.roleValue==="SuperAdmin")
      }
      return false
    },

    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          updateUser(tempData).then(() => {
            for (const v of this.list) {
              if (v.id === this.temp.id) {
                const index = this.list.indexOf(v)
                this.list.splice(index, 1, this.temp)
                break
              }
            }
            this.dialogFormVisible = false
            this.$notify({
              title: 'Success',
              message: '修改成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },

    //删除
    handleDelete(idx, row) {
      this.$confirm('您确定要永久删除该用户？', '提示', confirm).then(() => {
        deleteUser(row.id).then(res => {
          this.list.splice(idx, 1)
          --this.total
          this.dialogFormVisible = false
          this.$message.success("删除成功")
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });

    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['timestamp', 'title', 'type', 'importance', 'status']
        const filterVal = ['timestamp', 'title', 'type', 'importance', 'status']
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'table-list'
        })
        this.downloadLoading = false
      })
    },

    //更新用户的角色
    handleUpdateUserRoles(idx, row) {
      this.isIndeterminate = row.roleList.length >0 && row.roleList.length < this.roleOptions.length
      this.checkAll = row.roleList.length === this.roleOptions.length
      // 显示用户的角色
      this.updateUserRolesData = {
        idx: idx,
        uid: row.id,
        rids: row.roleList.length > 0 ? row.roleList.map(role=>role.id):[]
      }
      // 显示弹窗
      this.editRolesDialogVisible = true
    },

    //全选
    handleCheckAllChange(val) {
      let allRids = this.roleOptions.map(role => role.id)
      this.updateUserRolesData.rids = val ? allRids : [];
      this.isIndeterminate = false;
    },

    handleCheckedRolesChange(value){
      let checkedCount = value.length
      this.checkAll = checkedCount === this.roleOptions.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.roleOptions.length;
    },

    checkUpdateUserRolesData() {
      const noRolesSelected = this.updateUserRolesData && this.updateUserRolesData.rids && this.updateUserRolesData.rids.length ==0;
      if(noRolesSelected){
        this.$confirm('当前没有选中任何角色，会清除该用户已有的角色, 是否继续?', '提示', confirm).then(() => {
          this.invokeUpdateUserRolesApi()
        }).catch(() => {
          this.$message("已取消编辑用户角色");
        });
      }else{
        this.invokeUpdateUserRolesApi()
      }
    },

    invokeUpdateUserRolesApi(){
      console.info(JSON.stringify(this.updateUserRolesData))
      updateUserRoles(this.updateUserRolesData).then(res => {
        let newRoles = this.updateUserRolesData.rids.map(rid=>{
          let roleName = this.roleMap.get(rid);
          if(roleName) return {id: rid, roleName: roleName}
        })

        this.list[this.updateUserRolesData.idx].roleList = newRoles
        this.editRolesDialogVisible = false
        this.$message.success("更新成功")
      })
    },

    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .role-checkbox{
    margin-left: 0px;
    margin-right: 15px;
  }
</style>
