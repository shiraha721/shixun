<template>
  <div class="product-list">
    <el-row v-if="productList.length === 0">
      <el-empty description="暂无商品信息"></el-empty>
    </el-row>
    <el-row v-else>
      <el-col
        :span="6"
        @click.native="route(product)"
        v-for="(product, index) in productList"
        :key="index"
      >
        <div class="item-product">
          <div class="cover">
            <img :src="coverListParse(product)" alt="" srcset="" />
          </div>
          <div
            style="display: flex;justify-content: left;gap: 4px;align-items: center;"
          >
            <span class="bargain-hover">{{
              product.isBargain ? "支持砍价" : "不支持砍价"
            }}</span>
            <span class="title">
              {{ product.name }}
            </span>
          </div>
          <div style="padding-block: 15px;">
            <span class="decimel-symbol">¥</span>
            <span class="price">{{ product.price }}</span>
            <span class="love">4人想要</span>
          </div>
          <div class="info">
            <img :src="product.userAvatar" alt="" srcset="" />
            <span>{{ product.userName }}</span>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getSearchKey } from "@/utils/storage";
export default {
  data() {
    return {
      searchKey: "",
      keyInterval: null, // 用于存储定时器的引用
      productQueryDto: {},
      productList: []
    };
  },
  created() {
    this.startKeyLoader(); // 启动定时器
  },
  beforeDestroy() {
    this.clearKeyLoader(); // 清除定时器
  },
  methods: {
    route(product) {
      // 跳转商品详情
      this.$router.push("/product-detail?productId=" + product.id);
    },
    coverListParse(product) {
      if (product.coverList === null) {
        return;
      }
      const newCoverList = product.coverList.split(",");
      return newCoverList[0];
    },
    fetchProduct() {
      this.productQueryDto.name = this.searchKey;
      this.$axios
        .post("/product/query", this.productQueryDto)
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            this.productList = data.data;
          }
        })
        .catch(error => {
          console.log("商品查询异常：", error);
        });
    },
    loadKey() {
      const key = getSearchKey();
      if (key !== this.searchKey) {
        this.searchKey = key;
        this.fetchProduct();
      }
    },
    startKeyLoader() {
      // 每隔一定时间调用 loadKey 方法
      this.keyInterval = setInterval(() => {
        this.loadKey();
      }, 1000); // 每1000毫秒（1秒）调用一次
    },
    clearKeyLoader() {
      // 清除定时器
      if (this.keyInterval) {
        clearInterval(this.keyInterval);
        this.keyInterval = null; // 重置定时器引用
      }
    }
  }
};
</script>
<style lang="scss" scope>
.product-list {
  padding-block: 20px;

  .item-product {
    padding: 10px 10px 16px 10px;
    box-sizing: border-box;
    border-radius: 15px;
    transition: all 0.5s;
    cursor: pointer;

    .cover {
      img {
        width: 100%;
        height: 240px;
        border-radius: 10px;
      }
    }

    .bargain-hover {
      font-size: 12px;
      font-weight: 800;
      background-color: rgb(255, 230, 15);
      color: rgb(51, 51, 51);
      border-radius: 2px;
      padding: 2px 6px;
    }

    .title {
      font-size: 20px;
      color: #1f1f1f;
    }

    .decimel-symbol {
      font-size: 14px;
      color: #ff4f24;
      font-weight: 800;
    }

    .price {
      font-size: 24px;
      color: #ff4f24;
      font-weight: 800;
      margin-right: 6px;
    }

    .love {
      font-size: 14px;
      color: #999;
    }

    .info {
      display: flex;
      justify-content: left;
      align-items: center;
      gap: 4px;

      img {
        width: 20px;
        height: 20px;
        border-radius: 50%;
      }

      span {
        font-size: 14px;
        color: #999;
      }
    }
  }

  .item-product:hover {
    box-shadow: 1px 4px 6px rgba(0, 0, 0, 0.1), 0 1px 3px rgba(0, 0, 0, 0.06);
  }
}
</style>
