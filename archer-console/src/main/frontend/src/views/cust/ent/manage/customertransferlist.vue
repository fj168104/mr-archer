<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="客户名称">
          <el-input v-model="listQuery.filters.customername" placeholder="客户名称" @keyup.enter.native="handleFilter"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
              新增
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table
      :key="listQuery.tableKey"
      v-loading="listQuery.listLoading"
      :data="listQuery.list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >

      <el-table-column label="转出状态" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.status | showCodeName(codemap.CustTransferStatus)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="权限类型" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.righttype | showCodeName(codemap.CustTransferType)}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="接收人" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.username}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="客户名称" width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.customername}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="证件类型" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.certtype | showCodeName(certTypeOption)}}</span>
        </template>
      </el-table-column>
      
      <el-table-column label="证件号码" width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.certid}}</span>
        </template>
      </el-table-column>

      <el-table-column label="转出申请日期"  align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.applydate}}</span>
        </template>
      </el-table-column>

      <el-table-column label="转出确认日期"  align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.confirmdate}}</span>
        </template>
      </el-table-column>

      <el-table-column label="转出拒绝日期"  align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.refusedate}}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="120" class-name="small-padding fixed-width" fixed="right">

        <template slot-scope="scope">
          <el-tooltip v-if="scope.row.status == '01'"  content="删除" placement="top">
            <el-button @click="handleDelete(scope.$index,scope.row)" size="medium" type="danger" icon="el-icon-delete" circle plain></el-button>
          </el-tooltip>
        </template>

      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="'新增客户转出'" :append-to-body="true" :visible.sync="newDataDialogVisible" v-if="newDataDialogVisible">
      <el-form ref="newDataForm" :rules="rules" :model="newData" label-position="left" label-width="160px" style="width: 50%; margin-left:50px;">

        <el-form-item label="权限类型" prop="righttype">
          <!-- <el-select v-model="newData.righttype" clearable placeholder="">
            <el-option
              v-for="item in codemap.CustTransferType"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select> -->
          <el-radio-group v-model="newData.righttype">
            <el-radio v-for="item in codemap.CustTransferType" :key="item.value" :label="item.value">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="客户名称" prop="customername">
          <el-input v-model="newData.customername" :readonly="true">
            <el-button slot="append" icon="el-icon-search" @click="chooseCustomerDialogVisible = true"></el-button>
          </el-input>
        </el-form-item>

        <el-form-item label="证件类型" prop="certtype">
          <el-select v-model="newData.certtype" placeholder="" :disabled="true">
            <el-option
              v-for="item in certTypeOption"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="证件号码" prop="certid">
          <el-input v-model="newData.certid" :readonly="true">
          </el-input>
        </el-form-item>

        <el-form-item label="接收人" prop="username">
          <el-input v-model="newData.username" :readonly="true">
            <el-button slot="append" icon="el-icon-search" @click="chooseUserDialogVisible = true"></el-button>
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="createData()">
            确定
          </el-button>
          <el-button @click="newDataDialogVisible = false">
            取消
          </el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
    
    <!-- 查询客户列表窗口 start-->
    <el-dialog :title="'查询客户'" :append-to-body="true" :visible.sync="chooseCustomerDialogVisible" width="70%">
      <ent-choose-list @confirmChooseCustomer="confirmChooseCustomer"></ent-choose-list>
    </el-dialog>
    <!-- 查询客户列表窗口 end -->
    
    <!-- 查询客户列表窗口 start-->
    <el-dialog :title="'查询用户'" :append-to-body="true" :visible.sync="chooseUserDialogVisible">
      <choose-user-list @confirmChooseUser="confirmChooseUser"></choose-user-list>
    </el-dialog>
    <!-- 查询客户列表窗口 end -->

  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryCustomerTransferList,deleteCustomerTransfer,createCustomerTransfer } from '@/api/cust/customertransfer'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'
import EntChooseList from '@/views/cust/ent/choose/list'
import ChooseUserList from '@/views/system/choose/chooseuserlist'

export default {
  name: 'CustomerTransferList',
  components: { Pagination,EntChooseList,ChooseUserList },
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
          customername: ''
        },
        list: null
      },
      newData: {},
      rules:{
        righttype: [{ required: true, message: '请选择权限类型', trigger: 'blur' }],
        customername: [{ required: true, message: '请选择客户名称', trigger: 'blur' }],
        username: [{ required: true, message: '请选择接收人', trigger: 'blur' }]
      },
      certTypeOption: [],
      codemap: {},
      newDataDialogVisible: false,
      chooseCustomerDialogVisible: false,
      chooseUserDialogVisible: false
    }
  },
  created() {
    queryCodeList({codelist:['EntCertType','PersonCertType','CustTransferStatus','CustTransferType']}).then(response => {
      this.codemap = response.data
      this.certTypeOption = this.codemap.EntCertType.concat(this.codemap.PersonCertType)
      this.getList()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryCustomerTransferList(this.listQuery).then(response => {
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
    },
    resetNewData() {
      this.newData = {
        righttype: '',
        customerid: '',
        customername: '',
        certtype: '',
        certid: '',
        userid: '',
        username: ''
      }
    },
    handleCreate() {
      this.resetNewData()
      this.newDataDialogVisible = true
      this.$nextTick(() => {
        this.$refs['newDataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['newDataForm'].validate((valid) => {
        if (valid) {
          createCustomerTransfer(this.newData).then((res) => {
            this.newDataDialogVisible = false
            this.getList()
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
    confirmChooseCustomer(cust){
      this.newData.customerid = cust.id
      this.newData.customername = cust.name
      this.newData.certtype = cust.certtype
      this.newData.certid = cust.certid
      this.chooseCustomerDialogVisible = false;
    },
    confirmChooseUser(user){
      this.newData.userid = user.id
      this.newData.username = user.nick
      this.chooseUserDialogVisible = false;
    },
    //删除
    handleDelete(idx, row) {
      this.$confirm('您确定要永久删除该信息？', '提示', confirm).then(() => {
        deleteCustomerTransfer(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    }
  }
}
</script>