<template>
  <div>

    <div style="margin-top: 10px">
      <!--      商品-->
      <el-row :gutter="10">
        <!--    图片-->
        <el-col :span="8">
          <el-card>
            <div>
              <el-image :src="goods.image"></el-image>
            </div>
          </el-card>
        </el-col>

        <!--    商品细节-->
        <el-col :span="16">
          <el-card>
            <div style="text-align: right">
              <div style="display: inline-block; cursor: pointer;"  @click="collect">
                <img :src="collectActiveIcon" alt="收藏" style="width: 15px; ">
                <span style="position: relative; bottom: 3px; font-size: 12px; left: 5px; color: #666">收藏</span>
              </div>
            </div>
            <div style="padding: 10px 0; font-size: 20px; font-weight: bold">{{ goods.name }}</div>
            <div style="padding: 10px 0; color: #666; font-size: 14px">{{ goods.description }}</div>
            <div style="padding: 10px 0; color: #999;">
              <span>发布时间</span>
              <span style="margin-left: 20px; color: #666">{{ goods.createTime }}</span>
            </div>



            <div style="margin-top: 20px; padding: 10px 0">
              <el-button style="margin-left: 10px; background-color: orangered; color: white" @click="buyNow">立即用药
              </el-button>
            </div>

          </el-card>
        </el-col>

      </el-row>

    </div>
  </div>
</template>

<script>
import API from "@/utils/request";

const url = "/api/video/"

export default {
  name: "Goods",
  data() {
    return {
      messages: [],
      dialogFormVisible: false,
      entity: {},
      address: '',
      mainImg: '',
      collectActiveIcon: require("../../assets/收藏-激活.png"),
      id: 1,
      user: {},
      goods: {},
      praiseFlag: false
    };
  },
  created() {
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    this.id = this.$route.query.id
    this.load()
  },
  methods: {
    collect() {
      API.post("/api/collect", {
        petName: this.goods.name,
        petImg: this.goods.image,
        petId: this.goods.id,
        userId: this.user.id
      }).then(res => {
        if (res.code === '0') {
          this.$message({
            message: "收藏成功",
            type: "success"
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
        this.load();
      })
    },
    buyNow() {
      this.$router.replace("/front/preOrder/"+this.goods.categoryId+"/"+this.goods.id)
    },

    load() {
      API.get("/api/pet/" + this.id).then(res => {
        this.goods = res.data
      })
    },
  },
};
</script>

<style scoped>

</style>
