<template>
  <el-container class="app-container">
    <el-header>
      <el-form :inline="true" class="demo-form-inline filter-container">
        <el-form-item label="名称">
          <el-input v-model="listQuery.filters.name" placeholder="名称" @keyup.enter.native="handleFilter"></el-input>
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
        <el-table-column label="ID" prop="id" align="center" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>

        <el-table-column label="名称">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="文件类型" align="center" width="120">
          <template slot-scope="scope">
            <span>{{ scope.row.filetype | showCodeName(codemap.FileType)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="文件数量限制" align="center" width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.count}}</span>
          </template>
        </el-table-column>

        <el-table-column label="是否可用" align="center" width="120">
          <template slot-scope="scope">
            <span>{{ scope.row.isinuse | showCodeName(codemap.IsNot)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width" fixed="right">

          <template slot-scope="scope">
            <el-tooltip content="确认选择" placement="top">
              <el-button @click="$emit('confirmChooseFilelistNode', scope.row)" type="primary">确认选择</el-button>
            </el-tooltip>
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
import { queryFilelistNodeList } from '@/api/filelist/filelistnode'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'ChooseFilelistNodeList',
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
          name: ''
        },
        list: null
      },
      codemap : {},
      edittype: 'Add',
      editData: {
        id: '',
        filetype: '',
        name: '',
        remark: '',
        count: 0,
        isinuse: ''
      }
    }
  },
  created() {
    queryCodeList({codelist:['FileType','IsNot']}).then(response => {
      this.codemap = response.data
      this.getList()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryFilelistNodeList(this.listQuery).then(response => {
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