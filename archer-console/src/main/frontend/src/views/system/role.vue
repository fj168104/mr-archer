<template>
  <div class="app-container">
    <el-button type="primary" @click="handleAddRole">新增角色</el-button>
    <el-button type="warning" @click="syncPerm">同步权限</el-button>

    <el-table :data="rolesList" style="width: 100%;margin-top:30px;" border>
      <el-table-column align="center" label="角色ID" width="220">
        <template slot-scope="scope">
          {{ scope.row.roleValue }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="角色名称" width="220">
        <template slot-scope="scope">
          {{ scope.row.roleName }}
        </template>
      </el-table-column>
      <el-table-column align="header-center" label="描述">
        <template slot-scope="scope">
          {{ scope.row.roleDesc }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope)">编辑权限</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" :title="dialogType==='edit'?'编辑角色':'新增角色'">
      <el-form :model="role" label-width="80px" label-position="left">
        <el-form-item label="名称">
          <el-input v-model="role.roleName" placeholder="Role Name" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="role.roleDesc"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
            placeholder="角色描述"
          />
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-tree
            ref="tree"
            :check-strictly="checkStrictly"
            :data="routesData"
            :props="defaultProps"
            show-checkbox
            node-key="path"
            class="permission-tree"
          />
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="confirmRole">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import path from 'path'
import {doGenerateRoutes} from '@/store/modules/permission'
import { deepClone } from '@/utils'
import { constantRoutes, asyncRoutes } from '@/router'
import { getRoles, addRole, deleteRole, updateRole, syncPerm } from '@/api/role'

const defaultRole = {
  id: undefined,
  roleValue: '',
  roleName: '',
  roleDesc: '',
  routes: [],
  perms: []
}

export default {
  data() {
    return {
      role: Object.assign({}, defaultRole),
      routes: [],
      perms: [],
      rolesList: [],
      dialogVisible: false,
      dialogType: 'new',
      checkStrictly: false,
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  computed: {
    routesData() {
      return this.routes
    }
  },
  created() {
    // Mock: get all routes and roles list from server
    this.getRoutes()
    this.getRoles()
  },
  methods: {
    async getRoutes() {
      let routes = await deepClone([...constantRoutes, ...asyncRoutes])
      this.serviceRoutes = routes
      this.routes = this.generateRoutes(routes)
    },
    async getRoles() {
      const res = await getRoles()
      this.rolesList = res.data
      for (const role of this.rolesList) {
        if(role.perms){
          const accessRoutes = await doGenerateRoutes(role.perms)
          role.routes = await deepClone([...constantRoutes, ...accessRoutes])
        }else{
          role.routes = []
        }
      }
    },

    async syncPerm(){
      const aPerms = await this.getAllPerms(this.routes)
      let uniqueArr = [...new Set(aPerms)]
      if(uniqueArr.length !== aPerms.length) {
        this.$message({
          type: 'error',
          message: '权限值重复!'
        })
        return
      }
      syncPerm(this.routes).then(()=>
        this.$message({
          type: 'success',
          message: '同步成功!'
        })
      ).catch(error =>
        this.$message({
          message: error.message,
          type: 'error',
          duration: 5 * 1000
        })
      )
    },

    getAllPerms(routes){
      let data = []
      routes.forEach(route => {
        if (route.children && route.children.length > 0) {
          const temp = this.getAllPerms(route.children)
          if (temp.length > 0) {
            data = [...data, ...temp]
          }
        }else{
          data.push(route.perm)
        }
      })
      return data
    },
    // Reshape the routes structure so that it looks the same as the sidebar
    generateRoutes(routes, basePath = '/') {
      const res = []

      for (let route of routes) {
        // skip some route
        if (route.hidden) { continue }

        const onlyOneShowingChild = this.onlyOneShowingChild(route.children, route)

        if (route.children && onlyOneShowingChild && !route.alwaysShow) {
          route = onlyOneShowingChild
        }

        const data = {
          path: path.resolve(basePath, route.path),
          title: route.meta && route.meta.title,
          perm: route.meta && route.meta.perm

        }

        // recursive child routes
        if (route.children) {
          data.children = this.generateRoutes(route.children, data.path)
        }

        //如果没有配置perm，则不加入
        if(data.perm){
          res.push(data)
        }
      }
      return res
    },
    generateArr(routes) {
      let data = []
      routes.forEach(route => {
        if (route.children && route.children.length > 0) {
          const temp = this.generateArr(route.children)
          if (temp.length > 0) {
            data = [...data, ...temp]
          }
        }else{
          data.push(route)
        }
      })
      return data
    },
    handleAddRole() {
      this.role = Object.assign({}, defaultRole)
      if (this.$refs.tree) {
        this.$refs.tree.setCheckedNodes([])
      }
      this.dialogType = 'new'
      this.dialogVisible = true
    },
    handleEdit(scope) {
      this.dialogType = 'edit'
      this.dialogVisible = true
//      this.checkStrictly = true
      this.role = deepClone(scope.row)

      this.$nextTick(() => {
        const routes = this.generateRoutes(this.role.routes)
        this.$refs.tree.setCheckedNodes(this.generateArr(routes))
        // set checked state of a node not affects its father and child nodes
        this.checkStrictly = false
      })
    },
    handleDelete({ $index, row }) {
      this.$confirm('确定要删除该角色么?', 'Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      })
        .then(async() => {
          await deleteRole(row.val)
          this.rolesList.splice($index, 1)
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
        .catch(err => { console.error(err) })
    },
    generateTree(routes, basePath = '/', checkedKeys) {
      const res = []

      for (const route of routes) {
        const routePath = path.resolve(basePath, route.path)

        // recursive child routes
        if (route.children) {
          route.children = this.generateTree(route.children, routePath, checkedKeys)
        }

        if (checkedKeys.includes(routePath) || (route.children && route.children.length >= 1)) {
          res.push(route)
        }
      }
      return res
    },
    async confirmRole() {
      const isEdit = this.dialogType === 'edit'

      const checkedKeys = this.$refs.tree.getCheckedKeys()
      this.role.routes = this.generateTree(deepClone(this.serviceRoutes), '/', checkedKeys)

      //delete unuse properties
      this.role.routes.forEach(r => delete r['component']);

      if (isEdit) {
        await updateRole(this.role)
        for (let index = 0; index < this.rolesList.length; index++) {
          if (this.rolesList[index].roleValue === this.role.roleValue) {
            this.rolesList.splice(index, 1, Object.assign({}, this.role))
            break
          }
        }
      } else {
        const { data } = await addRole(this.role)
        this.role = data
        this.rolesList.push(this.role)
      }

      const { roleDesc, roleValue, roleName } = this.role
      this.dialogVisible = false
      this.$notify({
        title: '成功',
        dangerouslyUseHTMLString: true,
        message: `
            <div>角色值: ${roleValue}</div>
            <div>角色名: ${roleName}</div>
            <div>描述: ${roleDesc}</div>
          `,
        type: 'success'
      })
    },
    // reference: src/view/layout/components/Sidebar/SidebarItem.vue
    onlyOneShowingChild(children = [], parent) {
      let onlyOneChild = null
      const showingChildren = children.filter(item => !item.hidden)

      // When there is only one child route, the child route is displayed by default
      if (showingChildren.length === 1) {
        onlyOneChild = showingChildren[0]
        onlyOneChild.path = path.resolve(parent.path, onlyOneChild.path)
        return onlyOneChild
      }

      // Show parent if there are no child route to display
      if (showingChildren.length === 0) {
        onlyOneChild = { ... parent, path: '', noShowingChildren: true }
        return onlyOneChild
      }

      return false
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  .roles-table {
    margin-top: 30px;
  }
  .permission-tree {
    margin-bottom: 30px;
  }
}
</style>
