<template>
  <div style="margin-top: 10px">
    <!--    全部-->
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="全部订单" name="all">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="scope">
              <el-table :data="scope.row.drugList" stripe style="width: 100%">
                <el-table-column prop="drugName" label="药品名称"></el-table-column>
                <el-table-column prop="gram" label="药品克数"></el-table-column>
                <el-table-column prop="price" label="价格"></el-table-column>
              </el-table>
            </template>
          </el-table-column>
          <el-table-column label="订单编号" prop="orderNo"></el-table-column>
          <el-table-column label="宠物" width="300">
            <template slot-scope="scope">
              <div style="display: flex">
                <div style="flex: 2"><a :href="['/front/pet?id=' + scope.row.petId]">
                  <el-image :src="scope.row.image" style="width: 100px; height: 100px;" fit="contain"></el-image>
                </a></div>
                <div style="flex: 2"><a style="color: #666"
                                        :href="['/front/pet?id=' + scope.row.petId]">{{ scope.row.petName }}</a></div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="总价格">
            <template slot-scope="scope">
              <span style="color: red">￥ {{ scope.row.totalPrice }}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" prop="state">
            <template slot-scope="scope">
              <span v-if="scope.row.state ==='0'">未支付</span>
              <span v-if="scope.row.state ==='1'">订单完成</span>
              <span v-if="scope.row.state ==='2'">订单取消</span>
            </template>
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="pay(scope.row.id)" v-if="scope.row.state === '0'">付款</el-button>
              <el-button type="danger" @click="cancel(scope.row.id)" v-if="scope.row.state === '0'">取消</el-button>
              <el-popconfirm
                  v-if="scope.row.state === '0' || scope.row.state === '1'"
                  @confirm="del(scope.row.id)"
                  title="确定删除？"
              >
                <el-button type="danger" icon="el-icon-delete" circle slot="reference" style="margin-left: 10px"></el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-size="pageSize"
              :page-sizes="[2, 5, 10]"
              layout="prev, pager, next"
              :total="total"
          >
          </el-pagination>
        </div>
      </el-tab-pane>

      <!--      待付款-->
      <el-tab-pane label="未支付" name="0">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="scope">
              <el-table :data="scope.row.drugList" stripe style="width: 100%">
                <el-table-column prop="drugName" label="药品名称"></el-table-column>
                <el-table-column prop="gram" label="药品克数"></el-table-column>
                <el-table-column prop="price" label="价格"></el-table-column>
              </el-table>
            </template>
          </el-table-column>
          <el-table-column label="订单编号" prop="orderNo"></el-table-column>
          <el-table-column label="宠物" width="300">
            <template slot-scope="scope">
              <div style="display: flex">
                <div style="flex: 2"><a :href="['/front/pet?id=' + scope.row.petId]">
                  <el-image :src="scope.row.image" style="width: 100px; height: 100px;" fit="contain"></el-image>
                </a></div>
                <div style="flex: 2"><a style="color: #666"
                                        :href="['/front/pet?id=' + scope.row.petId]">{{ scope.row.petName }}</a></div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="总价格">
            <template slot-scope="scope">
              <span style="color: red">￥ {{ scope.row.totalPrice }}</span>
            </template>
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="pay(scope.row.id)">付款</el-button>
              <el-button type="danger" @click="cancel(scope.row.id)">取消</el-button>
              <el-popconfirm
                  @confirm="del(scope.row.id)"
                  title="确定删除？"
              >
                <el-button type="danger" icon="el-icon-delete" circle slot="reference" style="margin-left: 10px"></el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-size="pageSize"
              :page-sizes="[2, 5, 10]"
              layout="prev, pager, next"
              :total="total"
          >
          </el-pagination>
        </div>
      </el-tab-pane>

      <el-tab-pane label="订单完成" name="1">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="scope">
              <el-table :data="scope.row.drugList" stripe style="width: 100%">
                <el-table-column prop="drugName" label="药品名称"></el-table-column>
                <el-table-column prop="gram" label="药品克数"></el-table-column>
                <el-table-column prop="price" label="价格"></el-table-column>
              </el-table>
            </template>
          </el-table-column>
          <el-table-column label="订单编号" prop="orderNo"></el-table-column>
          <el-table-column label="宠物" width="300">
            <template slot-scope="scope">
              <div style="display: flex">
                <div style="flex: 2"><a :href="['/front/pet?id=' + scope.row.petId]">
                  <el-image :src="scope.row.image" style="width: 100px; height: 100px;" fit="contain"></el-image>
                </a></div>
                <div style="flex: 2"><a style="color: #666"
                                        :href="['/front/pet?id=' + scope.row.petId]">{{ scope.row.petName }}</a></div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="总价格">
            <template slot-scope="scope">
              <span style="color: red">￥ {{ scope.row.totalPrice }}</span>
            </template>
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          >
            <template slot-scope="scope">
              <el-popconfirm
                  @confirm="del(scope.row.id)"
                  title="确定删除？"
              >
                <el-button type="danger" icon="el-icon-delete" circle slot="reference" style="margin-left: 10px"></el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-size="pageSize"
              :page-sizes="[2, 5, 10]"
              layout="prev, pager, next"
              :total="total"
          >
          </el-pagination>
        </div>
      </el-tab-pane>

      <el-tab-pane label="订单取消" name="2">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="scope">
              <el-table :data="scope.row.drugList" stripe style="width: 100%">
                <el-table-column prop="drugName" label="药品名称"></el-table-column>
                <el-table-column prop="gram" label="药品克数"></el-table-column>
                <el-table-column prop="price" label="价格"></el-table-column>
              </el-table>
            </template>
          </el-table-column>
          <el-table-column label="订单编号" prop="orderNo"></el-table-column>
          <el-table-column label="宠物" width="300">
            <template slot-scope="scope">
              <div style="display: flex">
                <div style="flex: 2"><a :href="['/front/pet?id=' + scope.row.petId]">
                  <el-image :src="scope.row.image" style="width: 100px; height: 100px;" fit="contain"></el-image>
                </a></div>
                <div style="flex: 2"><a style="color: #666"
                                        :href="['/front/pet?id=' + scope.row.petId]">{{ scope.row.petName }}</a></div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="总价格">
            <template slot-scope="scope">
              <span style="color: red">￥ {{ scope.row.totalPrice }}</span>
            </template>
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          >
            <template slot-scope="scope">
              <el-popconfirm
                  @confirm="del(scope.row.id)"
                  title="确定删除？"
              >
                <el-button type="danger" icon="el-icon-delete" circle slot="reference" style="margin-left: 10px"></el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-size="pageSize"
              :page-sizes="[2, 5, 10]"
              layout="prev, pager, next"
              :total="total"
          >
          </el-pagination>
        </div>
      </el-tab-pane>


    </el-tabs>


  </div>
</template>

<script>
import API from "@/utils/request";

export default {
  name: "cart",
  data() {
    return {
      user: {},
      pageNum: 1,
      pageSize: 10,
      total: 0,
      activeName: 'all',
      tableData: [],
      payData: [],
      commentData: [],
      entity: {},
      state: 'all',
    }
  },
  created() {
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    if (!this.user.id) {
      this.$message({
        type: 'warning',
        message: '请登录！'
      })
      return
    }
    this.load()
  },
  methods: {
    handleClick(tab, event) {
      this.state = tab.name
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    load() {
      this.commentData = []
      if(this.state === 'all') {this.state = ''}
      API.get("/api/order/page/front", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          state: this.state
        }
      }).then(res => {
        this.tableData = res.data.records
      })
    },
    pay(id) {
      API.put("/api/order/pay/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: 'success',
            message: '付款成功'
          })
          this.load()
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          })
        }
      })
    },

    cancel(id) {
      API.put("/api/order", {id: id, state: '2'}).then(res => {
        if (res.code === '0') {
          this.$message({
            type: 'success',
            message: '操作成功'
          })
          this.load()
        }
      })
    },
    del(id) {
      API.delete("/api/order/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: 'success',
            message: '操作成功'
          })
          this.load()
        }
      })
    }
  }
}
</script>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
