<template>
  <el-container>
    <el-header>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="用户名称">
          <el-input v-model="listQuery.filters.nick" placeholder="用户名称" @keyup.enter.native="handleFilter"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
          </el-button>
        </el-form-item>
      </el-form>
    </el-header>

    <el-main>
      <el-table
        :key="listQuery.tableKey"
        v-loading="listQuery.listLoading"
        :data="listQuery.list"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column label="登录账户">
          <template slot-scope="scope">
            <span>{{ scope.row.username }}</span>
          </template>
        </el-table-column>

        <el-table-column label="用户名称">
          <template slot-scope="scope">
            <span>{{ scope.row.nick }}</span>
          </template>
        </el-table-column>

        <el-table-column label="所属机构">
          <template slot-scope="scope">
            <span>{{ scope.row.orgname }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" width="130" class-name="small-padding fixed-width" fixed="right">
          <template slot-scope="scope">
            <el-button @click="$emit('confirmChooseUser', scope.row)" type="primary">确认</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    </el-main>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryUserAllList } from '@/api/user'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'ChooseUserList',
  components: { Pagination },
  directives: { waves },
  filters: {},
  data() {
    return {
      listQuery: {
        tableKey: 0,
        listLoading: true,
        page: 1,
        total: 0,
        limit: 10,
        filters:{
          nick: ''
        },
        list: null
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryUserAllList(this.listQuery).then(response => {
        this.listQuery.list = response.data.records
        this.listQuery.total = response.data.total
        this.listQuery.listLoading = false
      }).catch(() => {
        this.$message.info("获取数据失败！")
        this.listQuery.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    }
  }
}
</script>