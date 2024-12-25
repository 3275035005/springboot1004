<template>
  <div style="margin-top: 10px">
    <div style="background-color: white; padding: 10px">
      <div style="padding: 10px; margin-bottom: 20px; border-bottom: 1px solid #eee">
        <div style="font-size: 20px; border-bottom: 2px solid dodgerblue; padding-bottom: 10px; margin-bottom: 20px">
          填写信息
        </div>

        <el-form :model="formData" label-position="left" label-width="80px">
          <el-form-item label="疾病" >
            <el-radio-group v-model="formData.diseaseId">
            <div style=" color: #555">
            <el-radio  :label="item.id" v-for="item in diseaseData" :key="item.id" border>{{ item.name }}</el-radio>
             </div>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="体重">
            <el-input v-model="formData.kg" type="number"  style="width: 20%" placeholder="请填写体重/kg"/>
          </el-form-item>
          <el-form-item label="年龄(周)">
            <el-input-number v-model="formData.age" :min="1"
                             :max="1000" ></el-input-number>
          </el-form-item>

        </el-form>
      </div>
      <div style="margin-top: 10px">
        <div style="background-color: white; padding: 10px">
          <div style="color: red; text-align: right">
            <div style="padding: 10px 0">
              <el-button style="background-color: red; color: white; width: 100px" @click="submitOrder">提交订单</el-button>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import API from "@/utils/request";

export default {
  name: "cart",
  data() {
    return {
      chooseDisease: '',
      user: {},
      diseaseData: [],
      formData:{}
    }
  },
  created() {
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    if (!this.user) {
      this.$message({
        type: "warning",
        message: '请登录！'
      })
      return
    }
    let id = this.$route.params.id
    this.formData.petId = this.$route.params.petId
    this.formData.userId = this.user.id
    API.get("/api/disease/findAllByCategoryId/" + id).then(res => {
      this.diseaseData = res.data;
    })

    this.load()

  },
  methods: {
    submitOrder() {
      // 提交订单
      API.post("/api/order",this.formData).then(res => {
        if (res.code === '0') {
          this.$message({
            type: 'success',
            message: '提交成功！'
          })
          this.$router.replace("/front/order")
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          })
        }
      })
    },
    load() {
    },
  }
}
</script>

<style scoped>

</style>
