<template>
  <div class="detail-container">
    <div class="cover">
      <div>
        <div
          :style="{
            border: coverIndex === index ? '1px solid rgb(51,51,51)' : ''
          }"
          @click="coverSelected(coverItem, index)"
          class="cover-item"
          v-for="(coverItem, index) in coverList"
          :key="index"
        >
          <img :src="coverItem" alt="" srcset="" />
        </div>
      </div>
      <div class="large-cover">
        <div>
          <i @click="coverToLeft" class="el-icon-arrow-left"></i>
        </div>
        <img :src="coverItem" alt="" srcset="" />
        <div>
          <i @click="coverToRight" class="el-icon-arrow-right"></i>
        </div>
      </div>
    </div>
    <div class="info">
      <div class="decimal">
        <span class="price"
          ><span class="symbol">￥</span>{{ product.price }}</span
        >
        <span
          style="border: 2px solid rgb(214, 214, 214);border-radius: 50%;"
        ></span>
        <span>{{ product.categoryName }}</span>
        <span
          style="border: 2px solid rgb(214, 214, 214);border-radius: 50%;"
        ></span>
        <img
          :src="product.userAvatar"
          style="width: 20px;height: 20px;border-radius: 50%;"
          alt=""
          srcset=""
        />
        <span>{{ product.userName }}</span>
        <span class="bargain">{{
          product.isBargain ? "可砍价" : "不支持砍价"
        }}</span>
      </div>
      <div class="decimal">
        <span class="love">{{ product.likeNumber }}人想要</span>
        <span
          style="border: 2px solid rgb(214, 214, 214);border-radius: 50%;"
        ></span>
        <span class="love">{{ product.saveNumber }}人收藏</span>
        <span
          style="border: 2px solid rgb(214, 214, 214);border-radius: 50%;"
        ></span>
        <span class="love">{{ product.viewNumber }}人浏览</span>
        <span
          style="border: 2px solid rgb(214, 214, 214);border-radius: 50%;"
        ></span>
        <span>{{ product.oldLevel }}成新</span>
        <span
          style="border: 2px solid rgb(214, 214, 214);border-radius: 50%;"
        ></span>
        <span>库存&nbsp;{{ product.inventory }}（件/盒/箱..）</span>
      </div>
      <div class="name">
        {{ product.name }}
      </div>
      <div>
        <div v-html="product.detail"></div>
      </div>
      <div class="operation">
        <div class="left">
          <span @click="likeProduct"
            ><i class="el-icon-sell" style="margin-right: 5px;"></i>我想要</span
          >
          <span @click="buyProduct">立即购买</span>
        </div>
        <div class="right">
          <span @click="saveOperation"
            ><i style="margin-right: 5px;" class="el-icon-star-off"></i
            >{{ saveFlag ? "取消收藏" : "收藏" }}</span
          >
        </div>
      </div>
      <div v-if="userInfo !== null">
        <Evaluations contentType="PRODUCT" :contentId="product.id" />
      </div>
    </div>
    <el-dialog
      :show-close="false"
      :visible.sync="dialogProductOperaion"
      width="35%"
    >
      <div style="padding:16px 20px;">
        <p>商品下单</p>
        <div class="info">
          <div class="decimal">
            <span class="price"
              ><span class="symbol">￥</span>{{ product.price }}</span
            >
            <span
              style="border: 2px solid rgb(214, 214, 214);border-radius: 50%;"
            ></span>
            <span>{{ product.categoryName }}</span>
            <span
              style="border: 2px solid rgb(214, 214, 214);border-radius: 50%;"
            ></span>
            <img
              :src="product.userAvatar"
              style="width: 20px;height: 20px;border-radius: 50%;"
              alt=""
              srcset=""
            />
            <span>{{ product.userName }}</span>
            <span class="bargain">{{
              product.isBargain ? "可砍价" : "不支持砍价"
            }}</span>
          </div>
          <div class="decimal">
            <span
              style="border: 2px solid rgb(214, 214, 214);border-radius: 50%;"
            ></span>
            <span>{{ product.oldLevel }}成新</span>
            <span
              style="border: 2px solid rgb(214, 214, 214);border-radius: 50%;"
            ></span>
            <span>库存&nbsp;{{ product.inventory }}（件/盒/箱..）</span>
          </div>
          <div class="name">
            {{ product.name }}
          </div>
        </div>
        <div>
          <p>下单数量</p>
          <el-input-number
            v-model="buyNumber"
            :min="1"
            :max="product.inventory"
            label="请选择"
          ></el-input-number>
        </div>
        <div>
          <p>备注信息</p>
          <el-input
            type="textarea"
            :rows="3"
            placeholder="补充备注"
            v-model="detail"
          >
          </el-input>
        </div>
      </div>
      <span slot="footer" class="dialog-footer" style="margin-top: 10px;">
        <span class="channel-button" @click="cannelBuy()">
          取消下单
        </span>
        <span class="edit-button" @click="buyConfirm()">
          确定下单
        </span>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getUserInfo } from "@/utils/storage";
import Evaluations from "@/components/Evaluations";
export default {
  components: { Evaluations },
  name: "ProductDetail",
  data() {
    return {
      productId: null,
      product: {},
      coverList: [],
      coverIndex: 0,
      coverItem: null,
      keyInterval: null,
      saveFlag: false, // 判断用户是否已经收藏
      dialogProductOperaion: false,
      buyNumber: 1,
      detail: "",
      userInfo: null
    };
  },
  created() {
    this.getParam();
    this.viewOperation();
  },
  beforeDestroy() {
    this.clearBanner(); // 清除定时器
  },
  methods: {
    // 浏览操作
    viewOperation() {
      const userInfo = getUserInfo();
      if (userInfo === null) {
        // 没登录不用记录
        return;
      }
      this.userInfo = userInfo;
      // 对于用户这是无感的
      this.$axios
        .post(`/interaction/view/${this.productId}`)
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            console.log("用户浏览已经处理");
          }
        })
        .catch(error => {
          console.log("浏览记录异常：", error);
        });
    },
    /**
     * 商品下单
     */
    buyConfirm() {
      const ordersDTO = {
        productId: this.product.id,
        buyNumber: this.buyNumber,
        detail: this.detail
      };
      this.$axios
        .post(`/product/buyProduct`, ordersDTO)
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            this.$notify({
              duration: 1000,
              title: "下单操作",
              message: data.msg,
              type: "success"
            });
            this.fetchProduct(this.product.id);
            this.cannelBuy();
          } else {
            this.$notify({
              duration: 2000,
              title: "下单操作",
              message: data.msg,
              type: "error"
            });
          }
        })
        .catch(error => {
          this.$notify({
            duration: 2000,
            title: "下单操作",
            message: error,
            type: "error"
          });
          console.log("商品下单异常：", error);
        });
    },
    cannelBuy() {
      this.dialogProductOperaion = false;
      this.buyNumber = 1;
    },
    buyProduct() {
      this.dialogProductOperaion = true;
    },
    likeProduct() {
      this.$axios
        .post(`/interaction/likeProduct/${this.product.id}`)
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            this.$notify({
              duration: 1000,
              title: "想要操作通知",
              message: data.msg,
              type: "success"
            });
          } else {
            this.$notify({
              duration: 2000,
              title: "想要操作通知",
              message: data.msg,
              type: "info"
            });
          }
        })
        .catch(error => {
          console.log("商品---想要---异常：", error);
        });
    },
    querySaveStatus() {
      // 判断用户是否已经登录
      const userInfo = getUserInfo();
      if (userInfo === null) {
        // 没登录不用查
        console.log("用户没登录");
        return;
      }
      const interactionQueryDto = {
        userId: userInfo.id,
        productId: this.product.id,
        type: 1 // 1代表的是收藏行为
      };
      this.$axios
        .post("/interaction/query", interactionQueryDto)
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            // 代表没有收藏
            this.saveFlag = data.total !== 0;
          }
        })
        .catch(error => {
          console.log("商品查询异常：", error);
        });
    },
    /**
     * 收藏操作 （收藏跟取消收藏是一组对立的操作）
     */
    saveOperation() {
      this.$axios
        .post(`/interaction/saveOperation/${this.product.id}`)
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            // 代表没有收藏
            this.saveFlag = data.data;
            this.$notify({
              duration: 1000,
              title: "收藏操作成功",
              message: data.msg,
              type: "success"
            });
          }
        })
        .catch(error => {
          console.log("商品查询异常：", error);
        });
    },
    clearBanner() {
      if (this.keyInterval) {
        clearInterval(this.keyInterval);
        this.keyInterval = null; // 重置定时器引用
      }
    },
    startBanner() {
      this.keyInterval = setInterval(() => {
        if (this.coverIndex === this.coverList.length - 1) {
          this.coverIndex = 0;
        } else {
          this.coverIndex = this.coverIndex + 1;
        }
        this.coverItem = this.coverList[this.coverIndex];
      }, 5000);
    },
    coverToLeft() {
      if (this.coverIndex === 0) {
        this.coverIndex = this.coverList.length - 1;
      } else {
        this.coverIndex = this.coverIndex - 1;
      }
      this.coverItem = this.coverList[this.coverIndex];
    },
    coverToRight() {
      if (this.coverIndex === this.coverList.length - 1) {
        this.coverIndex = 0;
      } else {
        this.coverIndex = this.coverIndex + 1;
      }
      this.coverItem = this.coverList[this.coverIndex];
    },
    coverSelected(coverItem, index) {
      this.coverItem = coverItem;
      this.coverIndex = index;
    },
    /**
     * 从路径上取得商品ID
     */
    getParam() {
      const param = this.$route.query;
      this.productId = Number(param.productId);
      this.fetchProduct(this.productId);
    },
    coverListParse(product) {
      if (product.coverList === null) {
        return;
      }
      this.coverList = product.coverList.split(",");
      // 默认选中第一张封面
      this.coverItem = this.coverList[0];
      // 启动定时器，定时轮播
      this.startBanner();
    },
    fetchProduct(productId) {
      this.$axios
        .post("/product/query", { id: productId })
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            this.product = data.data[0];
            this.coverListParse(this.product);
            this.querySaveStatus();
          }
        })
        .catch(error => {
          console.log("商品查询异常：", error);
        });
    }
  }
};
</script>
<style scoped lang="scss">
.love {
  font-size: 14px;
  color: #999;
}

.info {
  width: 500px;

  .operation {
    display: flex;
    justify-content: left;
    gap: 20px;
    font-size: 14px;
    cursor: pointer;

    .right {
      span:hover {
        background-color: rgb(241, 241, 241);
      }

      span {
        display: inline-block;
        width: 100px;
        text-align: center;
        background-color: rgb(246, 246, 246);
        border-radius: 20px;
      }
    }

    .left {
      display: flex;
      justify-content: space-evenly;

      span {
        display: inline-block;
        width: 160px;
        text-align: center;
      }

      span:first-child {
        background-color: rgb(255, 230, 15);
        border-top-left-radius: 20px;
        border-bottom-left-radius: 20px;
      }

      span:last-child {
        background-color: rgb(59, 59, 59);
        color: rgb(245, 245, 245);
        border-top-right-radius: 20px;
        border-bottom-right-radius: 20px;
      }
    }

    div {
      line-height: 40px;
    }
  }

  .name {
    margin-block: 10px;
    font-size: 24px;
  }

  .decimal {
    display: flex;
    justify-content: left;
    align-items: center;
    gap: 10px;
    font-size: 14px;
    margin-block: 6px;
    width: 500px;

    .price {
      .symbol {
        font-size: 16px;
      }

      font-size: 32px;
      font-weight: 800;
      color: rgb(255, 68, 0);
    }

    .bargain {
      font-size: 10px;
      color: rgb(51, 51, 51);
      background-color: rgb(246, 228, 24);
      padding: 2px 4px;
      border-radius: 5px;
    }
  }
}

.detail-container {
  display: flex;
  justify-content: left;
  justify-content: center;

  .cover {
    display: flex;
    justify-content: left;

    .large-cover {
      padding: 6px;
      margin: 10px;
      display: flex;
      justify-content: left;
      gap: 10px;

      i:hover {
        background-color: rgb(246, 246, 246);
      }

      i {
        display: inline-block;
        padding: 20px;
        border-radius: 50%;
        font-size: 20px;
        font-weight: 800;
        cursor: pointer;
      }

      img {
        width: 280px;
        height: 280px;
      }
    }

    .cover-item:hover {
      border: 1px solid rgb(51, 51, 51);
    }

    .cover-item {
      padding: 6px;
      margin: 10px;
      border: 1px solid rgb(255, 255, 255);
      border-radius: 5px;
      transition: all 0.5s;

      img {
        width: 80px;
        height: 80px;
      }
    }
  }
}
</style>
